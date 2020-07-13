package vn.hbm.core.thread;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.TaskScheduler;
import vn.hbm.core.bean.ThreadConfig;
import vn.hbm.core.bean.ThreadConfigs;
import vn.hbm.core.common.AppContext;
import vn.hbm.core.common.Common;
import vn.hbm.core.common.FileUtil;
import vn.hbm.core.common.IGsonBase;
import vn.hbm.core.service.CommonService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

@Slf4j
public class ThreadManager implements Runnable, IGsonBase {
//    private static Logger LOG = LoggerFactory.getLogger(ThreadManager.class);

    private Map<String, ManageableThread> threadList = new ConcurrentHashMap<String, ManageableThread>();
    private boolean runningStatus = false;
    private boolean requireStop = false;
    private boolean envLoaded = false;
    final static String __CONFIG_FILE_DEFAULT__ = "threads.json";
    final List<String> CONFIG_FILES = new ArrayList<String>();
    private String serverMode = "";
    private String jsonConfigDB;
    private String threadLoadFrom;

    protected CommonService commonService = (CommonService) AppContext.getBean("commonService");
    protected TaskScheduler taskScheduler = (TaskScheduler) AppContext.getBean("taskScheduler");

    public ThreadManager() {
        CONFIG_FILES.clear();
        CONFIG_FILES.add(__CONFIG_FILE_DEFAULT__);

        initThreadConfigDB();
    }

    public ThreadManager(List<String> configFiles) {
        CONFIG_FILES.clear();
        if (configFiles != null && configFiles.size() > 0) {
            CONFIG_FILES.addAll(configFiles);
        } else {
            CONFIG_FILES.add(__CONFIG_FILE_DEFAULT__);
        }

        initThreadConfigDB();
    }

    public ThreadManager(List<String> configFiles, String threadLoadFrom) {
        this.threadLoadFrom = threadLoadFrom;
        CONFIG_FILES.clear();
        if (configFiles != null && configFiles.size() > 0) {
            CONFIG_FILES.addAll(configFiles);
        } else {
            CONFIG_FILES.add(__CONFIG_FILE_DEFAULT__);
        }

        initThreadConfigDB();
    }

//    public ThreadManager(List<String> configFiles, String threadLoadFrom, TaskScheduler taskScheduler) {
//        this.threadLoadFrom = threadLoadFrom;
//        this.taskScheduler = taskScheduler;
//        CONFIG_FILES.clear();
//        if (configFiles != null && configFiles.size() > 0) {
//            CONFIG_FILES.addAll(configFiles);
//        } else {
//            CONFIG_FILES.add(__CONFIG_FILE_DEFAULT__);
//        }
//
//        initThreadConfigDB();
//    }

    public void initThreadConfigDB() {
        try {
            if ("BOTH".equalsIgnoreCase(threadLoadFrom) || "ONLY_DB".equalsIgnoreCase(threadLoadFrom)) {
                log.info("Thread config load from DB");
                List<Object> lstObj = commonService.findBySQL("", "SELECT _value FROM sys_param WHERE _key = ? ", new String[]{"THREAD_CONFIG"});
                if (!Common.isEmpty(lstObj)) {
                    jsonConfigDB = String.valueOf(lstObj.get(0));
                }
            }
        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    public void startManager() {
        this.requireStop = false;
        if (!runningStatus) {
            Executors.newFixedThreadPool(1).submit(this);
        } else {
            log.warn("ThreadManager is already running ...");
        }
    }

    public void stopManager() {
        log.info("Require to stop ThreadManager");
        this.requireStop = true;
        Thread.currentThread().interrupt();
        Collection<ManageableThread> threads = threadList.values();
        for (ManageableThread manageableThread : threads) {
            manageableThread.stopJob();
        }
    }

    public Map<String, ManageableThread> getThreadList() {
        return threadList;
    }

    public void setThreadList(Map<String, ManageableThread> threadList) {
        this.threadList = threadList;
    }

    public ManageableThread getThread(String threadId) {
        ManageableThread thread = threadList.get(threadId);
        return thread;
    }

    public String stopThread(String threadId) {
        String message = "";
        ManageableThread thread = threadList.get(threadId);
        if (thread != null) {
            if (thread.isRunning()) {
                thread.stopJob();
            } else {
                message = "Thread is not running yet.";
            }
        } else {
            message = "Could not find the thread";
        }
        return message;
    }

    public String startThread(String threadId) {
        String message = "";
        ManageableThread thread = threadList.get(threadId);
        if (thread != null) {
            if (!thread.isRunning()) {
                thread.startJob();
            } else {
                message = "Thread is already running.";
            }
        } else {
            message = "Could not find the thread";
        }
        return message;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("ThreadManager");
        runningStatus = true;
        while (!requireStop) {
            try {
                if (envLoaded) {
                    ThreadConfigs threadCfgs = null;
                    if ("BOTH".equalsIgnoreCase(threadLoadFrom)) {
                        threadCfgs = loadThreadConfigs();
                        ThreadConfigs threadCfgsDB = loadThreadConfigDB(jsonConfigDB);
                        if (threadCfgsDB != null && !Common.isEmpty(threadCfgsDB.getThreads())) {
                            threadCfgs.addThreads(threadCfgsDB.getThreads());
                        }
                    } else if ("ONLY_DB".equalsIgnoreCase(threadLoadFrom)){
                        threadCfgs = loadThreadConfigDB(jsonConfigDB);
                    } else {
                        threadCfgs = loadThreadConfigs();
                    }

                    processThreadConfigs(threadCfgs);
                } else {
                    log.info("Waiting for environment loaded ...");
                }
            } catch (Exception e) {
                log.error("ThreadManager.run", e);
            } finally {
                try {
                    for (int i = 0; i < 30 * 10 && !requireStop; i++) {
                        Thread.sleep(100l);
                    }
                } catch (InterruptedException e) {
                }
            }
        }
        runningStatus = false;
    }

    public void processThreadConfigs(ThreadConfigs threadCfgs) {
        List<ThreadConfig> threads = null;
        if ((threadCfgs != null) && ((threads = threadCfgs.getThreads()) != null)) {
            for (ThreadConfig threadConfig : threads) {
                ManageableThread thread = threadList.get(threadConfig.getId());
                if(thread == null) {
                    if (!threadConfig.isEnabled()) {
                        continue;
                    }
                    try {
                        thread = (ManageableThread) Class.forName(threadConfig.getClassName()).newInstance();
                        thread.setId(threadConfig.getId());
                        thread.setName(threadConfig.getName());
                        thread.setStatus(false);
                        thread.setDelayTime(threadConfig.getDelayTime());
                        thread.setOrder(threadConfig.getOrder());
                        thread.setParams(threadConfig.getParams());
                        thread.setManager(this);
                        this.getThreadList().put(thread.getId(), thread);
                        if (threadConfig.isAutoStart()) {
                            this.startThread(thread.getId());
                        }
                    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                        log.error("", e);
                    }
                } else {
                    if (!threadConfig.isEnabled()) {
                        log.info("Going to disable thread: {}", thread.getName());
                        this.stopThread(thread.getId());
                        this.getThreadList().remove(thread.getId());
                    } else {
                        thread.setName(threadConfig.getName());
                        thread.setDelayTime(threadConfig.getDelayTime());
                        thread.setParams(threadConfig.getParams());
                        this.getThreadList().put(thread.getId(), thread);
                    }
                }
            }
        }
    }

    public synchronized ThreadConfigs loadThreadConfigs() throws IOException {
        ThreadConfigs threadConfigs = new ThreadConfigs();
        if (CONFIG_FILES.size() == 0) {
            log.warn("No configuration file for threads");
        }
        for (String configFile : CONFIG_FILES) {
            String strJson = FileUtil.fileContentFromClasspath(configFile);
            JSONObject objJson = new JSONObject(strJson);
            JSONArray arrJson = objJson.getJSONArray("threads");
            for (int index = 0 ; index < arrJson.length(); index++) {
                JSONObject obj = arrJson.getJSONObject(index);
                String threadId = obj.getString("id");
                String threadName = obj.getString("name");
                String className = obj.getString("className");
                boolean autoStart = obj.getBoolean("autoStart");
                int delayTime = obj.getInt("delayTime");
                JSONObject params = obj.getJSONObject("params");
                int order = obj.optInt("order");
                boolean enabled = obj.optBoolean("enabled", true);
                ThreadConfig threadConfig = new ThreadConfig();
                threadConfig.setId(threadId);
                threadConfig.setName(threadName);
                threadConfig.setClassName(className);
                threadConfig.setAutoStart(autoStart);
                threadConfig.setDelayTime(delayTime);
                threadConfig.setOrder(order);
                threadConfig.setEnabled(enabled);
                threadConfig.setParams(params);
                threadConfigs.getThreads().add(threadConfig);
            }
        }
        return threadConfigs;
    }

    public synchronized ThreadConfigs loadThreadConfigDB(String jsonConfigDB) throws IOException {
        ThreadConfigs threadConfigs = new ThreadConfigs();

        String strJson = jsonConfigDB;
        JSONObject objJson = new JSONObject(strJson);
        JSONArray arrJson = objJson.getJSONArray("threads");
        for (int index = 0 ; index < arrJson.length(); index++) {
            JSONObject obj = arrJson.getJSONObject(index);
            String threadId = obj.getString("id");
            String threadName = obj.getString("name");
            String className = obj.getString("className");
            boolean autoStart = obj.getBoolean("autoStart");
            int delayTime = obj.getInt("delayTime");
            JSONObject params = obj.getJSONObject("params");
            int order = obj.optInt("order");
            boolean enabled = obj.optBoolean("enabled", true);
            ThreadConfig threadConfig = new ThreadConfig();
            threadConfig.setId(threadId);
            threadConfig.setName(threadName);
            threadConfig.setClassName(className);
            threadConfig.setAutoStart(autoStart);
            threadConfig.setDelayTime(delayTime);
            threadConfig.setOrder(order);
            threadConfig.setEnabled(enabled);
            threadConfig.setParams(params);
            threadConfigs.getThreads().add(threadConfig);
        }

        return threadConfigs;
    }

    /**
     * @return the serverMode
     */
    public String getServerMode() {
        return serverMode;
    }

    /**
     * @param serverMode the serverMode to set
     */
    public void setServerMode(String serverMode) {
        this.serverMode = serverMode;
    }

    public boolean isEnvLoaded() {
        return envLoaded;
    }

    public void setEnvLoaded(boolean envLoaded) {
        this.envLoaded = envLoaded;
    }
}

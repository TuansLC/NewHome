package vn.hbm.core.thread;

import com.google.common.collect.EvictingQueue;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import vn.hbm.core.common.AppContext;
import vn.hbm.core.common.Common;
import vn.hbm.core.common.Constants.ManageableThreadState;
import vn.hbm.core.common.IGsonBase;

import javax.annotation.Resource;

@Slf4j
public abstract class ManageableThread implements Runnable, IGsonBase {
    protected Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected String id;
    protected String name;
    protected boolean status;
    protected boolean requireStop = false;
    private byte state = ManageableThreadState.NORMAL;
    protected int delayTime = 30;
    private int order = 0;
    protected JSONObject params = new JSONObject();
//    private ExecutorService exeService = Executors.newFixedThreadPool(1);
//    private ScheduledExecutorService exeService = Executors.newScheduledThreadPool(1);
    private EvictingQueue<String> tempLogQueue = EvictingQueue.create(500);

    //HA
    protected boolean active = false;
    protected Object activeMonitorObj = new Object();

    @Resource
    private ThreadManager threadManager;

    protected TaskScheduler taskScheduler = (TaskScheduler) AppContext.getBean("taskScheduler");

    public ManageableThread() {
        this.requireStop = false;
        this.setState(ManageableThreadState.NORMAL);
        params = new JSONObject();
    }

    public boolean isRunning() {
        return status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ThreadManager getManager() {
        return threadManager;
    }

    public void setManager(ThreadManager manager) {
        this.threadManager = manager;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void startJob() {
        if (status) {
            log.warn(String.format("Thread %s, name = %s is already started!", this.getId(), this.getName()));
        } else {
            log.info("Submit request to start this thread.");
//            exeService.submit(this);
            String cronExpression = null;
            if (getParams().has("schedule")) {
                cronExpression = getParams().getString("schedule");
            }
            if (Common.isBlank(cronExpression)) {
                cronExpression = "* * * * * ?";
            }
            taskScheduler.schedule(this, new CronTrigger(cronExpression));
        }
    }

    public void stopJob() {
        if (!status) {
            log.warn(String.format("Thread %s, name = %s is not running yet!", this.getId(), this.getName()));
        } else {
            this.requireStop = true;
        }
    }

    protected void loadParameters()  throws Exception {

    }

    protected void initializeSession() throws Exception {
    }

    protected abstract boolean processSession() throws Exception;

    protected void completeSession() throws Exception {
    }

    protected void storeConfig() {
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                Thread.currentThread().setName(this.getName());
                this.status = true;
//            while (!requireStop) {
                if (!requireStop) {
                    try {
                        this.loadParameters();
                        this.initializeSession();

                        this.processSession();
                    } catch (Exception e) {
                        log.error("", e);
                    } finally {
                        this.completeSession();
                    }

                    try {
                        if (getDelayTime() > 0) {
                            for (int iIndex = 0; (iIndex < this.getDelayTime() * 10) && !requireStop; iIndex++) {
                                safeSleep(100L);
                            }
                        } else {
                            safeSleep(100L);
                        }
                    } catch (Exception e) {
                    }

                }
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            this.status = false;
            this.requireStop = false;
        }
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }

    public int getParamAsInt(String keyVal) {
        return this.params.getInt(keyVal);
    }

    public String getParamAsString(String keyVal) {
        return this.params.optString(keyVal, "");
    }

    public boolean getParamAsBoolean(String keyVal) {
        return this.params.getBoolean(keyVal);
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     *
     * @param millis
     */
    protected void safeSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
        }
    }

    protected void safeWait(Object locker, long millis) {
        try {
            locker.wait(millis);
        } catch (Exception e) {
        }
    }

    public void notifyEnteringNewMode(String serverOldMode, String serverNewMode) {
    }

    /**
     *
     * @param beanName
     * @return
     */
    protected Object getBean(String beanName) {
        return AppContext.getBean(beanName);
    }

    public EvictingQueue<String> getTempLogQueue() {
        return tempLogQueue;
    }

    public void setTempLogQueue(EvictingQueue<String> tempLogQueue) {
        this.tempLogQueue = tempLogQueue;
    }

//    public TaskScheduler getTaskScheduler() {
//        return taskScheduler;
//    }
//
//    public void setTaskScheduler(TaskScheduler taskScheduler) {
//        this.taskScheduler = taskScheduler;
//    }
}

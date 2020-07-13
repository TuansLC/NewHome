package vn.hbm.core.bean;

import java.util.ArrayList;
import java.util.List;

public class ThreadConfigs {
    private List<ThreadConfig> threads = new ArrayList();

    public List<ThreadConfig> getThreads() {
        return threads;
    }

    public void setThreads(List<ThreadConfig> threads) {
        this.threads = threads;
    }

    public void addThreads(List<ThreadConfig> threads) {
        this.threads.addAll(threads);
    }
}

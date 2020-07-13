package vn.hbm.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import vn.hbm.core.thread.ThreadManager;

import java.util.List;

@Configuration
public class ThreadManagerConfig {
    @Value("#{'${threadmanager.configfiles}'.split(',')}")
    private List<String> threadConfigFiles;

    @Value("#{'${threadmanager.thread_load_from}'}")
    private String threadLoadFrom;

    @Bean(name = "threadManager", initMethod = "startManager", destroyMethod = "stopManager")
    @DependsOn({"appContext"}) //DependsOn config phai load appContext truoc khi load threadManager neu SpringBoot init threadManager truoc
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ThreadManager threadManager() {
        ThreadManager threadManager = new ThreadManager(threadConfigFiles, threadLoadFrom);
        return threadManager;
    }
}

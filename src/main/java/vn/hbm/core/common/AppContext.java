package vn.hbm.core.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppContext implements ApplicationContextAware {
    public static final String APP_CONTEXT = "appContext";
    private static ApplicationContext applicationContext;

    public AppContext() {}

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        return null == beanName ? null : applicationContext.getBean(beanName);
    }

    public static String getValue(String param) {
        return (String)applicationContext.getBean(param);
    }

    public void initialized() {}
}

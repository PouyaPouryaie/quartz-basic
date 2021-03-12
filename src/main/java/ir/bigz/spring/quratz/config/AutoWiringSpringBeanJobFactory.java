package ir.bigz.spring.quratz.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;


public final class AutoWiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {


    private transient  AutowireCapableBeanFactory beanFactory;

    public void setApplicationContext(ApplicationContext applicationContext) {
        beanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

    protected Object createJobInstance(final TriggerFiredBundle triggerFiredBundle) throws Exception {
        Object job = super.createJobInstance(triggerFiredBundle);
        beanFactory.autowireBean(job);
        return job;
    }
}

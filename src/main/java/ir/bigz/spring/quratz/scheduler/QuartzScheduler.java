package ir.bigz.spring.quratz.scheduler;

import ir.bigz.spring.quratz.config.ApplicationProperties;
import ir.bigz.spring.quratz.config.AutoWiringSpringBeanJobFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Configuration
public class QuartzScheduler {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    DataSource dataSource;

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        System.out.println("Configuring Job factory");
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean("schedulerBean")
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setQuartzProperties(quartzProperties());
        factory.setOverwriteExistingJobs(true);
        factory.setAutoStartup(true);
        factory.setDataSource(dataSource);
        factory.setJobFactory(springBeanJobFactory());
        factory.setWaitForJobsToCompleteOnShutdown(true);
        return factory;
    }

    @Bean
    public Scheduler scheduler(Trigger trigger, JobDetail job, @Qualifier("schedulerBean") SchedulerFactoryBean factory) throws SchedulerException {
        System.out.println("Getting a handle to the Scheduler");
        Scheduler scheduler = factory.getScheduler();
        scheduler.scheduleJob(job, trigger);

        System.out.println("Starting Scheduler threads");
        scheduler.start();
        return scheduler;
    }


    public Properties quartzProperties() {
        Properties properties = new Properties();
        properties.setProperty("org.quartz.threadPool.class",
                applicationProperties.getProperty("org.quartz.threadPool.class"));
        properties.setProperty("org.quartz.threadPool.threadCount",
                applicationProperties.getProperty("org.quartz.threadPool.threadCount"));
        properties.setProperty("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread",
                applicationProperties.getProperty("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread"));
        properties.setProperty("org.quartz.scheduler.instanceName",
                applicationProperties.getProperty("org.quartz.scheduler.instanceName"));
        properties.setProperty("org.quartz.scheduler.instanceId",
                applicationProperties.getProperty("org.quartz.scheduler.instanceId"));
        properties.setProperty("org.quartz.jobStore.driverDelegateClass",
                applicationProperties.getProperty("org.quartz.jobStore.driverDelegateClass"));

        return properties;
    }


    @Bean
    public JobDetail jobDetail(){
        return newJob().ofType(QuartzJob.class)
                .storeDurably()
                .withIdentity("Quartz_Job")
                .withDescription("Invoke Sample Job service...")
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail){
        return newTrigger()
                .withIdentity("Quartz_Job")
                .withDescription("Sample trigger")
                .withSchedule(simpleSchedule().repeatForever().withIntervalInMinutes(1))
                .build();
    }

//    public Scheduler scheduler(Trigger trigger, JobDetail job)
//            throws SchedulerException {
//        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//        scheduler.scheduleJob(job, trigger);
//        return scheduler;
//    }
//
//    public static void runQuery() {
//        QuartzScheduler quartzScheduler = new QuartzScheduler();
//        JobDetail jobDetail = quartzScheduler.jobDetail();
//        Trigger trigger = quartzScheduler.trigger(jobDetail);
//        try {
//            Scheduler scheduler = quartzScheduler.scheduler(trigger, jobDetail);
//            scheduler.start();
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//    }

//    @Bean
//    public JobDetail jobDetails(){
//        return JobBuilder.newJob().ofType(QuartzJob.class).withIdentity("sampleA").storeDurably().build();
//    }
//
//    @Bean
//    public Trigger jobTrigger(JobDetail jobDetails){
//        return TriggerBuilder.newTrigger().forJob(jobDetails)
//                .withIdentity("triggerA")
//                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
//                .build();
//    }

}

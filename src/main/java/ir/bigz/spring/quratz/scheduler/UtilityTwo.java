package ir.bigz.spring.quratz.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

//@Component
public class UtilityTwo {

    public static void runQuery(){
        try {
            JobDetail job = JobBuilder.newJob(QuartzJob.class).withIdentity("sample", "sample data").build();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("sample", "sample data")
                    .withSchedule(
                            // at 00:30 every hour
//                            MIN HOUR DOM MON DOW CMD
//                            CronScheduleBuilder.cronSchedule("* * * * * ?")
                            CronScheduleBuilder.cronSchedule("0 0/5 * ? * * *")
                                    // don't run missed jobs (missed when app was down)
                                    .withMisfireHandlingInstructionDoNothing()
                    )
                    .build();

            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

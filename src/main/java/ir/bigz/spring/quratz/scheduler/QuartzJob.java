package ir.bigz.spring.quratz.scheduler;

import ir.bigz.spring.quratz.service.Data;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.lang.String.format;

@Component
public class QuartzJob implements Job {

    @Autowired
    private Data data;

    @Override
    public void execute(JobExecutionContext context) {
        System.out.println(format("Job ** %s ** fired @ %s", context.getJobDetail().getKey().getName(), context.getFireTime()));
        System.out.println("--------------------------------------------------------------");
        System.out.println("QuartzJob query job STARTED at: " + LocalDateTime.now());
        System.out.println("--------------------------------------------------------------");
        data.getData();
        System.out.println("--------------------------------------------------------------");
        System.out.println("QuartzJob query job DONE at: " + LocalDateTime.now());
        System.out.println("--------------------------------------------------------------");
        System.out.println(format("Next job scheduled @ %s", context.getNextFireTime()));
    }
}

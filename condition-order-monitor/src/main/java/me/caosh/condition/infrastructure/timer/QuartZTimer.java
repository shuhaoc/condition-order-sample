package me.caosh.condition.infrastructure.timer;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.annotation.PostConstruct;

/**
 * Test code!
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/8
 */
//@Component
public class QuartZTimer {
    @PostConstruct
    public void init() throws Exception {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(SimpleQuartzJob.class)
                .withIdentity("simple", "cond")
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("cron", "cond")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();
    }
}

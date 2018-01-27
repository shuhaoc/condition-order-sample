package me.caosh.condition.infrastructure.timer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Test code!
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/8
 */
public class SimpleQuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("=== hello ===");
        System.out.println(context.getJobDetail().getKey());
        System.out.println(context.getTrigger().getKey());
    }
}

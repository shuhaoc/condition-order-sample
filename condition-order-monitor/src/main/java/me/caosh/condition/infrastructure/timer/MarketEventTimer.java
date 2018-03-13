package me.caosh.condition.infrastructure.timer;

import org.joda.time.LocalTime;
import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TimeOfDay;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/13
 */
@Component
public class MarketEventTimer {
    private final MarketEventTimerProperties marketEventTimerProperties;

    public MarketEventTimer(MarketEventTimerProperties marketEventTimerProperties) {
        this.marketEventTimerProperties = marketEventTimerProperties;
    }

    public LocalTime getMarketClosedTime() {
        return LocalTime.parse(marketEventTimerProperties.getMarketClosedTime());
    }

    @PostConstruct
    public void init() throws Exception {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        LocalTime marketClosedTime = getMarketClosedTime();
        TimeOfDay timeOfDayMarketClosed = new TimeOfDay(
                marketClosedTime.getHourOfDay(), marketClosedTime.getMinuteOfHour(), marketClosedTime.getSecondOfMinute());
        JobDetail jobDetail = JobBuilder.newJob(MarketClosedEventJob.class)
                .withIdentity("marketClosed", "engine")
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("marketClosed", "engine")
                .withSchedule(DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                        .onEveryDay()
                        .startingDailyAt(timeOfDayMarketClosed)
                        .withRepeatCount(0))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}

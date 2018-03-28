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
        LocalTime marketClosedTimeEnd = getMarketClosedTime().plusMinutes(1);
        TimeOfDay timeOfDayMarketClosed = transformToTimeOfDay(marketClosedTime);
        TimeOfDay timeOfDayMarketClosedEnd = transformToTimeOfDay(marketClosedTimeEnd);
        JobDetail jobDetail = JobBuilder.newJob(MarketClosedEventJob.class)
                                        .withIdentity("marketClosed", "engine")
                                        .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                                        .withIdentity("marketClosed", "engine")
                                        .withSchedule(DailyTimeIntervalScheduleBuilder
                                                              .dailyTimeIntervalSchedule()
                                                              .onMondayThroughFriday()
                                                              .startingDailyAt(timeOfDayMarketClosed)
                                                              .endingDailyAt(timeOfDayMarketClosedEnd)
                                                              .withRepeatCount(0))
                                        .build();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }

    private static TimeOfDay transformToTimeOfDay(LocalTime localTime) {
        return new TimeOfDay(localTime.getHourOfDay(), localTime.getMinuteOfHour(), localTime.getSecondOfMinute());
    }
}

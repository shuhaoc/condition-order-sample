package me.caosh.condition.infrastructure.timer;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/13
 */
@ConfigurationProperties(prefix = "me.caosh.condition.market-event-timer")
@Component
@Validated
public class MarketEventTimerProperties {
    @NotNull
    private String marketClosedTime;

    public String getMarketClosedTime() {
        return marketClosedTime;
    }

    public void setMarketClosedTime(String marketClosedTime) {
        this.marketClosedTime = marketClosedTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(MarketEventTimerProperties.class).omitNullValues()
                .add("marketClosedTime", marketClosedTime)
                .toString();
    }
}

package me.caosh.condition.application.impl;

import com.google.common.eventbus.Subscribe;
import me.caosh.condition.application.MonitorService;
import me.caosh.condition.domain.model.market.event.RealTimeMarketPushEvent;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.event.ConditionOrderCommandEvent;
import me.caosh.condition.domain.util.EventBuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by caosh on 2017/8/9.
 */
@Service
public class MonitorServiceImpl implements MonitorService {
    private static final Logger logger = LoggerFactory.getLogger(MonitorServiceImpl.class);

    @PostConstruct
    public void init() {
        EventBuses.DEFAULT.register(this);
    }

    @Subscribe
    public void onRealTimeMarketPushEvent(RealTimeMarketPushEvent e) {
        Map<String, RealTimeMarket> realTimeMarketMap = e.getMarketMap();
        logger.info("Start checking =============>>");
        logger.info("Finish checking <<=============");
    }

    @Subscribe
    public void onConditionOrderCommand(ConditionOrderCommandEvent e) {
        ConditionOrder conditionOrder = e.getConditionOrder();
        logger.info("Create or update condition order <= {}", conditionOrder);
    }
}

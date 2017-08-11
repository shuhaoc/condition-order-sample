package me.caosh.condition.application.monitor;

import com.google.common.eventbus.Subscribe;
import me.caosh.condition.domain.model.market.event.RealTimeMarketPushEvent;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.RealTimeMarketDriven;
import me.caosh.condition.domain.model.order.event.ConditionOrderCommandEvent;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.util.EventBuses;
import me.caosh.condition.infrastructure.repository.MonitorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;

/**
 * Created by caosh on 2017/8/9.
 */
@Component
public class ConditionOrderMonitor {
    private static final Logger logger = LoggerFactory.getLogger(ConditionOrderMonitor.class);

    private final MonitorRepository monitorRepository;
    private Map<Long, ConditionOrder> monitorOrders = Collections.emptyMap();

    public ConditionOrderMonitor(MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }

    @PostConstruct
    public void init() {
        EventBuses.DEFAULT.register(this);

        monitorOrders = monitorRepository.getAll();
    }

    @Subscribe
    public void onRealTimeMarketPushEvent(RealTimeMarketPushEvent e) {
        Map<String, RealTimeMarket> realTimeMarketMap = e.getMarketMap();
        logger.info("Start checking =============>>");

        monitorOrders.values().forEach(conditionOrder -> {
            if (conditionOrder instanceof RealTimeMarketDriven) {
                RealTimeMarket realTimeMarket = realTimeMarketMap.get(conditionOrder.getSecurityInfo().getCode());
                if (realTimeMarket != null) {
                    checkWithRealTimeMarket((RealTimeMarketDriven) conditionOrder, realTimeMarket);
                }
            }
        });

        logger.info("Finish checking <<=============");
    }

    private void checkWithRealTimeMarket(RealTimeMarketDriven conditionOrder, RealTimeMarket realTimeMarket) {
        TradeSignal tradeSignal = conditionOrder.onRealTimeMarketUpdate(realTimeMarket);
        if (tradeSignal == SignalFactory.getInstance().general()) {

        }
    }

    @Subscribe
    public void onConditionOrderCommand(ConditionOrderCommandEvent e) {
        ConditionOrder conditionOrder = e.getConditionOrder();
        logger.info("Create or update condition order <= {}", conditionOrder);
    }
}

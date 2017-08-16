package me.caosh.condition.domain.model.trade;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.EntrustStrategy;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/15.
 */
public class EntrustPriceSelector {

    public static BigDecimal selectPrice(RealTimeMarket realTimeMarket, EntrustStrategy entrustStrategy) {
        if (entrustStrategy == EntrustStrategy.CURRENT_PRICE) {
            return Preconditions.checkNotNull(realTimeMarket.getCurrentPrice());
        } else {
            BigDecimal offeredPrice = realTimeMarket.getOfferedPrices().get(entrustStrategy.getValue() - 2);
            return MoreObjects.firstNonNull(offeredPrice, realTimeMarket.getCurrentPrice());
        }
    }

    private EntrustPriceSelector() {
    }
}

package hbec.intellitrade.condorder.domain.tradeplan;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import hbec.intellitrade.common.market.RealTimeMarket;

import java.math.BigDecimal;

/**
 * 五档择价
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/15
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

package hbec.intellitrade.condorder.domain.tradeplan;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.RealTimeMarket;
import me.caosh.autoasm.ConvertibleEnum;

import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/13
 */
public enum OfferedPriceSelector implements ConvertibleEnum<Integer> {
    CURRENT_PRICE(1) {
        @Override
        public BigDecimal selectOfferedPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getCurrentPrice(), realTimeMarket.getPreviousPrice());
        }
    },
    SELL5(2) {
        @Override
        public BigDecimal selectOfferedPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(0), CURRENT_PRICE.selectOfferedPrice(realTimeMarket));
        }
    },
    SELL4(3) {
        @Override
        public BigDecimal selectOfferedPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(1), CURRENT_PRICE.selectOfferedPrice(realTimeMarket));
        }
    },
    SELL3(4) {
        @Override
        public BigDecimal selectOfferedPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(2), CURRENT_PRICE.selectOfferedPrice(realTimeMarket));
        }
    },
    SELL2(5) {
        @Override
        public BigDecimal selectOfferedPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(3), CURRENT_PRICE.selectOfferedPrice(realTimeMarket));
        }
    },
    SELL1(6) {
        @Override
        public BigDecimal selectOfferedPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(4), CURRENT_PRICE.selectOfferedPrice(realTimeMarket));
        }
    },
    BUY1(7) {
        @Override
        public BigDecimal selectOfferedPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(5), CURRENT_PRICE.selectOfferedPrice(realTimeMarket));
        }
    },
    BUY2(8) {
        @Override
        public BigDecimal selectOfferedPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(6), CURRENT_PRICE.selectOfferedPrice(realTimeMarket));
        }
    },
    BUY3(9) {
        @Override
        public BigDecimal selectOfferedPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(7), CURRENT_PRICE.selectOfferedPrice(realTimeMarket));
        }
    },
    BUY4(10) {
        @Override
        public BigDecimal selectOfferedPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(8), CURRENT_PRICE.selectOfferedPrice(realTimeMarket));
        }
    },
    BUY5(11) {
        @Override
        public BigDecimal selectOfferedPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(9), CURRENT_PRICE.selectOfferedPrice(realTimeMarket));
        }
    };

    int value;

    OfferedPriceSelector(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public abstract BigDecimal selectOfferedPrice(RealTimeMarket realTimeMarket);
}

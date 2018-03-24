package hbec.intellitrade.condorder.domain.tradeplan;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.RealTimeMarket;
import me.caosh.autoasm.ConvertibleEnum;

import java.math.BigDecimal;

/**
 * 委托策略，是指基于实时行情五档决定委托价格（五档择价）的策略
 * <p>
 * 其中0（自定义价格）和99（市价委托）是分类选项值，selectEntrustPrice方法实际不调用
 * <p>
 * 五档择价类使用的（包括现价）选项值为1-11，实现selectEntrustPrice方法用于选择实际委托价格
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/2
 */
public enum EntrustStrategy implements ConvertibleEnum<Integer> {
    /**
     * 自定义价格选项
     */
    CUSTOMIZED_PRICE(0) {
        @Override
        public BigDecimal selectEntrustPrice(RealTimeMarket realTimeMarket) {
            throw new UnsupportedOperationException("Use customized price directly");
        }
    },
    CURRENT_PRICE(1) {
        @Override
        public BigDecimal selectEntrustPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getCurrentPrice(), realTimeMarket.getPreviousPrice());
        }
    },
    SELL5(2) {
        @Override
        public BigDecimal selectEntrustPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(0),
                                            CURRENT_PRICE.selectEntrustPrice(realTimeMarket));
        }
    },
    SELL4(3) {
        @Override
        public BigDecimal selectEntrustPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(1),
                                            CURRENT_PRICE.selectEntrustPrice(realTimeMarket));
        }
    },
    SELL3(4) {
        @Override
        public BigDecimal selectEntrustPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(2),
                                            CURRENT_PRICE.selectEntrustPrice(realTimeMarket));
        }
    },
    SELL2(5) {
        @Override
        public BigDecimal selectEntrustPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(3),
                                            CURRENT_PRICE.selectEntrustPrice(realTimeMarket));
        }
    },
    SELL1(6) {
        @Override
        public BigDecimal selectEntrustPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(4),
                                            CURRENT_PRICE.selectEntrustPrice(realTimeMarket));
        }
    },
    BUY1(7) {
        @Override
        public BigDecimal selectEntrustPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(5),
                                            CURRENT_PRICE.selectEntrustPrice(realTimeMarket));
        }
    },
    BUY2(8) {
        @Override
        public BigDecimal selectEntrustPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(6),
                                            CURRENT_PRICE.selectEntrustPrice(realTimeMarket));
        }
    },
    BUY3(9) {
        @Override
        public BigDecimal selectEntrustPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(7),
                                            CURRENT_PRICE.selectEntrustPrice(realTimeMarket));
        }
    },
    BUY4(10) {
        @Override
        public BigDecimal selectEntrustPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(8),
                                            CURRENT_PRICE.selectEntrustPrice(realTimeMarket));
        }
    },
    BUY5(11) {
        @Override
        public BigDecimal selectEntrustPrice(RealTimeMarket realTimeMarket) {
            return MoreObjects.firstNonNull(realTimeMarket.getOfferedPrices().get(9),
                                            CURRENT_PRICE.selectEntrustPrice(realTimeMarket));
        }
    },
    MARKET_PRICE(99) {
        @Override
        public BigDecimal selectEntrustPrice(RealTimeMarket realTimeMarket) {
            // 市价委托不需要输入委托价
            return null;
        }
    };

    int value;

    EntrustStrategy(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    /**
     * 基于交易证券的实时行情选择委托价格
     *
     * @param realTimeMarket 实时行情
     * @return 委托价格，可能为空
     */
    public abstract BigDecimal selectEntrustPrice(RealTimeMarket realTimeMarket);
}

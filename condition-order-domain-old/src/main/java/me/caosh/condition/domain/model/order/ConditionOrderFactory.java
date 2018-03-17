package me.caosh.condition.domain.model.order;

import hbec.intellitrade.common.market.index.IndexInfo;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.delayconfirm.count.DelayConfirmCount;
import hbec.intellitrade.condorder.domain.delayconfirm.count.SingleDelayConfirmCount;
import hbec.intellitrade.condorder.domain.orders.PriceOrder;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlan;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmParam;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlParam;
import hbec.intellitrade.strategy.domain.strategies.condition.PriceCondition;
import hbec.intellitrade.strategy.domain.timerange.MonitorTimeRange;
import org.joda.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/9.
 */
public class ConditionOrderFactory {
    private static final ConditionOrderFactory INSTANCE = new ConditionOrderFactory();

    public static ConditionOrderFactory getInstance() {
        return INSTANCE;
    }

    public ConditionOrder create(Long orderId,
                                 TradeCustomerInfo tradeCustomerInfo,
                                 OrderState orderState,
                                 SecurityInfo securityInfo,
                                 IndexInfo trackedIndexInfo,
                                 StrategyInfo strategyInfo,
                                 Condition condition,
                                 LocalDateTime expireTime,
                                 TradePlan tradePlan,
                                 DelayConfirmParam delayConfirmParam,
                                 DelayConfirmCount delayConfirmCount,
                                 MonitorTimeRange monitorTimeRange,
                                 DeviationCtrlParam deviationCtrlParam) {
        if (strategyInfo == NativeStrategyInfo.PRICE) {
            return new PriceOrder(orderId,
                                  tradeCustomerInfo,
                                  orderState,
                                  securityInfo,
                                  trackedIndexInfo,
                                  (PriceCondition) condition,
                                  expireTime,
                                  (BasicTradePlan) tradePlan,
                                  delayConfirmParam,
                                  (SingleDelayConfirmCount) delayConfirmCount,
                                  monitorTimeRange,
                                  deviationCtrlParam);
//        } else if (strategyInfo == NativeStrategyInfo.TURN_UP) {
//            return new TurnUpBuyOrder(orderId, tradeCustomerInfo, securityInfo, (TurnUpCondition) condition,
//                                      null, (BasicTradePlan) tradePlan, orderState);
//        } else if (strategyInfo == NativeStrategyInfo.TIME) {
//            return new TimeOrder(orderId, tradeCustomerInfo, securityInfo, (TimeReachedCondition) condition, expireTime,
//                                 (BasicTradePlan) tradePlan, orderState);
//        } else if (strategyInfo == NativeStrategyInfo.GRID) {
//            return new GridTradeOrder(orderId, tradeCustomerInfo, securityInfo, (GridCondition) condition,
//                                      null, (DoubleDirectionTradePlan) tradePlan, orderState);
//        } else if (strategyInfo == NativeStrategyInfo.NEW_STOCK) {
//            return new NewStockOrder(orderId,
//                                     tradeCustomerInfo,
//                                     (NewStockPurchaseCondition) condition,
//                                     expireTime,
//                                     orderState);
        }
        throw new IllegalArgumentException("strategyInfo=" + strategyInfo);
    }

    private ConditionOrderFactory() {
    }
}

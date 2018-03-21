package hbec.intellitrade.strategy.domain.condition.deviation;

import hbec.intellitrade.strategy.domain.condition.market.PredictableMarketCondition;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/16
 */
public enum DeviationCtrlConditionFactory {
    /**
     * 单例
     */
    INSTANCE;

    public PredictableMarketCondition wrapWith(PredictableMarketCondition marketCondition,
                                               DeviationCtrl deviationCtrl) {
        if (deviationCtrl.getOption() == DeviationCtrlOption.DISABLED) {
            return marketCondition;
        } else {
            DeviationCtrlInfo param = (DeviationCtrlInfo) deviationCtrl;
            return new DeviationCtrlConditionImpl(param.getLimitPercent(), marketCondition);
        }
    }
}

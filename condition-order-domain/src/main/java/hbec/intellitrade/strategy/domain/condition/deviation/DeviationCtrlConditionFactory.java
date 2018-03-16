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

    public PredictableMarketCondition wrap(PredictableMarketCondition marketCondition,
                                           DeviationCtrlParam deviationCtrlParam) {
        if (deviationCtrlParam.getOption() == DeviationCtrlOption.DISABLED) {
            return marketCondition;
        } else {
            EnabledDeviationCtrlParam param = (EnabledDeviationCtrlParam) deviationCtrlParam;
            return new EnabledDeviationCtrlCondition(marketCondition, param.getLimitPercent());
        }
    }
}

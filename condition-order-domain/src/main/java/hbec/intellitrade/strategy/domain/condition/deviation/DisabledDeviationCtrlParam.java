package hbec.intellitrade.strategy.domain.condition.deviation;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/16
 */
public enum DisabledDeviationCtrlParam implements DeviationCtrlParam {
    /**
     * 禁用状态的偏差控制参数
     */
    DISABLED;

    @Override
    public DeviationCtrlOption getOption() {
        return DeviationCtrlOption.DISABLED;
    }
}

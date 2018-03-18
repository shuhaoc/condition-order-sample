package hbec.intellitrade.strategy.domain.condition.delayconfirm;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
public enum DisabledDelayConfirm implements DelayConfirm {
    /**
     * 单例
     */
    DISABLED;

    @Override
    public DelayConfirmOption getOption() {
        return DelayConfirmOption.DISABLED;
    }
}

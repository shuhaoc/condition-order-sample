package hbec.intellitrade.strategy.domain.condition.delayconfirm;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
public enum DisabledDelayConfirmParam implements DelayConfirmParam {
    /**
     * 单例
     */
    DISABLED;

    @Override
    public DelayConfirmOption getOption() {
        return DelayConfirmOption.DISABLED;
    }

    @Override
    public int getConfirmTimes() {
        return 0;
    }
}

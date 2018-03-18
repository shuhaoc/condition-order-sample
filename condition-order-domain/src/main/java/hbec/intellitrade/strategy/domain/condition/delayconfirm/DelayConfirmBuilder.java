package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import me.caosh.autoasm.ConvertibleBuilder;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
public class DelayConfirmBuilder implements ConvertibleBuilder<DelayConfirm> {
    private DelayConfirmOption option = DelayConfirmOption.DISABLED;
    private int confirmTimes;

    public DelayConfirmBuilder setOption(DelayConfirmOption option) {
        this.option = option;
        return this;
    }

    public DelayConfirmBuilder setConfirmTimes(int confirmTimes) {
        this.confirmTimes = confirmTimes;
        return this;
    }

    @Override
    public DelayConfirm build() {
        if (option == DelayConfirmOption.DISABLED) {
            return DisabledDelayConfirm.DISABLED;
        }
        return new DelayConfirmInfo(option, confirmTimes);
    }
}

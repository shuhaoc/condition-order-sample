package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import com.google.common.base.Preconditions;
import me.caosh.autoasm.ConvertibleBuilder;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
public class DelayConfirmParamBuilder implements ConvertibleBuilder<DelayConfirmParam> {
    private DelayConfirmOption option = DelayConfirmOption.DISABLED;
    private int confirmTimes;

    public DelayConfirmParamBuilder setOption(DelayConfirmOption option) {
        this.option = option;
        return this;
    }

    public DelayConfirmParamBuilder setConfirmTimes(int confirmTimes) {
        this.confirmTimes = confirmTimes;
        return this;
    }

    @Override
    public DelayConfirmParam build() {
        Preconditions.checkNotNull(option, "option cannot be null");

        if (option == DelayConfirmOption.DISABLED) {
            return DisabledDelayConfirmParam.DISABLED;
        } else {
            return new EnabledDelayConfirmParam(option, confirmTimes);
        }
    }
}

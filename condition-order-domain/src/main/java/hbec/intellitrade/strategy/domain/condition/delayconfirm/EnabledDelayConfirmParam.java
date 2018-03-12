package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/8
 */
public class EnabledDelayConfirmParam implements DelayConfirmParam {
    private final DelayConfirmOption option;
    private final int confirmTimes;

    public EnabledDelayConfirmParam(DelayConfirmOption option, int confirmTimes) {
        Preconditions.checkArgument(option != DelayConfirmOption.DISABLED, "option cannot be DISABLED");
        this.option = option;
        this.confirmTimes = confirmTimes;
    }

    @Override
    public DelayConfirmOption getOption() {
        return option;
    }

    @Override
    public int getConfirmTimes() {
        return confirmTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnabledDelayConfirmParam that = (EnabledDelayConfirmParam) o;

        if (confirmTimes != that.confirmTimes) {
            return false;
        }
        return option == that.option;
    }

    @Override
    public int hashCode() {
        int result = option.hashCode();
        result = 31 * result + confirmTimes;
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(EnabledDelayConfirmParam.class).omitNullValues()
                .add("option", option)
                .add("confirmTimes", confirmTimes)
                .toString();
    }
}

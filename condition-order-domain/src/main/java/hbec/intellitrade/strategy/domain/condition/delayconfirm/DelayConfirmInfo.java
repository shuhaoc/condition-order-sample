package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/8
 */
public class DelayConfirmInfo implements DelayConfirm {
    private final DelayConfirmOption option;
    /**
     * 延迟确认（目标）次数
     *
     * @return 延迟确认（目标）次数
     */
    private final int confirmTimes;

    public DelayConfirmInfo(DelayConfirmOption option, int confirmTimes) {
        Preconditions.checkArgument(option != DelayConfirmOption.DISABLED, "option cannot be DISABLED");
        Preconditions.checkArgument(confirmTimes > 1, "confirmTimes should be greater than 1");
        this.option = option;
        this.confirmTimes = confirmTimes;
    }

    @Override
    public DelayConfirmOption getOption() {
        return option;
    }

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

        DelayConfirmInfo that = (DelayConfirmInfo) o;

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
        return MoreObjects.toStringHelper(DelayConfirmInfo.class).omitNullValues()
                          .add("option", option)
                          .add("confirmTimes", confirmTimes)
                          .toString();
    }
}

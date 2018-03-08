package me.caosh.condition.domain.model.strategy.condition.delayconfirm;

import com.google.common.base.MoreObjects;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/8
 */
public class DelayConfirmParam {
    private final DelayConfirmOption option;
    private final int confirmingCount;

    public DelayConfirmParam(DelayConfirmOption option, int confirmingCount) {
        this.option = option;
        this.confirmingCount = confirmingCount;
    }

    public DelayConfirmOption getOption() {
        return option;
    }

    public int getConfirmingCount() {
        return confirmingCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DelayConfirmParam that = (DelayConfirmParam) o;

        if (confirmingCount != that.confirmingCount) {
            return false;
        }
        return option == that.option;
    }

    @Override
    public int hashCode() {
        int result = option.hashCode();
        result = 31 * result + confirmingCount;
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(DelayConfirmParam.class).omitNullValues()
                .add("option", option)
                .add("confirmingCount", confirmingCount)
                .toString();
    }
}

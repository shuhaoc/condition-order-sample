package hbec.intellitrade.conditionorder.domain.delayconfirm.count;

import com.google.common.base.MoreObjects;

/**
 * 延迟确认次数状态（值对象）
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
public class SingleDelayConfirmCount implements DelayConfirmCount {
    private final int confirmedCount;

    public SingleDelayConfirmCount(int confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public int getConfirmedCount() {
        return confirmedCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SingleDelayConfirmCount that = (SingleDelayConfirmCount) o;

        return confirmedCount == that.confirmedCount;
    }

    @Override
    public int hashCode() {
        return confirmedCount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(SingleDelayConfirmCount.class).omitNullValues()
                .add("confirmedCount", confirmedCount)
                .toString();
    }
}

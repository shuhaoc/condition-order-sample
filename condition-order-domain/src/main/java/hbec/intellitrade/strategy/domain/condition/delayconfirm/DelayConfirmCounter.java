package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import com.google.common.base.Preconditions;
import hbec.intellitrade.strategy.domain.shared.DirtyFlag;
import hbec.intellitrade.strategy.domain.shared.DynamicProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 延迟确认计数器
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/8
 */
public class DelayConfirmCounter implements DirtyFlag {
    private static final Logger logger = LoggerFactory.getLogger(DelayConfirmCounter.class);

    /**
     * 总共确认次数
     */
    private final int confirmTimes;

    /**
     * 当前确认次数
     * <p>
     * value保证非空
     */
    private DynamicProperty<Integer> confirmedCount = new DynamicProperty<>(0);

    public DelayConfirmCounter(int confirmTimes) {
        Preconditions.checkArgument(confirmTimes > 0, "Confirm times should be greater than 0");
        this.confirmTimes = confirmTimes;
    }

    public DelayConfirmCounter(int confirmTimes, int confirmedCount) {
        Preconditions.checkArgument(confirmedCount >= 0, "Confirmed count should be greater than or equals 0");
        Preconditions.checkArgument(confirmTimes >= confirmedCount, "Confirm times should be greater than confirmed count");
        this.confirmTimes = confirmTimes;
        this.confirmedCount = new DynamicProperty<>(confirmedCount);
    }

    public int getConfirmTimes() {
        return confirmTimes;
    }

    public int getConfirmedCount() {
        return confirmedCount.get();
    }

    /**
     * 增加确认次数计数
     */
    void increaseConfirmedCount() {
        confirmedCount.set(confirmedCount.get() + 1);
    }

    /**
     * 是否完成确认，即确认次数达到总次数
     *
     * @return 是否完成确认
     */
    public boolean isConfirmCompleted() {
        return confirmedCount.get() >= confirmTimes;
    }

    /**
     * 重置状态
     */
    public void reset() {
        confirmedCount.set(0);
    }

    @Override
    public boolean isDirty() {
        return confirmedCount.isDirty();
    }

    @Override
    public void clearDirty() {
        confirmedCount.clearDirty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DelayConfirmCounter that = (DelayConfirmCounter) o;

        if (confirmTimes != that.confirmTimes) {
            return false;
        }
        return confirmedCount.equals(that.confirmedCount);
    }

    @Override
    public int hashCode() {
        int result = confirmTimes;
        result = 31 * result + confirmedCount.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return confirmedCount.get() + "/" + confirmTimes;
    }
}

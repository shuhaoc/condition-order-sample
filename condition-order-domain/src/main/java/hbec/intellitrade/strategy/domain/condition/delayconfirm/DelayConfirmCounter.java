package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import com.google.common.base.Preconditions;
import hbec.intellitrade.strategy.domain.shared.DirtyFlag;
import hbec.intellitrade.strategy.domain.shared.DynamicProperty;
import org.joda.time.LocalDate;
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

    /**
     * 上一次条件达成日期，用于实现每日重置
     */
    private DynamicProperty<LocalDate> lastConfirmedDate = new DynamicProperty<>();

    public DelayConfirmCounter(int confirmTimes) {
        Preconditions.checkArgument(confirmTimes > 0, "Confirm times should be greater than 0");
        this.confirmTimes = confirmTimes;
    }

    public int getConfirmTimes() {
        return confirmTimes;
    }

    public int getConfirmedCount() {
        return confirmedCount.get();
    }

    /**
     * 判断日期变更并更新状态
     */
    void checkDateChangs() {
        boolean hasConfirmed = lastConfirmedDate.get() != null;
        boolean todayConfirmed = LocalDate.now().equals(lastConfirmedDate.get());
        if (hasConfirmed && !todayConfirmed) {
            reset();
            logger.info("Delay confirm counter reset due to date changes");
        }
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
    void reset() {
        confirmedCount.set(0);
        lastConfirmedDate.set(null);
    }

    @Override
    public boolean isDirty() {
        return confirmedCount.isDirty() || lastConfirmedDate.isDirty();
    }

    @Override
    public void clearDirty() {
        confirmedCount.clearDirty();
        lastConfirmedDate.clearDirty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DelayConfirmCounter counter = (DelayConfirmCounter) o;

        if (confirmTimes != counter.confirmTimes) {
            return false;
        }
        if (!confirmedCount.equals(counter.confirmedCount)) {
            return false;
        }
        return lastConfirmedDate.equals(counter.lastConfirmedDate);
    }

    @Override
    public int hashCode() {
        int result = confirmTimes;
        result = 31 * result + confirmedCount.hashCode();
        result = 31 * result + lastConfirmedDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return confirmedCount.get() + "/" + confirmTimes;
    }
}

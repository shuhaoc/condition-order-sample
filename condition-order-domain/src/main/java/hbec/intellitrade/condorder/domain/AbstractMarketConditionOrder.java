package hbec.intellitrade.condorder.domain;

import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.market.index.IndexInfo;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.trackindex.TrackIndexOption;
import hbec.intellitrade.strategy.domain.MarketDrivenStrategy;
import hbec.intellitrade.strategy.domain.TimeDrivenStrategy;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmParam;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DisabledDelayConfirmParam;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlParam;
import hbec.intellitrade.strategy.domain.condition.deviation.DisabledDeviationCtrlParam;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.strategy.domain.timerange.MonitorTimeRange;
import hbec.intellitrade.strategy.domain.timerange.NoneMonitorTimeRange;
import org.joda.time.LocalDateTime;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/20
 */
public abstract class AbstractMarketConditionOrder extends AbstractConditionOrder implements MarketDrivenStrategy, TimeDrivenStrategy {
    /**
     * 跟踪的指数信息，仅当跟踪指数开启时非空
     */
    private final IndexInfo trackedIndexInfo;

    /**
     * 监控时段，非空，未开启为{@link NoneMonitorTimeRange}
     */
    private final MonitorTimeRange monitorTimeRange;

    /**
     * 偏差控制参数，非空，未开启为{@link DisabledDelayConfirmParam}
     */
    private final DelayConfirmParam delayConfirmParam;

    /**
     * 偏差控制参数，非空，未开启为{@link DisabledDeviationCtrlParam}
     */
    private final DeviationCtrlParam deviationCtrlParam;

    public AbstractMarketConditionOrder(Long orderId,
                                        TradeCustomerInfo tradeCustomerInfo,
                                        OrderState orderState,
                                        SecurityInfo securityInfo,
                                        LocalDateTime expireTime,
                                        IndexInfo trackedIndexInfo,
                                        MonitorTimeRange monitorTimeRange,
                                        DelayConfirmParam delayConfirmParam,
                                        DeviationCtrlParam deviationCtrlParam) {
        super(orderId, tradeCustomerInfo, orderState, securityInfo, expireTime);
        this.trackedIndexInfo = trackedIndexInfo;
        this.delayConfirmParam = delayConfirmParam;
        this.monitorTimeRange = monitorTimeRange;
        this.deviationCtrlParam = deviationCtrlParam;
    }

    /**
     * 行情驱动策略的条件必然是行情条件
     *
     * @return 行情条件
     */
    protected abstract MarketCondition getCondition();

    @Override
    public MarketID getTrackMarketID() {
        // 跟踪指数开启时，返回指数的行情ID
        if (trackedIndexInfo != null) {
            return trackedIndexInfo.getMarketID();
        }
        // 否则跟踪交易的证券标的
        return getSecurityInfo().getMarketID();
    }

    /**
     * 获取跟踪指数选项
     *
     * @return 跟踪指数选项
     */
    public TrackIndexOption getTrackIndexOption() {
        if (trackedIndexInfo != null) {
            return TrackIndexOption.ENABLED;
        }
        return TrackIndexOption.DISABLED;
    }

    /**
     * 获取跟踪的指数信息
     *
     * @return 跟踪的指数信息，可能为空
     */
    public IndexInfo getTrackedIndexInfo() {
        return trackedIndexInfo;
    }

    public DelayConfirmParam getDelayConfirmParam() {
        return delayConfirmParam;
    }

    public MonitorTimeRange getMonitorTimeRange() {
        return monitorTimeRange;
    }

    public DeviationCtrlParam getDeviationCtrlParam() {
        return deviationCtrlParam;
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        if (getOrderState() != OrderState.ACTIVE) {
            return Signals.none();
        }

        if (!monitorTimeRange.isInRange(realTimeMarket.getArriveTime())) {
            return Signals.none();
        }

        return getCondition().onMarketTick(realTimeMarket);
    }

    @Override
    public Signal onTimeTick(LocalDateTime localDateTime) {
        if (isMonitoringState() && isExpiredAt(localDateTime)) {
            return Signals.expire();
        }
        return Signals.none();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        AbstractMarketConditionOrder that = (AbstractMarketConditionOrder) o;

        if (trackedIndexInfo != null ? !trackedIndexInfo.equals(that.trackedIndexInfo) : that.trackedIndexInfo != null) {
            return false;
        }
        return monitorTimeRange.equals(that.monitorTimeRange);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (trackedIndexInfo != null ? trackedIndexInfo.hashCode() : 0);
        result = 31 * result + monitorTimeRange.hashCode();
        return result;
    }
}

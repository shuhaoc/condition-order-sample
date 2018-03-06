package hbec.intellitrade.strategy.domain;

import com.google.common.base.Optional;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import hbec.intellitrade.strategy.domain.condition.Condition;
import org.joda.time.LocalDateTime;

/**
 * 策略实体
 * <p>
 * 策略是条件与行为的组合，策略系统将在行情、时间达到条件时，触发相应的行为
 *
 * @author caoshuhao@touker.com
 * @date 2018/1/27
 */
public interface Strategy {
    /**
     * 获取策略ID
     *
     * @return 策略ID
     */
    long getStrategyId();

    /**
     * 获取策略条件
     *
     * @return 策略条件
     */
    Condition getCondition();

    /**
     * 获取策略状态
     *
     * @return 策略状态
     */
    StrategyState getStrategyState();

    /**
     * 获取过期时间
     *
     * @return 过期时间
     */
    Optional<LocalDateTime> getExpireTime();
}

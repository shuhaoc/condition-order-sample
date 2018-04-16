package me.caosh.condition.infrastructure.tunnel;

import com.google.common.base.Optional;
import hbec.intellitrade.conditionorder.domain.ConditionOrder;

import java.util.List;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/7
 */
public interface ConditionOrderDbTunnel {
    void save(ConditionOrder conditionOrder);

    void remove(Long orderId);

    Optional<ConditionOrder> findOne(Long orderId);

    List<ConditionOrder> findAllMonitoring();
}

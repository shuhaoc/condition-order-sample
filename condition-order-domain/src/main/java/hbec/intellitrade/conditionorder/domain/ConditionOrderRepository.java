package hbec.intellitrade.conditionorder.domain;

import com.google.common.base.Optional;

import java.util.List;

/**
 * Created by caosh on 2017/8/3.
 */
public interface ConditionOrderRepository {
    void save(ConditionOrder conditionOrder);

    void update(ConditionOrder conditionOrder);

    void remove(Long orderId);

    Optional<ConditionOrder> findOne(Long orderId);

    List<ConditionOrder> findAllMonitoring();

    void updateDynamicProperties(ConditionOrder conditionOrder);
}

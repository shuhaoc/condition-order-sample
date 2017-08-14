package me.caosh.condition.infrastructure.repository;

import me.caosh.condition.domain.model.trade.EntrustOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by caosh on 2017/8/3.
 */
public interface EntrustOrderRepository {
    void save(EntrustOrder entrustOrder);

    Page<EntrustOrder> findByCustomerNo(String customerNo, Pageable pageable);
}

package me.caosh.condition.infrastructure.repository.impl;

import me.caosh.condition.infrastructure.repository.model.EntrustOrderDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by caosh on 2017/8/3.
 */
interface EntrustOrderDORepository extends CrudRepository<EntrustOrderDO, Long> {
    Page<EntrustOrderDO> findByCustomerNoOrderByEntrustIdDesc(String customerNo, Pageable pageable);
}

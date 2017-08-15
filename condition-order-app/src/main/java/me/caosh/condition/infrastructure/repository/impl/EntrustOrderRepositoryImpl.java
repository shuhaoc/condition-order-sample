package me.caosh.condition.infrastructure.repository.impl;

import me.caosh.condition.domain.model.trade.EntrustOrder;
import me.caosh.condition.infrastructure.repository.EntrustOrderRepository;
import me.caosh.condition.infrastructure.repository.model.EntrustOrderDO;
import me.caosh.condition.infrastructure.repository.model.EntrustOrderDOAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
@Repository
public class EntrustOrderRepositoryImpl implements EntrustOrderRepository {

    private final EntrustOrderDORepository entrustOrderDORepository;

    public EntrustOrderRepositoryImpl(EntrustOrderDORepository entrustOrderDORepository) {
        this.entrustOrderDORepository = entrustOrderDORepository;
    }

    @Override
    public void save(EntrustOrder entrustOrder) {
        EntrustOrderDO entrustOrderDO = EntrustOrderDOAssembler.toDO(entrustOrder);
        entrustOrderDORepository.save(entrustOrderDO);
    }

    @Override
    public Page<EntrustOrder> findByCustomerNo(String customerNo, Pageable pageable) {
        Page<EntrustOrderDO> entrustOrderDOPage = entrustOrderDORepository.findByCustomerNoOrderByEntrustIdDesc(customerNo, pageable);
        return entrustOrderDOPage.map(EntrustOrderDOAssembler::fromDO);
    }
}

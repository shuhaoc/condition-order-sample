package me.caosh.condition.infrastructure.rabbitmq.impl;

import hbec.intellitrade.trade.domain.EntrustOrder;
import me.caosh.autoasm.AutoAssemblers;
import hbec.intellitrade.condorder.domain.EntrustOrderRepository;
import me.caosh.condition.infrastructure.tunnel.impl.EntrustOrderDoRepository;
import me.caosh.condition.infrastructure.tunnel.model.EntrustOrderDO;
import org.springframework.stereotype.Repository;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
@Repository
public class EntrustOrderRepositoryImpl implements EntrustOrderRepository {

    private final EntrustOrderDoRepository entrustOrderDoRepository;

    public EntrustOrderRepositoryImpl(EntrustOrderDoRepository entrustOrderDoRepository) {
        this.entrustOrderDoRepository = entrustOrderDoRepository;
    }

    @Override
    public void save(EntrustOrder entrustOrder) {
        EntrustOrderDO entrustOrderDO = AutoAssemblers.getDefault().assemble(entrustOrder, EntrustOrderDO.class);
        entrustOrderDoRepository.save(entrustOrderDO);
    }

}

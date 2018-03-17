package me.caosh.condition.interfaces.facade.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.orders.ConditionOrderBuilder;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.dto.trade.EntrustOrderDTO;
import me.caosh.condition.infrastructure.tunnel.impl.ConditionOrderDoRepository;
import me.caosh.condition.infrastructure.tunnel.impl.EntrustOrderDoRepository;
import me.caosh.condition.infrastructure.tunnel.model.ConditionOrderDO;
import me.caosh.condition.infrastructure.tunnel.model.EntrustOrderDO;
import me.caosh.condition.interfaces.facade.ConditionOrderQueryFacade;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caosh on 2017/8/14.
 */
@Service
public class ConditionOrderQueryFacadeImpl implements ConditionOrderQueryFacade {

    private final ConditionOrderDoRepository conditionOrderDoRepository;
    private final EntrustOrderDoRepository entrustOrderDoRepository;

    public ConditionOrderQueryFacadeImpl(ConditionOrderDoRepository conditionOrderDoRepository,
                                         EntrustOrderDoRepository entrustOrderRepository) {
        this.conditionOrderDoRepository = conditionOrderDoRepository;
        this.entrustOrderDoRepository = entrustOrderRepository;
    }

    @Override
    public List<ConditionOrderDTO> listMonitoringOrders(String customerNo) {
        List<ConditionOrderDO> conditionOrderDOList = conditionOrderDoRepository.findMonitoring(customerNo);
        return  Lists.transform(conditionOrderDOList, new Function<ConditionOrderDO, ConditionOrderDTO>() {
            @Override
            public ConditionOrderDTO apply(ConditionOrderDO conditionOrderDO) {
                return assembleDataObjectToDTO(conditionOrderDO);
            }
        });
    }

    private static ConditionOrderDTO assembleDataObjectToDTO(ConditionOrderDO conditionOrderDO) {
        ConditionOrder conditionOrder = AutoAssemblers.getDefault().disassemble(conditionOrderDO, ConditionOrderBuilder.class).build();
        return AutoAssemblers.getDefault().assemble(conditionOrder, ConditionOrderDTO.class);
    }

    @Override
    public ConditionOrderDTO getConditionOrder(Long orderId) {
        ConditionOrderDO conditionOrderDO = conditionOrderDoRepository.findOne(orderId);
        return assembleDataObjectToDTO(conditionOrderDO);
    }

    @Override
    public Page<EntrustOrderDTO> listEntrustOrders(String customerNo, Pageable pageable) {
        Page<EntrustOrderDO> entrustOrderDOPage = entrustOrderDoRepository.findByCustomerNoOrderByEntrustIdDesc(customerNo, pageable);
        return entrustOrderDOPage.map(new Converter<EntrustOrderDO, EntrustOrderDTO>() {
            @Override
            public EntrustOrderDTO convert(EntrustOrderDO entrustOrderDO) {
                return AutoAssemblers.getDefault().disassemble(entrustOrderDO, EntrustOrderDTO.class);
            }
        });
    }
}

package me.caosh.condition.domain.dto.order.assembler;

import me.caosh.condition.domain.dto.order.ConditionOrderMonitorDTO;
import me.caosh.condition.domain.model.constants.OrderCommandType;
import me.caosh.condition.domain.model.order.event.ConditionOrderCommandEvent;
import me.caosh.condition.domain.model.order.event.ConditionOrderCreateCommandEvent;
import me.caosh.condition.domain.model.order.event.ConditionOrderDeleteCommandEvent;
import me.caosh.condition.domain.model.order.event.ConditionOrderUpdateCommandEvent;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionOrderCommandEventFactory {
    private static final ConditionOrderCommandEventFactory INSTANCE = new ConditionOrderCommandEventFactory();

    public static ConditionOrderCommandEventFactory getInstance() {
        return INSTANCE;
    }

    public ConditionOrderCommandEvent create(ConditionOrderMonitorDTO dto) {
        OrderCommandType orderCommandType = ValuedEnumUtil.valueOf(dto.getOrderCommandType(), OrderCommandType.class);
        switch (orderCommandType) {
            case CREATE:
                return new ConditionOrderCreateCommandEvent(ConditionOrderDTOAssembler.fromDTO(dto.getConditionOrderDTO()));
            case UPDATE:
                return new ConditionOrderUpdateCommandEvent(ConditionOrderDTOAssembler.fromDTO(dto.getConditionOrderDTO()));
            case DELETE:
                return new ConditionOrderDeleteCommandEvent(dto.getOrderId());
            default:
                throw new IllegalArgumentException("orderCommandType" + dto.getOrderCommandType());
        }
    }

    private ConditionOrderCommandEventFactory() {
    }
}

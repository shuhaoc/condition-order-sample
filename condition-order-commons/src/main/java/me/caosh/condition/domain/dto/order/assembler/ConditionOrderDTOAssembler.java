package me.caosh.condition.domain.dto.order.assembler;

import me.caosh.condition.domain.dto.market.SecurityInfoDTO;
import me.caosh.condition.domain.dto.order.ConditionDTO;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.model.constants.SecurityExchange;
import me.caosh.condition.domain.model.constants.SecurityType;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.ConditionOrderFactory;
import me.caosh.condition.domain.model.order.TradeCustomer;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;
import me.caosh.condition.domain.model.strategy.NativeStrategyInfo;
import me.caosh.condition.domain.model.strategy.StrategyInfo;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionOrderDTOAssembler {
    public static ConditionOrderDTO toDTO(ConditionOrder conditionOrder) {
        ConditionDTOBuilder conditionDTOBuilder = new ConditionDTOBuilder(conditionOrder.getCondition());
        ConditionDTO conditionDTO = conditionDTOBuilder.build();

        return ConditionOrderDTOBuilder.aConditionOrderDTO()
                .withOrderId(conditionOrder.getOrderId())
                .withUserId(conditionOrder.getCustomer().getUserId())
                .withCustomerNo(conditionOrder.getCustomer().getCustomerNo())
                .withOrderState(conditionOrder.getOrderState().getValue())
                .withSecurityInfo(SecurityInfoDTO.fromDomain(conditionOrder.getSecurityInfo()))
                .withStrategyId(conditionOrder.getStrategyInfo().getStrategyId())
                .withCondition(conditionDTO)
                .withTradePlanDTO(TradePlanDTOAssembler.fromDomain(conditionOrder.getTradePlan()))
                .build();
    }

    public static ConditionOrder fromDTO(ConditionOrderDTO dto) {
        TradeCustomer customerIdentity = new TradeCustomer(dto.getUserId(), dto.getCustomerNo());
        OrderState orderState = ValuedEnumUtil.valueOf(dto.getOrderState(), OrderState.class);
        SecurityType securityType = ValuedEnumUtil.valueOf(dto.getSecurityInfoDTO().getType(), SecurityType.class);
        SecurityExchange securityExchange = SecurityExchange.valueOf(dto.getSecurityInfoDTO().getExchange());
        SecurityInfo securityInfo = new SecurityInfo(securityType, dto.getSecurityInfoDTO().getCode(), securityExchange,
                dto.getSecurityInfoDTO().getName());
        StrategyInfo strategyInfo = ValuedEnumUtil.valueOf(dto.getStrategyId(), NativeStrategyInfo.class);
        Condition condition = new ConditionBuilder(dto.getConditionDTO()).build();
        TradePlan tradePlan = TradePlanDTOAssembler.toDomain(dto.getTradePlanDTO());
        return ConditionOrderFactory.getInstance().create(dto.getOrderId(), customerIdentity, false, orderState, securityInfo,
                strategyInfo, condition, tradePlan);
    }

    private ConditionOrderDTOAssembler() {
    }
}

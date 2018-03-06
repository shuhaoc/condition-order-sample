package me.caosh.condition.domain.dto.order.assembler;

import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import me.caosh.condition.domain.dto.market.SecurityInfoDTO;
import me.caosh.condition.domain.dto.order.ConditionDTO;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.ConditionOrderFactory;
import me.caosh.condition.domain.model.order.TradeCustomerInfo;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;
import me.caosh.condition.domain.model.strategy.condition.Condition;
import me.caosh.condition.domain.model.strategyinfo.NativeStrategyInfo;
import me.caosh.condition.domain.model.strategyinfo.StrategyInfo;

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
                .withOrderState(conditionOrder.getStrategyState().getValue())
                .withSecurityInfo(SecurityInfoDTO.fromDomain(conditionOrder.getSecurityInfo()))
                .withStrategyId(conditionOrder.getStrategyInfo().getStrategyTemplateId())
                .withCondition(conditionDTO)
                .withTradePlanDTO(TradePlanDTOAssembler.fromDomain(conditionOrder.getTradePlan()))
                .build();
    }

    public static ConditionOrder fromDTO(ConditionOrderDTO dto) {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(dto.getUserId(), dto.getCustomerNo());
        StrategyState strategyState = ValuedEnumUtil.valueOf(dto.getOrderState(), StrategyState.class);
        SecurityType securityType = ValuedEnumUtil.valueOf(dto.getSecurityInfoDTO().getType(), SecurityType.class);
        SecurityExchange securityExchange = SecurityExchange.valueOf(dto.getSecurityInfoDTO().getExchange());
        SecurityInfo securityInfo = new SecurityInfo(securityType, dto.getSecurityInfoDTO().getCode(), securityExchange,
                dto.getSecurityInfoDTO().getName());
        StrategyInfo strategyInfo = ValuedEnumUtil.valueOf(dto.getStrategyId(), NativeStrategyInfo.class);
        Condition condition = new ConditionBuilder(dto.getConditionDTO()).build();
        TradePlan tradePlan = TradePlanDTOAssembler.toDomain(securityInfo, dto.getTradePlanDTO());
        return ConditionOrderFactory.getInstance().create(dto.getOrderId(), tradeCustomerInfo, strategyState, securityInfo,
                strategyInfo, condition, tradePlan);
    }

    private ConditionOrderDTOAssembler() {
    }
}

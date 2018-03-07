package me.caosh.condition.domain.dto.order.assembler;

import hbec.intellitrade.common.ValuedEnumUtil;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.ConditionOrderFactory;
import hbec.intellitrade.condorder.domain.StrategyState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlan;
import hbec.intellitrade.strategy.domain.condition.Condition;
import me.caosh.condition.domain.dto.market.SecurityInfoDTO;
import me.caosh.condition.domain.dto.order.ConditionDTO;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;

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
                .withStrategyId(conditionOrder.getStrategyInfo().getStrategyType())
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
        TradePlan tradePlan = TradePlanDTOAssembler.toDomain(dto.getTradePlanDTO());
        return ConditionOrderFactory.getInstance().create(dto.getOrderId(), tradeCustomerInfo, strategyState, securityInfo,
                strategyInfo, condition, null, tradePlan);
    }

    private ConditionOrderDTOAssembler() {
    }
}

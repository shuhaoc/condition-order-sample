package me.caosh.condition.domain.dto.order.assembler;

import me.caosh.condition.domain.dto.market.SecurityInfoDTO;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.dto.order.ConditionOrderDTOBuilder;
import me.caosh.condition.domain.dto.order.TradePlanDTO;
import me.caosh.condition.domain.model.market.SecurityExchange;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.market.SecurityType;
import me.caosh.condition.domain.model.order.CompareCondition;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.ConditionOrderFactory;
import me.caosh.condition.domain.model.order.EntrustStrategy;
import me.caosh.condition.domain.model.order.ExchangeType;
import me.caosh.condition.domain.model.order.OrderState;
import me.caosh.condition.domain.model.order.PriceCondition;
import me.caosh.condition.domain.model.order.PriceOrder;
import me.caosh.condition.domain.model.order.TradePlan;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;
import me.caosh.condition.domain.model.strategy.NativeStrategyInfo;
import me.caosh.condition.domain.model.strategy.StrategyInfo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionOrderDTOAssembler {
    public static ConditionOrderDTO toDTO(ConditionOrder conditionOrder) {
        ConditionDTOBuilder conditionDTOBuilder = new ConditionDTOBuilder();
        conditionOrder.getCondition().accept(conditionDTOBuilder);

        return ConditionOrderDTOBuilder.aConditionOrderDTO()
                .withOrderId(conditionOrder.getOrderId())
                .withOrderState(conditionOrder.getOrderState().getValue())
                .withSecurityInfo(SecurityInfoDTO.fromDomain(conditionOrder.getSecurityInfo()))
                .withStrategyId(conditionOrder.getStrategyInfo().getStrategyId())
                .withCondition(conditionDTOBuilder.build())
                .withTradePlanDTO(TradePlanDTO.fromDomain(conditionOrder.getTradePlan()))
                .build();
    }

    public static ConditionOrder fromDTO(ConditionOrderDTO dto) {
        ConditionBuilder conditionBuilder = new ConditionBuilder();
        dto.getCondition().accept(conditionBuilder);
        Condition condition = conditionBuilder.build();

        OrderState orderState = ValuedEnumUtil.valueOf(dto.getOrderState(), OrderState.class);
        SecurityType securityType = ValuedEnumUtil.valueOf(dto.getSecurityInfo().getType(), SecurityType.class);
        SecurityExchange securityExchange = SecurityExchange.valueOf(dto.getSecurityInfo().getExchange());
        SecurityInfo securityInfo = new SecurityInfo(securityType, dto.getSecurityInfo().getCode(), securityExchange,
                dto.getSecurityInfo().getName());
        StrategyInfo strategyInfo = ValuedEnumUtil.valueOf(dto.getStrategyId(), NativeStrategyInfo.class);
        ExchangeType exchangeType = ValuedEnumUtil.valueOf(dto.getTradePlanDTO().getExchangeType(), ExchangeType.class);
        EntrustStrategy entrustStrategy = ValuedEnumUtil.valueOf(dto.getTradePlanDTO().getEntrustStrategy(), EntrustStrategy.class);
        TradePlan tradePlan = new TradePlan(exchangeType, entrustStrategy, dto.getTradePlanDTO().getNumber());
        return ConditionOrderFactory.getInstance().create(dto.getOrderId(), orderState, securityInfo, strategyInfo, condition, tradePlan);
    }

    private ConditionOrderDTOAssembler() {
    }
}

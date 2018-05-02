package me.caosh.condition.infrastructure.repository.assembler;

import hbec.intellitrade.conditionorder.domain.BidirectionalConditionOrder;
import hbec.intellitrade.conditionorder.domain.ConditionOrder;
import hbec.intellitrade.conditionorder.domain.orders.ConditionOrderBuilder;
import hbec.intellitrade.conditionorder.domain.tradeplan.BidirectionalTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.EntrustStrategy;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.autoasm.ConvertibleBuilder;
import me.caosh.autoasm.MappedClass;
import me.caosh.autoasm.util.PropertyUtils;
import me.caosh.condition.infrastructure.tunnel.model.ConditionOrderDO;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/21
 */
public class ConditionOrderDoAssembler {
    public static ConditionOrderDO assemble(ConditionOrder conditionOrder) {
        ConditionOrderDO conditionOrderDO = AutoAssemblers.getDefault()
                .assemble(conditionOrder, ConditionOrderDO.class);
        if (conditionOrder instanceof BidirectionalConditionOrder) {
            BidirectionalTradePlan tradePlan = ((BidirectionalConditionOrder) conditionOrder).getTradePlan();
            Object buyStrategy = PropertyUtils.getPathPropertySoftly(tradePlan, "buyStrategy");
            if (buyStrategy != null) {
                conditionOrderDO.setEntrustStrategy(((EntrustStrategy) buyStrategy).getValue());
            }
            Object sellStrategy = PropertyUtils.getPathPropertySoftly(tradePlan, "sellStrategy");
            if (sellStrategy != null) {
                conditionOrderDO.setEntrustStrategy(((EntrustStrategy) sellStrategy).getValue());
            }
        }
        return conditionOrderDO;
    }

    public static ConditionOrder disassemble(ConditionOrderDO conditionOrderDO) {
        Class<? extends ConvertibleBuilder> conditionBuilderClass =
                conditionOrderDO.getConditionPropertiesObj().getClass().getAnnotation(MappedClass.class).builderClass();
        ConditionOrder conditionOrder = AutoAssemblers.getDefault()
                .useBuilder(new ConditionOrderBuilder(conditionBuilderClass))
                .disassemble(conditionOrderDO)
                .build();
        return conditionOrder;
    }

    private ConditionOrderDoAssembler() {
    }

    private static final ConditionOrderDoAssembler CODE_COVERAGE = new ConditionOrderDoAssembler();
}

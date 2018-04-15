package me.caosh.condition.infrastructure.repository.assembler;

import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.orders.ConditionOrderBuilder;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.autoasm.ConvertibleBuilder;
import me.caosh.autoasm.MappedClass;
import me.caosh.condition.infrastructure.tunnel.model.ConditionOrderDO;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/21
 */
public class ConditionOrderDoAssembler {
    public static ConditionOrderDO assemble(ConditionOrder conditionOrder) {
        return AutoAssemblers.getDefault().assemble(conditionOrder, ConditionOrderDO.class);
    }

    public static ConditionOrder disassemble(ConditionOrderDO conditionOrderDO) {
        Class<? extends ConvertibleBuilder> conditionBuilderClass =
                conditionOrderDO.getConditionPropertiesObj().getClass().getAnnotation(MappedClass.class).builderClass();
        return AutoAssemblers.getDefault()
                             .useBuilder(new ConditionOrderBuilder(conditionBuilderClass))
                             .disassemble(conditionOrderDO)
                             .build();
    }

    private ConditionOrderDoAssembler() {
    }

    private static final ConditionOrderDoAssembler CODE_COVERAGE = new ConditionOrderDoAssembler();
}

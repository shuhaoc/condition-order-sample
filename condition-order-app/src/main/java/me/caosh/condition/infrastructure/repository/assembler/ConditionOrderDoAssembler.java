package me.caosh.condition.infrastructure.repository.assembler;

import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.orders.ConditionOrderBuilder;
import hbec.intellitrade.condorder.domain.orders.DelayConfirmSupportedBuilder;
import hbec.intellitrade.condorder.domain.orders.DeviationCtrlSupportedBuilder;
import hbec.intellitrade.condorder.domain.orders.price.PriceConditionFacadeBuilder;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmOption;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlOption;
import me.caosh.autoasm.AutoAssembler;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.autoasm.ConvertibleBuilder;
import me.caosh.condition.infrastructure.tunnel.model.ConditionOrderDO;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/21
 */
public class ConditionOrderDoAssembler {
    private static final ConditionOrderDoAssembler CODE_COVERAGE = new ConditionOrderDoAssembler();

    private ConditionOrderDoAssembler() {
    }

    public static ConditionOrderDO assemble(ConditionOrder conditionOrder) {
        return AutoAssemblers.getDefault().assemble(conditionOrder, ConditionOrderDO.class);
    }

    public static ConditionOrder disassemble(ConditionOrderDO conditionOrderDO) {
        AutoAssembler autoAssembler = AutoAssemblers.getDefault();

        StrategyInfo strategyInfo = autoAssembler.disassemble(conditionOrderDO.getStrategyType(),
                                                              NativeStrategyInfo.class);

        ConvertibleBuilder<? extends Condition> conditionFacadeBuilder = createConditionBuilder(strategyInfo);
        autoAssembler.disassemble(conditionOrderDO.getConditionPropertiesObj(), conditionFacadeBuilder);

        if (conditionFacadeBuilder instanceof DelayConfirmSupportedBuilder) {
            DelayConfirmSupportedBuilder delayConfirmSupportedBuilder = (DelayConfirmSupportedBuilder) conditionFacadeBuilder;
            delayConfirmSupportedBuilder.getDelayConfirm()
                                        .setOption(autoAssembler.disassemble(conditionOrderDO.getDelayConfirmOption(),
                                                                             DelayConfirmOption.class))
                                        .setConfirmTimes(conditionOrderDO.getDelayConfirmTimes());
        }

        if (conditionFacadeBuilder instanceof DeviationCtrlSupportedBuilder) {
            DeviationCtrlSupportedBuilder deviationCtrlSupportedBuilder = (DeviationCtrlSupportedBuilder) conditionFacadeBuilder;
            deviationCtrlSupportedBuilder.getDeviationCtrl()
                                         .setOption(autoAssembler.disassemble(conditionOrderDO.getDeviationCtrlOption(),
                                                                              DeviationCtrlOption.class))
                                         .setLimitPercent(conditionOrderDO.getDeviationLimitPercent());
        }

        return autoAssembler.disassemble(conditionOrderDO, ConditionOrderBuilder.class)
                            .setCondition(conditionFacadeBuilder.build())
                            .build();
    }

    private static ConvertibleBuilder<? extends Condition> createConditionBuilder(StrategyInfo strategyInfo) {
        if (strategyInfo == NativeStrategyInfo.PRICE) {
            return new PriceConditionFacadeBuilder();
        }
        throw new IllegalArgumentException("strategyInfo=" + strategyInfo);
    }
}

package hbec.commons.domain.intellitrade.util;

import com.google.common.base.Converter;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import me.caosh.autoasm.AutoAssembler;
import me.caosh.autoasm.AutoAssemblerBuilder;
import hbec.commons.domain.intellitrade.condorder.ConditionOrderDTO;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/23
 */
public class ConditionOrderAssemblers {
    public static AutoAssembler dtoSupported() {
        return DTO_SUPPORTED;
    }

    public static AutoAssembler DTO_SUPPORTED = new AutoAssemblerBuilder()
            .registerConverter(ConditionOrder.class,
                               ConditionOrderDTO.class,
                               new Converter<ConditionOrder, ConditionOrderDTO>() {
                                   @Override
                                   protected ConditionOrderDTO doForward(ConditionOrder conditionOrder) {
                                       return ConditionOrderDtoAssembler.assemble(conditionOrder);
                                   }

                                   @Override
                                   protected ConditionOrder doBackward(ConditionOrderDTO conditionOrderDTO) {
                                       return ConditionOrderDtoAssembler.disassemble(conditionOrderDTO);
                                   }
                               }).build();

    private ConditionOrderAssemblers() {
    }

    private static final ConditionOrderAssemblers CODE_COVERAGE = new ConditionOrderAssemblers();
}

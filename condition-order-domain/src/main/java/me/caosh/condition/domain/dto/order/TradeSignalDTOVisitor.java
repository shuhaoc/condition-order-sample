package me.caosh.condition.domain.dto.order;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public interface TradeSignalDTOVisitor {
    void visitGeneralSignalDTO(GeneralSignalDTO generalSignalDTO);
}

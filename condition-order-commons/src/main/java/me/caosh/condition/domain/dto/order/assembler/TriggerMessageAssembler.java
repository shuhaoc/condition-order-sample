package me.caosh.condition.domain.dto.order.assembler;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.strategy.domain.signal.Signal;
import me.caosh.condition.domain.dto.market.RealTimeMarketDTO;
import me.caosh.condition.domain.dto.market.assembler.RealTimeMarketDTOAssembler;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.dto.order.TradeSignalDTO;
import me.caosh.condition.domain.dto.order.TriggerMessageDTO;
import me.caosh.condition.domain.model.order.TriggerMessage;

/**
 * Created by caosh on 2017/8/30.
 */
public class TriggerMessageAssembler {
    public static TriggerMessageDTO toDTO(TriggerMessage triggerMessage) {
        TradeSignalDTO tradeSignalDTO = new SignalDTOBuilder(triggerMessage.getSignal()).build();
        ConditionOrderDTO conditionOrderDTO = null;//AutoAssemblers.getDefault().assemble(triggerMessage.getConditionOrder(), ConditionOrderDTO.class);
        RealTimeMarketDTO realTimeMarketDTO = null;
        if (triggerMessage.getRealTimeMarket().isPresent()) {
            realTimeMarketDTO = RealTimeMarketDTOAssembler.toDTO(triggerMessage.getRealTimeMarket().get());
        }
        return new TriggerMessageDTO(tradeSignalDTO, conditionOrderDTO, realTimeMarketDTO);
    }

    public static TriggerMessage fromDTO(TriggerMessageDTO triggerMessageDTO) {
        Signal signal = new TradeSignalBuilder(triggerMessageDTO.getTradeSignal()).build();
        ConditionOrder conditionOrder = null;//AutoAssemblers.getDefault().disassemble(triggerMessageDTO.getConditionOrderDTO(), ConditionOrder.class);
        RealTimeMarket realTimeMarket = null;
        if (triggerMessageDTO.getRealTimeMarketDTO() != null) {
            realTimeMarket = RealTimeMarketDTOAssembler.fromDTO(triggerMessageDTO.getRealTimeMarketDTO());
        }
        return new TriggerMessage(signal, conditionOrder, realTimeMarket);
    }

    private TriggerMessageAssembler() {
    }
}

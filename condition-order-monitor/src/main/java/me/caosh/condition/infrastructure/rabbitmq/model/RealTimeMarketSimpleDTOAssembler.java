package me.caosh.condition.infrastructure.rabbitmq.model;

import com.google.common.collect.Maps;
import me.caosh.condition.domain.model.market.MarketID;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityType;

import java.util.Map;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class RealTimeMarketSimpleDTOAssembler {
    public static RealTimeMarket fromDTO(SecurityType securityType, RealTimeMarketSimpleDTO realTimeMarketSimpleDTO) {
        MarketID marketID = new MarketID(securityType, realTimeMarketSimpleDTO.getC());
        return new RealTimeMarket(marketID, realTimeMarketSimpleDTO.getP());
    }

    public static Map<String, RealTimeMarket> transformMap(SecurityType securityType, Map<String, RealTimeMarketSimpleDTO> realTimeMarketDTOMap) {
        return Maps.transformValues(realTimeMarketDTOMap, realTimeMarketSimpleDTO -> fromDTO(securityType, realTimeMarketSimpleDTO));
    }
}

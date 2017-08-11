package me.caosh.condition.infrastructure.rabbitmq.model;

import com.google.common.collect.Maps;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityExchange;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.market.SecurityType;

import java.util.Map;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class RealTimeMarketSimpleDTOAssembler {
    public static RealTimeMarket fromDTO(SecurityType securityType, RealTimeMarketSimpleDTO realTimeMarketSimpleDTO) {
        SecurityExchange exchange = SecurityExchange.valueOf(realTimeMarketSimpleDTO.getS());
        SecurityInfo securityInfo = new SecurityInfo(securityType,
                realTimeMarketSimpleDTO.getC(),
                exchange,
                realTimeMarketSimpleDTO.getN());
        return new RealTimeMarket(securityInfo, realTimeMarketSimpleDTO.getP());
    }

    public static Map<String, RealTimeMarket> transformMap(SecurityType securityType, Map<String, RealTimeMarketSimpleDTO> realTimeMarketDTOMap) {
        return Maps.transformValues(realTimeMarketDTOMap, realTimeMarketSimpleDTO -> fromDTO(securityType, realTimeMarketSimpleDTO));
    }
}

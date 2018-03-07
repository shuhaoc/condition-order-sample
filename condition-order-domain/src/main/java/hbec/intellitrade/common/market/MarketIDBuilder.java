package hbec.intellitrade.common.market;

import hbec.intellitrade.common.security.SecurityType;
import me.caosh.autoasm.ConvertibleBuilder;

public class MarketIDBuilder implements ConvertibleBuilder<MarketID> {
    private SecurityType type;
    private String code;

    public MarketIDBuilder setType(SecurityType type) {
        this.type = type;
        return this;
    }

    public MarketIDBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public MarketID build() {
        return new MarketID(type, code);
    }
}
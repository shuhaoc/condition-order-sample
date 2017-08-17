package me.caosh.condition.domain.model.market;

import me.caosh.condition.domain.model.constants.SecurityType;

/**
 * Created by caosh on 2017/8/1.
 */
public class MarketID {
    private final SecurityType type;
    private final String code;

    public SecurityType getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public MarketID(SecurityType type, String code) {

        this.type = type;
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarketID)) return false;

        MarketID marketID = (MarketID) o;

        if (type != marketID.type) return false;
        return !(code != null ? !code.equals(marketID.code) : marketID.code != null);

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return type.name() + "/" + code;
    }
}

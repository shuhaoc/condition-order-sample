package me.caosh.condition.domain.model.market;

import me.caosh.condition.domain.model.trade.SecurityID;

/**
 * Created by caosh on 2017/8/1.
 */
public class SecurityInfo {
    private final SecurityType type;
    private final String code;
    private final SecurityExchange exchange;
    private final String name;

    public SecurityInfo(SecurityType type, String code, SecurityExchange exchange, String name) {
        this.type = type;
        this.code = code;
        this.exchange = exchange;
        this.name = name;
    }

    public SecurityType getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public SecurityExchange getExchange() {
        return exchange;
    }

    public String getName() {
        return name;
    }

    public MarketID asMarketID() {
        return new MarketID(type, code);
    }

    public SecurityID asSecurityID() {
        return new SecurityID(code, exchange);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecurityInfo)) return false;

        SecurityInfo that = (SecurityInfo) o;

        if (type != that.type) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        return exchange == that.exchange;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (exchange != null ? exchange.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return type.name() + "(" + code + "." + exchange.name() + "," + name + ")";
    }
}

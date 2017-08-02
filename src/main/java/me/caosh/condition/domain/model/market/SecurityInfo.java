package me.caosh.condition.domain.model.market;

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

    @Override
    public String toString() {
        return type.name() + "(" + code + "." + exchange.name() + "," + name + ")";
    }
}

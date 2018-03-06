package hbec.intellitrade.common.security;

import com.google.common.base.Preconditions;
import hbec.intellitrade.common.market.MarketID;
import me.caosh.condition.domain.model.trade.SecurityID;

/**
 * 证券信息，含指数
 * 
 * Created by caosh on 2017/8/1.
 */
public class SecurityInfo {
    private final SecurityType type;
    private final String code;
    private final SecurityExchange exchange;
    private final String name;

    public SecurityInfo(SecurityType type, String code, SecurityExchange exchange, String name) {
        Preconditions.checkNotNull(type, "type cannot be null");
        Preconditions.checkNotNull(code, "code cannot be null");
        Preconditions.checkNotNull(exchange, "exchange cannot be null");
        Preconditions.checkNotNull(name, "name cannot be null");
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

    public MarketID getMarketID() {
        return new MarketID(type, code);
    }

    public SecurityID getSecurityID() {
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
        // 由于证券名称可能会变，比如XD，但不影响证券等于比较，因此这里没有比较name
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

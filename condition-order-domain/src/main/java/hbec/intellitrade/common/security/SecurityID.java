package hbec.intellitrade.common.security;

/**
 * 可交易证券ID，由交易所和证券代码组成
 * <p>
 * Created by caosh on 2017/8/10.
 */
public class SecurityID {
    private final String code;
    private final SecurityExchange exchange;

    public SecurityID(String code, SecurityExchange exchange) {
        this.code = code;
        this.exchange = exchange;
    }

    public String getCode() {
        return code;
    }

    public SecurityExchange getExchange() {
        return exchange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecurityID)) return false;

        SecurityID that = (SecurityID) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        return exchange == that.exchange;

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (exchange != null ? exchange.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return code + "." + exchange.name();
    }
}

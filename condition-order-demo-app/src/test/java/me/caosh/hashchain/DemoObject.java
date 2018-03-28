package me.caosh.hashchain;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/19
 */
public class DemoObject {
    private Integer direction;
    private String code;
    private String exchange;
    private BigDecimal price;
    private Integer number;
    private int chainedHash;

    public DemoObject(int prevChainedHash,
                      Integer direction,
                      String code,
                      String exchange,
                      BigDecimal price,
                      Integer number) {
        this.direction = direction;
        this.code = code;
        this.exchange = exchange;
        this.price = price;
        this.number = number;
        this.chainedHash = calculateHash(prevChainedHash, price, number, direction, code, exchange);
    }

    public Integer getDirection() {
        return direction;
    }

    public String getCode() {
        return code;
    }

    public String getExchange() {
        return exchange;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getNumber() {
        return number;
    }

    public int getChainedHash() {
        return chainedHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DemoObject that = (DemoObject) o;

        if (chainedHash != that.chainedHash) return false;
        if (direction != null ? !direction.equals(that.direction) : that.direction != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (exchange != null ? !exchange.equals(that.exchange) : that.exchange != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        return number != null ? number.equals(that.number) : that.number == null;
    }

    @Override
    public int hashCode() {
        int result = direction != null ? direction.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (exchange != null ? exchange.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + chainedHash;
        return result;
    }

    public static int calculateHash(Object... arguments) {
        int result = 0;
        for (Object argument : arguments) {
            if (argument != null) {
                result = 31 * result + argument.hashCode();
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(DemoObject.class).omitNullValues()
                          .add("direction", direction)
                          .add("code", code)
                          .add("exchange", exchange)
                          .add("price", price)
                          .add("number", number)
                          .add("chainedHash", chainedHash)
                          .toString();
    }
}

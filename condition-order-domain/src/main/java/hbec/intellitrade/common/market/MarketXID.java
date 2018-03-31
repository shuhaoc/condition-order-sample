package hbec.intellitrade.common.market;

import com.google.common.base.Preconditions;
import hbec.intellitrade.strategy.domain.container.BucketKey;

import java.util.Objects;

/**
 * 行情扩展ID，由类别和证券代码组成，支持指数
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/31
 */
public class MarketXID implements BucketKey {
    private final MarketType type;
    private final MarketSource source;
    private final String code;

    public MarketXID(MarketType type, MarketSource source, String code) {
        Preconditions.checkNotNull(type, "type cannot be null");
        Preconditions.checkNotNull(source, "source cannot be null");
        Preconditions.checkNotNull(code, "code cannot be null");

        this.type = type;
        this.source = source;
        this.code = code;
    }

    public MarketType getType() {
        return type;
    }

    public MarketSource getSource() {
        return source;
    }

    public String getCode() {
        return code;
    }

    public MarketID getMarketID() {
        return new MarketID(type, code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarketXID marketXID = (MarketXID) o;
        return type == marketXID.type &&
                source == marketXID.source &&
                Objects.equals(code, marketXID.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, source, code);
    }

    @Override
    public String toString() {
        return type.name() + "/" + code + "." + source;
    }
}

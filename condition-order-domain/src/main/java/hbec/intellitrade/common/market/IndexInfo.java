package hbec.intellitrade.common.market;

/**
 * 指数信息
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/16
 */
public class IndexInfo {
    /**
     * 指数来源
     */
    private final MarketSource source;

    /**
     * 指数代码
     */
    private final String code;

    /**
     * 指数名称
     */
    private final String name;

    public IndexInfo(MarketSource source, String code, String name) {
        this.source = source;
        this.code = code;
        this.name = name;
    }

    public MarketSource getSource() {
        return source;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 获取行情ID
     *
     * @return 行情ID
     */
    public MarketID getMarketID() {
        return new MarketID(MarketType.INDEX, code);
    }

    @Override
    public boolean equals(Object o) {
        // 由于证券名称可能会变，比如XD，但不影响证券等于比较，因此这里没有比较name
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IndexInfo indexInfo = (IndexInfo) o;

        if (source != indexInfo.source) {
            return false;
        }
        return code.equals(indexInfo.code);
    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Index(" + code + "." + source + "," + name + ")";
    }
}

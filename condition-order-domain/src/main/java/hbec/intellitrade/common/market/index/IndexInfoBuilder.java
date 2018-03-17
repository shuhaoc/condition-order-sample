package hbec.intellitrade.common.market.index;

import me.caosh.autoasm.ConvertibleBuilder;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/17
 */
public class IndexInfoBuilder implements ConvertibleBuilder<IndexInfo> {
    private IndexSource source;
    private String code;
    private String name;

    public IndexInfoBuilder setSource(IndexSource source) {
        this.source = source;
        return this;
    }

    public IndexInfoBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    public IndexInfoBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IndexInfo build() {
        return new IndexInfo(source, code, name);
    }
}

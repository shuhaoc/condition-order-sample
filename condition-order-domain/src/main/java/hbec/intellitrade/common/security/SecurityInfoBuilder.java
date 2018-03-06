package hbec.intellitrade.common.security;

import me.caosh.autoasm.ConvertibleBuilder;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/4
 */
public class SecurityInfoBuilder implements ConvertibleBuilder<SecurityInfo> {
    private SecurityType type;
    private String code;
    private SecurityExchange exchange;
    private String name;

    public SecurityInfoBuilder setType(SecurityType type) {
        this.type = type;
        return this;
    }

    public SecurityInfoBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    public SecurityInfoBuilder setExchange(SecurityExchange exchange) {
        this.exchange = exchange;
        return this;
    }

    public SecurityInfoBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public SecurityInfo build() {
        return new SecurityInfo(type, code, exchange, name);
    }
}
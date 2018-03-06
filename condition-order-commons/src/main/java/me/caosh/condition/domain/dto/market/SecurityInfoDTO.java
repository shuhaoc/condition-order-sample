package me.caosh.condition.domain.dto.market;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.common.ValuedEnumUtil;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class SecurityInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer type;
    private String code;
    private String exchange;
    private String name;

    public SecurityInfoDTO() {
    }

    public SecurityInfoDTO(Integer type, String code, String exchange, String name) {
        this.type = type;
        this.code = code;
        this.exchange = exchange;
        this.name = name;
    }

    public static SecurityInfoDTO fromDomain(SecurityInfo securityInfo) {
        return new SecurityInfoDTO(securityInfo.getType().getValue(),
                securityInfo.getCode(),
                securityInfo.getExchange().name(),
                securityInfo.getName());
    }

    public static SecurityInfo toDomain(SecurityInfoDTO securityInfoDTO) {
        SecurityType securityType = ValuedEnumUtil.valueOf(securityInfoDTO.getType(), SecurityType.class);
        SecurityExchange securityExchange = SecurityExchange.valueOf(securityInfoDTO.getName());
        return new SecurityInfo(securityType, securityInfoDTO.getCode(), securityExchange, securityInfoDTO.getName());
    }

    public Integer getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getExchange() {
        return exchange;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(SecurityInfoDTO.class).omitNullValues()
                .addValue(SecurityInfoDTO.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("type", type)
                .add("code", code)
                .add("exchange", exchange)
                .add("name", name)
                .toString();
    }
}

package me.caosh.condition.domain.dto.market;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityInfo;
import me.caosh.autoasm.Convertible;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
@Convertible
public class SecurityInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Range(min = 3, max = 4)
    private Integer type;
    @NotBlank
    @Length(min = 6, max = 6)
    private String code;
    @NotBlank
    @Length(min = 2, max = 2)
    private String exchange;
    @NotBlank
    @Length(min = 1, max = 4)
    private String name;

    public SecurityInfoDTO() {
    }

    @Deprecated
    public SecurityInfoDTO(Integer type, String code, String exchange, String name) {
        this.type = type;
        this.code = code;
        this.exchange = exchange;
        this.name = name;
    }

    @Deprecated
    public static SecurityInfoDTO fromDomain(SecurityInfo securityInfo) {
        return new SecurityInfoDTO(securityInfo.getType().getValue(),
                securityInfo.getCode(),
                securityInfo.getExchange().name(),
                securityInfo.getName());
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(SecurityInfoDTO.class).omitNullValues()
                .add("type", type)
                .add("code", code)
                .add("exchange", exchange)
                .add("name", name)
                .toString();
    }
}

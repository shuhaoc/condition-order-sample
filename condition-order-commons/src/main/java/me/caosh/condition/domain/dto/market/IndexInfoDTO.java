package me.caosh.condition.domain.dto.market;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.Convertible;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/17
 */
@Convertible
public class IndexInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Length(min = 2, max = 4)
    private String source;
    @NotBlank
    @Length(min = 6, max = 6)
    private String code;
    @NotBlank
    @Length(min = 1, max = 128)
    private String name;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(IndexInfoDTO.class).omitNullValues()
                          .add("source", source)
                          .add("code", code)
                          .add("name", name)
                          .toString();
    }
}

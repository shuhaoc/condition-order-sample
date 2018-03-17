package me.caosh.condition.domain.dto.market;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.Convertible;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/17
 */
@Convertible
public class TrackedIndexDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Range(min = 0, max = 1)
    private Integer option;
    @NotBlank
    @Length(min = 2, max = 4)
    private String source;
    @NotBlank
    @Length(min = 6, max = 6)
    private String code;
    @NotBlank
    @Length(min = 1, max = 128)
    private String name;

    public Integer getOption() {
        return option;
    }

    public void setOption(Integer option) {
        this.option = option;
    }

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
        return MoreObjects.toStringHelper(TrackedIndexDTO.class).omitNullValues()
                          .add("option", option)
                          .add("source", source)
                          .add("code", code)
                          .add("name", name)
                          .toString();
    }
}

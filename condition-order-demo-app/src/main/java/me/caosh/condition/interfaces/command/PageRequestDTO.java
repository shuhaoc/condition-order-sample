package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/15.
 */
public class PageRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer page;
    private Integer size;

    public PageRequestDTO() {
    }

    public PageRequestDTO(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Pageable asPageable() {
        return new PageRequest(page, size);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("pageNo", page)
                .add("pageSize", size)
                .toString();
    }
}

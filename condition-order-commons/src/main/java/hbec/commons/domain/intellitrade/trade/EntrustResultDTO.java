package hbec.commons.domain.intellitrade.trade;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/8
 */
public class EntrustResultDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer entrustState;
    private String entrustMessage;
    private Integer entrustCode;

    public Integer getEntrustState() {
        return entrustState;
    }

    public void setEntrustState(Integer entrustState) {
        this.entrustState = entrustState;
    }

    public String getEntrustMessage() {
        return entrustMessage;
    }

    public void setEntrustMessage(String entrustMessage) {
        this.entrustMessage = entrustMessage;
    }

    public Integer getEntrustCode() {
        return entrustCode;
    }

    public void setEntrustCode(Integer entrustCode) {
        this.entrustCode = entrustCode;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(EntrustResultDTO.class).omitNullValues()
                .add("entrustState", entrustState)
                .add("entrustMessage", entrustMessage)
                .add("entrustCode", entrustCode)
                .toString();
    }
}

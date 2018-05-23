package hbec.commons.domain.intellitrade.conditionorder;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.Convertible;
import me.caosh.autoasm.FieldMapping;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/5/2
 */
@Convertible
public class BidirectionalTradePlanDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String stockHolderNo;
    @NotNull
    @Range(min = 1, max = 11)
    private Integer buyStrategy;
    @NotNull
    @Range(min = 1, max = 11)
    private Integer sellStrategy;
    @NotNull
    @Range(min = 0, max = 1)
    @FieldMapping(mappedProperty = "tradeNumber.entrustMethod")
    private Integer entrustMethod;
    @FieldMapping(mappedProperty = "tradeNumber.number")
    private Integer entrustNumber;
    @FieldMapping(mappedProperty = "tradeNumber.amount")
    private BigDecimal entrustAmount;
    @Min(0)
    private Integer orderType;
    @NotBlank
    private String nodeInfo;

    public String getStockHolderNo() {
        return stockHolderNo;
    }

    public void setStockHolderNo(String stockHolderNo) {
        this.stockHolderNo = stockHolderNo;
    }

    public Integer getBuyStrategy() {
        return buyStrategy;
    }

    public void setBuyStrategy(Integer buyStrategy) {
        this.buyStrategy = buyStrategy;
    }

    public Integer getSellStrategy() {
        return sellStrategy;
    }

    public void setSellStrategy(Integer sellStrategy) {
        this.sellStrategy = sellStrategy;
    }

    public Integer getEntrustMethod() {
        return entrustMethod;
    }

    public void setEntrustMethod(Integer entrustMethod) {
        this.entrustMethod = entrustMethod;
    }

    public Integer getEntrustNumber() {
        return entrustNumber;
    }

    public void setEntrustNumber(Integer entrustNumber) {
        this.entrustNumber = entrustNumber;
    }

    public BigDecimal getEntrustAmount() {
        return entrustAmount;
    }

    public void setEntrustAmount(BigDecimal entrustAmount) {
        this.entrustAmount = entrustAmount;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getNodeInfo() {
        return nodeInfo;
    }

    public void setNodeInfo(String nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(BidirectionalTradePlanDTO.class).omitNullValues()
                .add("stockHolderNo", stockHolderNo)
                .add("buyStrategy", buyStrategy)
                .add("sellStrategy", sellStrategy)
                .add("entrustMethod", entrustMethod)
                .add("entrustNumber", entrustNumber)
                .add("entrustAmount", entrustAmount)
                .add("orderType", orderType)
                .add("nodeInfo", nodeInfo)
                .toString();
    }
}

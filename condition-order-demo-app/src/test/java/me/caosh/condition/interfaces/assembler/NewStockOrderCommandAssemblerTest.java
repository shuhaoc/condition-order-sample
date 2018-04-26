package me.caosh.condition.interfaces.assembler;

import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.orders.newstock.NewStockOrder;
import hbec.intellitrade.conditionorder.domain.orders.newstock.NewStockPurchaseCondition;
import me.caosh.condition.interfaces.command.NewStockOrderCreateCommand;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/15
 */
public class NewStockOrderCommandAssemblerTest {
    @Test
    public void test() throws Exception {
        NewStockOrderCreateCommand createCommand = new NewStockOrderCreateCommand();

        createCommand.setPurchaseTime("09:30:00");

        createCommand.setExpireTime(LocalDateTime.parse("2018-04-29T15:00:00").toDate());

        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        NewStockOrder newStockOrder = NewStockOrderCommandAssembler.assemble(123L, tradeCustomerInfo, createCommand);

        NewStockOrder expected = new NewStockOrder(123L,
                new TradeCustomerInfo(303348, "010000061086"),
                OrderState.ACTIVE,
                new NewStockPurchaseCondition(LocalTime.parse("09:30:00"), false, 0),
                new LocalDateTime("2018-04-29T15:00:00"));
        assertEquals(newStockOrder, expected);
    }
}

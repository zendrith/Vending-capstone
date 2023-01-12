package com.techelevator.money;

import org.junit.Assert;
import org.junit.Test;
import java.math.BigDecimal;

import static com.techelevator.money.MoneyManagement.getCurrentMoney;
import static com.techelevator.money.MoneyManagement.returnChange;


public class MoneyManagementTest {

    @Test
    public void testGetCurrentMoney() {
        Assert.assertEquals(0, BigDecimal.ZERO.compareTo(getCurrentMoney()));
    }

    @Test
    public void testFeedMoney() {
        MoneyManagement.feedMoney(10);
        Assert.assertEquals(BigDecimal.TEN, getCurrentMoney());
        MoneyManagement.feedMoney(5);
        Assert.assertEquals(new BigDecimal("15"), getCurrentMoney());
    }

    @Test
    public void testPayMoney() {
        MoneyManagement.feedMoney(15);
        MoneyManagement.payMoney(BigDecimal.ONE);
        Assert.assertEquals(new BigDecimal("14.00"), getCurrentMoney());
        MoneyManagement.payMoney(new BigDecimal(12));
        Assert.assertEquals(new BigDecimal("2.00"), getCurrentMoney());
    }

    @Test
    public void testEmptyCurrentMoney() {
        MoneyManagement.emptyCurrentMoney();
        Assert.assertEquals(new BigDecimal("0.00"), getCurrentMoney());
    }

    @Test
    public void testReturnChange() {
        MoneyManagement.feedMoney(3);
        Assert.assertEquals("12 quarters, 0 dimes, 0 nickels", returnChange() );
    }
}
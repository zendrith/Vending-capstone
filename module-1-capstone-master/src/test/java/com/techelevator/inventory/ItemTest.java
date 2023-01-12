package com.techelevator.inventory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ItemTest {
    Item test1 = new Item("test1", new BigDecimal("1.50"), ItemType.Candy);
    Item test2 = new Item("test2", new BigDecimal(".75"), ItemType.Chip);

    @Test
    public void getName() {
        Assert.assertEquals("test1", test1.getName());
        Assert.assertEquals("test2", test2.getName());
    }

    @Test
    public void getPrice() {
        Assert.assertEquals(new BigDecimal("1.50"), test1.getPrice());
        Assert.assertEquals(new BigDecimal(".75"), test2.getPrice());
    }

    @Test
    public void getQuantity() {
        Assert.assertEquals("5", test2.getQuantity());
    }

    @Test
    public void getAmountSold() {
        test2.reduceStock();
        Assert.assertEquals("1", test2.getAmountSold());
    }

    @Test
    public void reduceStock() {
        test1.reduceStock();
        test1.reduceStock();
        Assert.assertEquals("3", test1.getQuantity());
    }

    @Test
    public void message() {
        Assert.assertEquals("Munch Munch, Yum!", test1.message());
        Assert.assertEquals("Crunch Crunch, Yum!", test2.message());
    }
}
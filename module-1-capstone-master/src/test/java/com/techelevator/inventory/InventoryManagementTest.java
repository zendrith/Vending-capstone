package com.techelevator.inventory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class InventoryManagementTest {



    @Test
    public void testRestock() {
        File test = new File("vendingmachine.csv");
        InventoryManagement.restock(test);
        Assert.assertEquals(16, InventoryManagement.currentInventory.size());
        Assert.assertTrue(InventoryManagement.currentInventory.containsKey("A1"));
        Assert.assertTrue(InventoryManagement.currentInventory.containsKey("D4"));
        Assert.assertEquals("Cowtales", InventoryManagement.currentInventory.get("B2").getName());
        Assert.assertEquals(new BigDecimal("1.50"), InventoryManagement.currentInventory.get("C3").getPrice());
        Assert.assertEquals("Chew Chew, Yum!", InventoryManagement.currentInventory.get("D3").message());

    }

    @Test
    public void testDisplayInventoryString() {
        Assert.assertTrue(InventoryManagement.displayInventoryString().contains("Grain Waves"));
        Assert.assertTrue(InventoryManagement.displayInventoryString().contains("Cola"));
        Assert.assertTrue(InventoryManagement.displayInventoryString().contains("Wonka Bar"));
        Assert.assertTrue(InventoryManagement.displayInventoryString().contains("Chiclets"));
    }

    @Test
    public void testSelectItem() {
       //InventoryManagement.restock(test);
        InventoryManagement.selectItem("A1");
        Assert.assertEquals("4", InventoryManagement.currentInventory.get("A1").getQuantity());
        InventoryManagement.selectItem("A1");
        InventoryManagement.selectItem("A1");
        InventoryManagement.selectItem("A1");
        InventoryManagement.selectItem("A1");
        Assert.assertEquals("SOLD OUT", InventoryManagement.currentInventory.get("A1").getQuantity());
    }
}
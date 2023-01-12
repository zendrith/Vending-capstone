package com.techelevator.inventory;

import java.math.BigDecimal;

public class Item {
    final int MAX_QUANTITY = 5;
    private String name;
    private BigDecimal price;
    private int quantity = MAX_QUANTITY;
    private ItemType type;

    public Item(String name, BigDecimal price, ItemType type){
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getName() {return name;}

    public BigDecimal getPrice() {return price;}

    public String getQuantity() {
        if(quantity<1)return "SOLD OUT";
        return String.valueOf(quantity);
    }
    public String getAmountSold(){
        int sold = MAX_QUANTITY- quantity;
        return String.valueOf(sold);
    }
    public void reduceStock(){
        if(quantity>0) this.quantity = quantity-1;
    }
    public static void soldOut(){
        System.out.println("Item SOLD OUT, please select another item");
    }

    public String message(){
        return type.message;
    }

}

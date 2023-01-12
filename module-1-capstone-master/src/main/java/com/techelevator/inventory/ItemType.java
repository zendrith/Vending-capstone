package com.techelevator.inventory;

public enum ItemType {
    Candy("Munch Munch, Yum!"),
    Gum("Chew Chew, Yum!"),
    Chip("Crunch Crunch, Yum!"),
    Drink("Glug Glug, Yum!");

    final String message;

    ItemType(String message){
        this.message = message;
    }
}

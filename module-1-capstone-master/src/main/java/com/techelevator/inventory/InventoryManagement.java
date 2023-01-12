package com.techelevator.inventory;

import com.techelevator.logging.Log;
import com.techelevator.money.MoneyManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class InventoryManagement {
    public static Map<String, Item> currentInventory = new TreeMap<>();
    public static void restock(File inventorySource){

        final int itemLocation = 0;
        final int itemName = 1;
        final int itemPrice = 2;
        final int itemType = 3;

        final Map<String, ItemType> mapOfTypes = Map.of("Candy", ItemType.Candy, "Chip", ItemType.Chip, "Drink", ItemType.Drink, "Gum", ItemType.Gum);

        try (Scanner inputFile = new Scanner(inventorySource)){ //Builds map of items from the CSV file, uses map of enums to get around if statements
            while(inputFile.hasNextLine()){
                String nextLine = inputFile.nextLine();
                String[] arrayOfLine = nextLine.split("\\|");
                currentInventory.put(arrayOfLine[itemLocation], new Item(arrayOfLine[itemName],new BigDecimal(arrayOfLine[itemPrice]),mapOfTypes.get(arrayOfLine[itemType]) ) );
            }
        }catch (FileNotFoundException fnf){
            System.out.println("Vending startup inventory file not found");
        }
    }

    public static void displayInventory(){
        currentInventory.entrySet().forEach(item -> System.out.printf(item.getKey() + " | %-20s | $%-4s | %5s\n", item.getValue().getName(), item.getValue().getPrice(),item.getValue().getQuantity()));

    }
    public static String displayInventoryString(){
        StringBuilder inventory = new StringBuilder();
        currentInventory.entrySet().forEach(item ->  inventory.append(item.getKey()).append(" | ").append(item.getValue().getName()).append(" | ").append(item.getValue().getPrice()).append(" | ").append(item.getValue().getQuantity()).append("\n"));
        return inventory.toString();
    }

    public static void selectItem(String itemLocation) {
        String itemLocationSafe = itemLocation.toUpperCase();
        if(currentInventory.containsKey(itemLocationSafe)){
                currentInventory.get(itemLocationSafe).reduceStock();
                MoneyManagement.payMoney(currentInventory.get(itemLocationSafe).getPrice());
                Log.countSales(currentInventory.get(itemLocationSafe).getPrice());
            Log.writeLog("Purchase Item", currentInventory.get(itemLocationSafe).getPrice(), MoneyManagement.getCurrentMoney());
        }
        else throw new InputMismatchException();
    }
}

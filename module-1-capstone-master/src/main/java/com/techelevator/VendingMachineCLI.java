package com.techelevator;

import com.techelevator.inventory.InventoryManagement;
import com.techelevator.inventory.Item;
import com.techelevator.money.MoneyManagement;
import com.techelevator.view.Menu;
import com.techelevator.view.VendingGUI;

import java.io.File;

public class VendingMachineCLI {
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };
	private static final String PURCHASE_MENU_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_FEED_MONEY, PURCHASE_MENU_SELECT_PRODUCT,PURCHASE_MENU_FINISH_TRANSACTION };
	private static final File vendingInventory = new File("vendingMachine.csv");

	private Menu menu;






	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}


	public void run() {
		while (true) {

			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				InventoryManagement.displayInventory();

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				purchase();

			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.out.println("Thank you for using Vendo-Matic 800");
				System.exit(0);
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		InventoryManagement.restock(vendingInventory);
		//cli.run();
		new VendingGUI();
	}
	public static void alternateRun(){
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}


	public void purchase(){
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
			if (choice.equals(PURCHASE_MENU_FEED_MONEY)) {
				purchaseOptionFeedMoney();
			}
			if (choice.equals(PURCHASE_MENU_SELECT_PRODUCT)) {
				purchaseOptionSelectItem();
			}
			if (choice.equals(PURCHASE_MENU_FINISH_TRANSACTION)) {
				System.out.println("Current money: $" +MoneyManagement.getCurrentMoney().toString() );
				System.out.println("returned change: " + MoneyManagement.returnChange());
				break;
			}
		}
	}
	private void purchaseOptionFeedMoney(){
		System.out.println("Please enter whole dollar amount: ");
		try{
			MoneyManagement.feedMoney(Integer.parseInt(menu.getStringFromUser()));
			System.out.println("Current money: $" +MoneyManagement.getCurrentMoney().toString() );
		}catch(Exception e){
			System.out.println("please enter a valid whole number");
		}
	}
	private void purchaseOptionSelectItem(){
		InventoryManagement.displayInventory();
		System.out.println("Current money: $" +MoneyManagement.getCurrentMoney().toString() );
		System.out.println("Please enter an Item Location: ");
		try {
			String tempChoice = menu.getStringFromUser();
			if (InventoryManagement.currentInventory.get(tempChoice.toUpperCase()).getQuantity().equals("SOLD OUT")){
				Item.soldOut();
			} else if(InventoryManagement.currentInventory.get(tempChoice.toUpperCase()).getPrice().compareTo(MoneyManagement.getCurrentMoney()) > 0){
				System.out.println("Not enough money, please insert more or select another item");
			}
			else {
				InventoryManagement.selectItem(tempChoice);
				InventoryManagement.currentInventory.get(tempChoice.toUpperCase()).message();
				System.out.println("Current money: $" + MoneyManagement.getCurrentMoney().toString());
			}
		} catch (Exception e){
			System.out.println("Please enter a valid Location");
		}
	}

}

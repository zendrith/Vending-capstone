package com.techelevator.view;

import com.techelevator.VendingMachineCLI;
import com.techelevator.inventory.InventoryManagement;
import com.techelevator.inventory.Item;
import com.techelevator.logging.Log;
import com.techelevator.money.MoneyManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class VendingGUI {
    JFrame window = new JFrame();
    JPanel mainMenu = new JPanel();
    JPanel purchaseMenu = new JPanel();
    JPanel selectItem = new JPanel();
    JLabel cash1 = new JLabel();
    JLabel cash2 = new JLabel();

    public VendingGUI() {
        window.setPreferredSize(new Dimension(1000, 750));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setTitle("Vendo-Matic 800");
        window.add(mainMenu);

        mainMenu.setLayout(new GridLayout(3,1,10,10));
        mainMenu.setPreferredSize(new Dimension(1000,750));
        mainMenu.setFocusable(true);
        mainMenu.addKeyListener(new MyKeyAdapter());
        mainMenu.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        purchaseMenu.setLayout(new GridLayout(4,1,10,10));
        purchaseMenu.setPreferredSize(new Dimension(1000,750));
        purchaseMenu.setFocusable(false);
        purchaseMenu.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        cash1.setText("Current Money: $"+ MoneyManagement.getCurrentMoney());
        cash1.setHorizontalTextPosition(JLabel.CENTER);
        cash1.setHorizontalAlignment(JLabel.CENTER);
        cash1.setSize(new Dimension(350,100));
        cash1.setBorder(BorderFactory.createEtchedBorder());
        cash1.setBackground(Color.DARK_GRAY);
        cash1.setFont(new Font("Courier New", Font.BOLD, 20));

        cash2.setText("Current Money: $"+ MoneyManagement.getCurrentMoney());
        cash2.setHorizontalTextPosition(JLabel.CENTER);
        cash2.setHorizontalAlignment(JLabel.CENTER);
        cash2.setSize(new Dimension(350,100));
        cash2.setBorder(BorderFactory.createEtchedBorder());
        cash2.setBackground(Color.DARK_GRAY);
        cash2.setFont(new Font("Courier New", Font.BOLD, 15));

        List<JButton> generatedButtons = new ArrayList<>();
        InventoryManagement.currentInventory.entrySet().forEach(e -> generatedButtons.add(new JButton(e.getValue().getName()+": $" +e.getValue().getPrice()+ " "+ e.getKey())));
        generatedButtons.forEach(button -> button.addActionListener(e->cash2.setText("Current Money: $"+ MoneyManagement.getCurrentMoney())));
        generatedButtons.forEach(button -> button.addActionListener(e->{
            //try {
                Item tempItem = InventoryManagement.currentInventory.get(button.getText().substring(button.getText().length()-2));
                if(tempItem.getPrice().compareTo(MoneyManagement.getCurrentMoney())>0){
                    JOptionPane.showMessageDialog(window, "Not enough money, please add more or try another item", "Insufficient Funds", JOptionPane.ERROR_MESSAGE);
                }else if(tempItem.getQuantity().equals("SOLD OUT")){
                    JOptionPane.showMessageDialog(window,"Item is Sold Out, please select another item", "SOLD OUT", JOptionPane.ERROR_MESSAGE);
                }else {
                    InventoryManagement.selectItem(button.getText().substring(button.getText().length() - 2));
                    JOptionPane.showMessageDialog(window, tempItem.getName()+ "purchased for: "+ tempItem.getPrice(), tempItem.message(), JOptionPane.INFORMATION_MESSAGE);
                }
           // }
           // catch(Exception ex){
            //    JOptionPane.showMessageDialog(window,"Invalid Input", "Item Selection Error", JOptionPane.ERROR_MESSAGE);
            //}
        }));
        generatedButtons.forEach(button -> selectItem.add(button));

        selectItem.setLayout(new GridLayout(5,4,15,15));
        JButton backButton = new JButton("Go Back");
        selectItem.add(backButton);
        selectItem.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        backButton.addActionListener(e -> {
            window.setContentPane(purchaseMenu);
            window.invalidate();
            window.validate();
            cash1.setText("Current Money : $" + MoneyManagement.getCurrentMoney());
        });
        selectItem.add(cash2);

        JButton displayStuff = new JButton("Display Items");
        displayStuff.setFocusable(false);
        displayStuff.addActionListener(e -> JOptionPane.showMessageDialog(window, InventoryManagement.displayInventoryString(), "Vendo-Matic 800 Inventory", JOptionPane.INFORMATION_MESSAGE));
        JButton purchaseMenuButton = new JButton("Purchase Menu");
        purchaseMenuButton.setFocusable(false);
        purchaseMenuButton.addActionListener(e -> {
            window.setContentPane(purchaseMenu);
            window.invalidate();
            window.validate();
        });
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        JButton feedButton = new JButton("Feed Money");
        feedButton.setFocusable(false);
        feedButton.addActionListener(e -> {
            try {
                int passedMoney = Integer.parseInt(JOptionPane.showInputDialog(window,
                        "Please Enter Amount Of Money To Deposit", "Feed Money", JOptionPane.QUESTION_MESSAGE));
               MoneyManagement.feedMoney(passedMoney);
                cash1.setText("Current Money : $" + MoneyManagement.getCurrentMoney());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(window, "Please enter a whole number", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton selectButton = new JButton("Select Item");
        selectButton.setFocusable(false);
        selectButton.addActionListener(e -> {
            window.setContentPane(selectItem);
            window.invalidate();
            window.validate();
            cash2.setText("Current Money : $" + MoneyManagement.getCurrentMoney());
        });
        JButton finishedButton = new JButton("Finish Transaction");
        finishedButton.setFocusable(false);
        finishedButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(window, MoneyManagement.returnChange(),
                    "Returned Change", JOptionPane.INFORMATION_MESSAGE);
            cash1.setText("Current Money : $" + MoneyManagement.getCurrentMoney());
            window.setContentPane(mainMenu);
            window.invalidate();
            window.validate();
            mainMenu.requestFocusInWindow();
        });

        mainMenu.add(displayStuff);
        mainMenu.add(purchaseMenuButton);
        mainMenu.add(exitButton);

        purchaseMenu.add(cash1);
        purchaseMenu.add(feedButton);
        purchaseMenu.add(selectButton);
        purchaseMenu.add(finishedButton);

        window.pack();

    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent key) {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    window.dispose();
                    VendingMachineCLI.alternateRun();
                    break;
                case KeyEvent.VK_4:
                    Log.salesReport();
                    System.out.println("Sales Report Generated");
                    break;
            }
        }
    }


}

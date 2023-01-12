package com.techelevator.money;

import com.techelevator.logging.Log;

import java.math.BigDecimal;
import java.util.InputMismatchException;

public class MoneyManagement {
    private static BigDecimal currentMoney = BigDecimal.ZERO;

    public static BigDecimal getCurrentMoney() {
        return currentMoney;
    }

    public static void emptyCurrentMoney(){
        currentMoney = new BigDecimal("0.00");
    }

    public static void feedMoney(int fedAmount){
        if(fedAmount>0){
            currentMoney = currentMoney.add(new BigDecimal(fedAmount));
            Log.writeLog("Feed Money   ", new BigDecimal(fedAmount), currentMoney);
        }
        else throw new InputMismatchException();
    }

    public static void payMoney(BigDecimal charge){
        currentMoney = currentMoney.subtract(charge);
    }

    public static String returnChange(){
        final int quarterValue = 25;
        final int dimeValue = 10;
        final int nickelValue = 5;


        int change = currentMoney.multiply(new BigDecimal("100")).intValue();
        int quarters = change/quarterValue;
        change = change%quarterValue;
        int dimes = change/dimeValue;
        change = change%dimeValue;
        int nickels = change/nickelValue;

        Log.writeLog("Return Change", currentMoney, new BigDecimal("0.00"));
        emptyCurrentMoney();

        return quarters + " quarters, "+ dimes+ " dimes, "+nickels+ " nickels";
    }
}

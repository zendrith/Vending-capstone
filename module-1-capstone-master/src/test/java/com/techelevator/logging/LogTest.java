package com.techelevator.logging;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;

import static org.junit.Assert.*;

public class LogTest {

    @Test
    public void TestWriteLog() {
        Log.writeLog("testing", BigDecimal.TEN, new BigDecimal("5"));
        File testing = new File(Log.getLogName());
        String finalLine = "";
        try(Scanner readTest = new Scanner(testing)){
            while(readTest.hasNextLine()){
                finalLine = readTest.nextLine();
            }
        }catch(FileNotFoundException fnf){
            System.out.println("error during unit test");
            fail();
        }
        Assert.assertTrue(finalLine.contains("testing"));
        Assert.assertTrue(finalLine.contains("10"));
        Assert.assertTrue(finalLine.contains("5"));

    }
}
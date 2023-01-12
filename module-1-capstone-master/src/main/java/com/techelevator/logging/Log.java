package com.techelevator.logging;

import com.techelevator.inventory.InventoryManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

public class Log {
    private static final String TIMESTAMP = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(LocalDateTime.now());
    private static final String DATESTAMP = LocalDate.now().format(BASIC_ISO_DATE);
    private static final File LOG_DIRECTORY = new File("Logs");
    private static final File SALES_REPORT_DIRECTORY = new File("SalesReport");
    private static BigDecimal totalSales = BigDecimal.ZERO;

    private static String logName = LOG_DIRECTORY+"\\"+DATESTAMP+"-log.log";
    private static String salesReportName = SALES_REPORT_DIRECTORY+"\\"+ DATESTAMP+"-SalesReport.log";



    public static String getLogName(){
        return logName;
    }
    public static String getSalesReportName(){
        return salesReportName;
    }

    public static void writeLog(String action, BigDecimal transactionAmount, BigDecimal totalMoney) {
        if (LOG_DIRECTORY.mkdir()){
            //placeholder for creating directory
        }

        try(PrintWriter logWriter = new PrintWriter(new FileOutputStream(logName, true))){


            logWriter.println(TIMESTAMP + " | " + action + " | $" + transactionAmount + " | $" + totalMoney);

        }catch(FileNotFoundException fnf){
            System.out.println("There was a problem with creating directory");
        }
        catch (Exception e) {
            System.out.println("There was a problem with writing to the Log");

        }
    }
    public static void countSales(BigDecimal transaction){
        totalSales = totalSales.add(transaction);
    }

    public static void salesReport(){
        if(SALES_REPORT_DIRECTORY.mkdir()){
            //placeholder for creating directory
        }
        try(PrintWriter reportWriter = new PrintWriter(new FileOutputStream(salesReportName, true))){
            InventoryManagement.currentInventory.entrySet().forEach(item -> reportWriter.printf(item.getKey() + " | %-20s | $%-4s | %5s\n", item.getValue().getName(), item.getValue().getPrice(),item.getValue().getAmountSold()));
            reportWriter.print("*** Total Sales *** "+ totalSales+" | "+ TIMESTAMP+ "\n");
        }catch(Exception e){
            System.out.println("There was a problem with writing the sales report");
        }
    }
}

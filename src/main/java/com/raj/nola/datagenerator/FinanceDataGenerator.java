package com.raj.nola.datagenerator;

import com.github.javafaker.Faker;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;


public class FinanceDataGenerator {
    private static final String fileSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    private static final String SAMPLE_CSV_FILE = "sample"+fileSuffix+".csv";
    private static final String FILE_OUTPUT_DIRECTORY="/tmp/data/unprocessed/";

    private static final String[] SUB_TYPE = {"Platinum", "Gold", "Silver", "Diamond"};
    private static final String[] STATUS_TYPE = {"Active", "Blocked", "Pending"};



    String home = System.getProperty("user.home");
    String fileOutputPath=home+FILE_OUTPUT_DIRECTORY+SAMPLE_CSV_FILE;

    public void generateFinanceData(){

        // Create an object of Faker class with default locale i.e ENG
        Faker faker = new Faker(new Locale("en-US"));

        Random rand = new Random();



        try {

               boolean fileExistStatus= Files.deleteIfExists(Paths.get(fileOutputPath));

               if(fileExistStatus)
                   System.out.println("Old file existed and it is deleted to generate a new file to write data");
               else
                   System.out.println("New file created to write data");


                BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileOutputPath));

            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("FirstName", "LastName","Category", "Country", "Product","Color","Material","Price","Date","Status"));


            System.out.println("Started adding content to file...");


            for(int i =0; i <10000;i++){
                csvPrinter.printRecord(faker.name().firstName(),faker.name().lastName(),SUB_TYPE[rand.nextInt( SUB_TYPE.length)],
                        faker.address().country().replace(",","-"),
                        faker.commerce().productName(),faker.commerce().color(),faker.commerce().material(),
                        faker.commerce().price(),faker.date().past(300, TimeUnit.DAYS),STATUS_TYPE[rand.nextInt( STATUS_TYPE.length)]);
            }
            csvPrinter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {

        FinanceDataGenerator fdg = new FinanceDataGenerator();
        fdg.generateFinanceData();

        System.out.println("File Is Generated");
    }

    }


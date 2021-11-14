/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

import static j2html.TagCreator.*;

public class FilesInputOutput {
    //this class is used to deal with saving, loading process from different types of files
    //user should be passing File, for the purpose of testing
    private Formatter writer;

    //after already checked that the file is extension of .txt, and we are trying to save it
    public void saveListAsTSV(File file, List<ItemObject> list){
        //set 'writer' to the file
        try {
            writer = new Formatter(file);
            //format of saving in .txt will be
            //**************************************************************
            //Serial Number <tab>   Name    <tab>   Value
            //serNumItem1   <tab> nameItem1 <tab> priceItem1
            //serNumItem2   <tab> nameItem2 <tab> priceItem2 ...
            //**************************************************************
            writer.format("%s\t%s\t%s%n","Serial Number","Name","Value");
            for(int i = 0; i<list.size();i++) {
                writer.format("%s\t%s\t%s%s%n",list.get(i).getSerialNumber(),
                        list.get(i).getName(),"$",
                        String.format("%.2f",Double.parseDouble(list.get(i).getPrice()))
                );
            }
            //close writer
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("FILE CANNOT BE SAVED! something is wrong");
        }
    }

    //after already checked that the file is extension of .json, and we are trying to save it
    public void saveListAsJSON(File file, ListWrapper listWrapper){
        //set 'writer' to the file
        Gson gson = new Gson();
        //format of saving of JSON file will be (but all in one line)
        //**************************************************************
        // {
        //    "list" : [
        //        {"serialNumber": "serNumItem1", "name": "nameItem1", "price": "priceItem1"},
        //        {"serialNumber": "serNumItem2", "name": "nameItem2", "price": "priceItem2"},
        //          ...
        //        {"serialNumber": "serNumItemX", "name": "nameItemX", "price": "priceItemX"}
        //    ]
        // }
        //**************************************************************
        try {
            writer = new Formatter(file);
            gson.toJson(listWrapper, writer.out());
            writer.close();
            System.out.println("file saved");
        } catch (IOException e){
            System.out.println("json file saving is not right");
        }
        //close writer
    }

    //after already checked that the file is extension of .html, and we are trying to save it
    public void saveListAsHTML(File file, List<ItemObject> list){
        //using J2HTML,to create a html format
        //make a String that contains everything in it
        String html = html(head(
                title("InventoryApplication")),     //declaring the title
                body(
                        table(
                        th("Serial Number"),            //set table headers
                        th("Name"),
                        th("Value"),
                        tbody(
                                each(list, i -> tr(         //loop through the body to display each values
                                        td(i.getSerialNumber()),
                                        td(i.getName()),
                                        td(i.getPrice())
                                ))
                        )
                ))
        ).renderFormatted();                //renderFormatted gives an indented html format (when open the .html file)
        try {
            //create a writer linked to that file
            writer = new Formatter(file);
            //output to file, from the string we made above with j2html
            writer.format("%s%n%s","<!DOCTYPE html>",html);
            //close writer
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("save HTML failed");
        }
    }

    //after already checked that the file is extension of .txt, and we are trying to load it
    public List<ItemObject> loadFromTSV(File file){
        //loading will reflect from saving process
        List<ItemObject> result = new ArrayList<>();
        //set Scanner to the file location given
        try (Scanner scanner = new Scanner(file)) {
            //read each line at a time
            //skip the first line, since we already know what those are
            scanner.nextLine();
            //make a loop as long as it has nextLine
            String temp;
            while (scanner.hasNext()) {
                temp = scanner.nextLine();

                //  split those lines using <tab> into string[]
                String[] tempArr = temp.split("\t");
                //  set string[0] to the serialNumber string
                //  set string[1] to the name string
                //  set string[2] to the price string after parsed out the "$"
                //      trim out the "$" by replacing "$" with empty string
                String trimmed = tempArr[2].replace("$", "");
                //  create a new ItemObject using those elements
                //  add the ItemObject to the list
                result.add(new ItemObject(tempArr[0], tempArr[1], Double.parseDouble(trimmed)));
            }
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("something is wrong with saving TSV");
        }
        return Collections.emptyList();
    }

    //after already checked that the file is extension of .json, and we are trying to load it
    public List<ItemObject> loadFromJSON(File file){
        //when saved the file, I used Gson.toJson, it all saved in one line
        //the way to read that back, I will use fromJson, which will automatically parse the file to the object
        ListWrapper listWrapper = new ListWrapper();
        Gson gson = new Gson();
        try (FileReader scanner = new FileReader(file);){
            listWrapper = gson.fromJson(scanner,listWrapper.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listWrapper.getListOfItem();
    }

    //after already checked that the file is extension of .html, and we are trying to load it
    public List<ItemObject> loadFromHTML(File file){
        //create a list to store result
        List<ItemObject> list = new ArrayList<>();
        //temp variables to read from parsing
        String number;
        String name;
        double price;
        try {
            //parse to Document class with Jsoup
            Document document = Jsoup.parse(file,null);
            //set the focus only to table
            Elements tableElement = document.select("table");
            //set elements to tableRow
            Elements tableRowElements = tableElement.select(":not(th) tr");
            //loop through the table
            for (int i = 1; i < tableRowElements.size(); i++) {
                Element row = tableRowElements.get(i);
                Elements rowItems = row.select("td");
                //index 0 is item's serial number
                number = rowItems.get(0).text();
                //index 1 is item's name
                name = rowItems.get(1).text();
                //index 2 is item's price
                price = Double.parseDouble(rowItems.get(2).text());
                //create a new ItemObject with the information, add to the list
                list.add(new ItemObject(number,name,price));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("something is wrong with HTML savings... cannot save it");
            return Collections.emptyList();
        }
        //return the list
        return list;
    }
}

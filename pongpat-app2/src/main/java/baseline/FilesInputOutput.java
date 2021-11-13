/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

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
        //I need to figure this out....later
    }

    //after already checked that the file is extension of .txt, and we are trying to load it
    public List<ItemObject> loadFromTSV(File file){
        //loading will reflect from saving process
        List<ItemObject> result = new ArrayList<>();
        //set Scanner to the file location given
        try {
            Scanner scanner = new Scanner(file);
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
                StringBuilder tempSB = new StringBuilder();
                tempSB.append(tempArr[2]);
                //trim out the "$", which is always at index 0
                tempSB.deleteCharAt(0);
                //  create a new ItemObject using those elements
                //  add the ItemObject to the list
                result.add(new ItemObject(tempArr[0],tempArr[1],Double.parseDouble(tempSB.toString())));
            }
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        //I need to figure this out....later
        return null;
    }
}

/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import java.io.File;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class FilesInputOutput {
    //this class is used to deal with saving, loading process from different types of files
    //user should be passing File, for the purpose of testing

    private Formatter writer;
    private Scanner scanner;

    //after already checked that the file is extension of .txt, and we are trying to save it
    public void saveListAsTSV(File file, List<ItemObject> list){
        //set 'writer' to the file
        //format of saving in .txt will be
        //**************************************************************
        //Serial Number <tab>   Name    <tab>   Value
        //serNumItem1   <tab> nameItem1 <tab> priceItem1
        //serNumItem2   <tab> nameItem2 <tab> priceItem2 ...
        //**************************************************************
        //close writer
    }

    //after already checked that the file is extension of .json, and we are trying to save it
    public void saveListAsJSON(File file, List<ItemObject> list){
        //set 'writer' to the file
        //format of saving of JSON file will be
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
        //close writer
    }

    //after already checked that the file is extension of .html, and we are trying to save it
    public void saveListAsHTML(File file, List<ItemObject> list){
        //I need to figure this out....later
    }

    //after already checked that the file is extension of .txt, and we are trying to load it
    public List<ItemObject> loadFromTSV(File file, List<ItemObject> list){
        //loading will reflect from saving process
        //set Scanner to the file location given
        //read each line at a time
        //skip the first line, since we already know what those are
        //make a loop as long as it has nextLine
        //  split those lines using <tab> into string[]
        //  set string[0] to the serialNumber string
        //  set string[1] to the name string
        //  set string[2] to the price string
        //  create a new ItemObject using those elements
        //  add the ItemObject to the list

        return null;
    }

    //after already checked that the file is extension of .json, and we are trying to load it
    public List<ItemObject> loadFromJSON(File file, List<ItemObject> list){
        //I need to figure this out....later
        return null;
    }

    //after already checked that the file is extension of .html, and we are trying to load it
    public List<ItemObject> loadFromHTML(File file, List<ItemObject> list){
        //I need to figure this out....later
        return null;
    }
}

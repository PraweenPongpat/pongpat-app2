/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import java.util.ArrayList;
import java.util.List;

public class ListWrapper {
    //wrapper object storing a list of ItemObject and other variables/method as needed

    //list of ItemObject
    private List<ItemObject> listOfItem = new ArrayList<>();
    //isAdding to check if the process is whether add/edit to check on AddEditWindowController
    //isAdding = true when adding, isAdding = false when editing
    private boolean isAdding;
    //index, used to store index of the selected item in the tableView
    private int index = -1;

    //setters
    public void setIndex(int index) {
        this.index = index;
    }
    public void setAdding(boolean adding) {
        isAdding = adding;
    }

    //getters
    public int getIndex() {
        return index;
    }
    public List<ItemObject> getListOfItem() {
        return listOfItem;
    }
    public boolean getIsAdding() {
        return isAdding;
    }

    //other functions
    //this method will be called to remove a single item from the list with the given index
    public void removeOneItem (int index) {
        //access the list with the given index
        //remove that index from the list
        listOfItem.remove(index);
    }

    //this method is used to add data into the block
    public void addItemToList(String numberInput, String nameInput, String priceInput) {
        //before reaching this point, all validation already existed, we can just add it
        //create a new ItemObject parameterized with the strings given
        //add that ItemObject to the list
        listOfItem.add(new ItemObject(numberInput,nameInput,Double.parseDouble(priceInput)));
    }

    //this method is used to add data into the block
    public void editItemToList(String numberInput, String nameInput, String priceInput,int index) {
        //before reaching this point, all validation already existed, we can just edit it
        //access the list with the given index

        //set the serialNumber of that index to numberInput
        listOfItem.get(index).setSerialNumber(numberInput);
        //set the name of that index to nameInput
        listOfItem.get(index).setName(nameInput);
        //set the price of that index to princeInput
        listOfItem.get(index).setPrice(Double.parseDouble(priceInput));
    }

    //this method will be called to search the name from all item in the list, it will return index found or -1
    public List<Integer> searchName(String nameInput) {
        //create a new List
        List<Integer> sameNameList = new ArrayList<>();
        //loop through the size of the list, each iteration...
        for(int i = 0; i< listOfItem.size(); i++) {
            // check if the 'name' store in the current index is the same as 'nameInput'
            if(listOfItem.get(i).getName().equals(nameInput)) {
                // if both string is the same, add that index to the result list
                sameNameList.add(i);
            }
        }
        //if the loop ended, and not found the any matches, return -1
        return sameNameList;
    }

    //this method will be called to search the serial number from all item in the list, it will return index found or -1
    public int searchNumber( String numberInput) {
        //loop through the size of the list, each iteration...
        for(int i = 0; i< listOfItem.size(); i++) {
            // check if the 'serialNumber' store in the current index is the same as 'stringName'
            if (listOfItem.get(i).getSerialNumber().equals(numberInput)) {
                // if both string is the same, return that index
                return i;
            }
        }
        //if the loop ended, and not found the any matches, return -1
        return -1;
    }
}

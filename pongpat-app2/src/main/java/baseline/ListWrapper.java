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
    public boolean isAdding() {
        return isAdding;
    }

    //other functions
    //this method will be called to remove a single item from the list with the given index
    public void removeOneItem (List<ItemObject> list, int index) {
        //access the list with the given index
        //remove that index from the list
    }

    //this method will be called to search the name from all item in the list, it will return index found or -1
    public int searchName(List<ItemObject> list, String nameInput) {
        //loop through the size of the list, each iteration...
        // check if the 'name' store in the current index is the same as 'nameInput'
        // if both string is the same, return that index

        //if the loop ended, and not found the any matches, return -1
        return -1;
    }

    //this method will be called to search the serial number from all item in the list, it will return index found or -1
    public int searchNumber(List<ItemObject> list, String numberInput) {
        //loop through the size of the list, each iteration...
        // check if the 'serialNumber' store in the current index is the same as 'stringName'
        // if both string is the same, return that index

        //if the loop ended, and not found the any matches, return -1
        return -1;
    }
}

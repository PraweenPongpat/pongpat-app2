/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import java.util.Comparator;
import java.util.List;

public class ListSorter {
    //this class is used for sorting items in the list

    //to sort the list in general
    //list has .sort method that already sorts generic objects in the list already
    //we will be using this

    //this method is used for sort the list by names
    public void sortListByName(List<ItemObject> originalList) {
        //no parameter is passed in, the method is using the instance variable that it should be initialized before

        //use .sort, not to the list directly, but to a comparator that will grab only a name from the list
        //this will sort the list from using the 'name' element in each ItemObject
        //either store a result in a new list and return it, or just return that sorted list
        originalList.sort(Comparator.comparing(ItemObject::getName));
    }

    //this method is used for sort the list by serialNumber
    public void sortListByNumber(List<ItemObject> originalList) {
        //no parameter is passed in, the method is using the instance variable that it should be initialized before

        //use .sort, not to the list directly, but to a comparator that will grab only a serialNumber from the list
        //this will sort the list from using the 'serialNumber' element in each ItemObject
        //either store a result in a new list and return it, or just return that sorted list
        originalList.sort(Comparator.comparing(ItemObject::getSerialNumber));
    }

    //this method is used for sort the list by price
    public void sortListByPrice(List<ItemObject> originalList) {
        //no parameter is passed in, the method is using the instance variable that it should be initialized before

        //use .sort, not to the list directly, but to a comparator that will grab only a price from the list
        //this will sort the list from using the 'price' element in each ItemObject
        //either store a result in a new list and return it, or just return that sorted list
        originalList.sort(Comparator.comparing(ItemObject::getPriceDouble));
    }
}

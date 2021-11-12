/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

public class ItemObject {
    //this class is a placeholder for storing an item as object
    private String serialNumber;
    private String name;
    private String price;

    //parameterized constructor
    public ItemObject(String serialNumber, String name, String price) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.price = price;
    }

    //setters for setting VALIDATED item, item must be validated before set to it
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    //getters for getting value from the object
    public String getSerialNumber() {
        return serialNumber;
    }
    public String getName() {
        return name;
    }
    public String getPrice() {
        return price;
    }
}

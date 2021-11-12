/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class AddEditWindowController {

    @FXML
    private Label errorDisplayLabel;
    @FXML
    private TextField itemNameTextField;
    @FXML
    private TextField itemNumberTextField;
    @FXML
    private TextField itemPriceTextField;
    @FXML
    private Label topicLabel;

    //variables as needed
    private ListWrapper listWrapper = new ListWrapper();
    public int index = -1;

    @FXML
    void cancelButtonPushed(ActionEvent event) {
        //when cancel button is pushed,
        //simply call changeScene to change the scene back
        //since there is no new information to be passed
    }

    @FXML
    void doneButtonPushed(ActionEvent event) {
        //when done button is pushed
        //we need to verify information that user filled first

        //read a string from itemNumberTextField
        //call method serialNumberValidator, passing in the string from textField
        //if return value is true, proceed
        //if return value is false
        //  prompt user that "wrong serial number format: A-XXX-XXX-XXX"
        //  end the method, do nothing else

        //read a string from itemNameTextField
        //call method nameLengthValidator, passing in the string from textField
        //if return value is true, proceed
        //if return value is false
        //  prompt user that "the name of item must be length of 2-256 only"
        //  end the method, do nothing else

        //read a string from itemPriceTextField
        //call method priceValidator, passing in the string from textField
        //if return value is true, proceed
        //if return value is false
        //  prompt user that "price must be a positive number, with two digits only"
        //  end the method, do nothing else

        //***reaching this point, means all information is valid, next, check if item already exists
        //we are going check only by serialNumber, since name from products can be the same, as long as number's unique
        //call method isNumberAlreadyExisted, passing in string from itemNumberTextField
        //if the return is true, means that the item with that exact serial number already existed
        //  prompt user that "the inventory already has the item with this serial number"
        //  end the method, do nothing else
        //if the return value is false, means that list don't have this serial number, we can proceed

        //***reaching this point, all information is valid
        //check 'isAdding'
        //if 'isAdding' is true, call method addItemToList passing in all 3 strings read from textFields
        //if 'isAdding' is false, call method editItemInList passing in all 3 strings read from textFields, index

        //***reaching this, an item has been added, or edited already
        //call method changeScene
    }

    public void initializeListWrapper(ListWrapper listWrapper) {
        //this method is a receiver side from data passing process between scenes

        //set listWrapper instance variable to the one passed from another class (to initialize it)

        //set the observableList to the list within listWrapper

        //check 'isAdding'
        //when 'isAdding' is false, means we are editing, we must fill in textFields with the existed texts
        //set itemNameTextField to the 'name' String contained in the list within the given index
        //set itemNumberTextField to the 'serialNumber' String contained in the list within the given index
        //set itemPriceTextField to the 'Price' double contained in the list within the given index
        //set 'index' instance variable to 'index' contained in the listWrapper
    }

    private void changeScene() {
        //this method is a sender side from data passing process between scenes

        //this method will always be call to pre-load information before the actual scene changing and change scene
        //since the app only have 2 scenes, this method will only be pointing to MainWindow.fxml

        //create a FXMLLoader pointing at MainWindow.fxml
        //load the loader
        //create the controller from MainWindowController class

        //call initializeListWrapper in mainWindow's controller
        //  the method will receive the information where it must be done after we loaded the controller
        //  if not, the information passed to that controller will not be successful

        //create a stage with this stage information from one of the controller we used in this scene
        //set the scene and show it (now the scene will be moved)
    }

    public void initialize() {
        //this method is called automatically each time the scene AddEditWindow.fxml

        //set errorDisplayMessage to emptyString

        //check 'index'
        //if 'index' is -1, set topicLabel to "Add an item to the inventory'
        //  since we are adding, there is no initialization in any textFields

        //if 'index' is 0 or more, set topicLabel to "Edit an item in the inventory'
        //  since we are editing, we must fill in textFields with the existed texts
        //  the settings will be done in initializeListWrapper
    }

    //other methods

    //this method is used to validate the serial number input format
    public boolean serialNumberValidator (List<ItemObject> list, String numberInput) {
        //split the string delimit with '-', store value in string[]
        //check the size of the string[]
        //if the length of string[] is 4, if it is not, return false

        //check string[0] must have length of 1, and it must be a letter
        //if not, return false

        //make a loop counting 3 times
        //check string[counter] must have length of 3
        //  loop through string[counter], characters inside can only be digit or latter, nothing else
        //if not, return false

        //reaching this point, means every validation requirement has passed
        return true;
    }

    //this method is used to check the length in the item's name
    public boolean nameLengthValidator (List<ItemObject> list, String nameInput) {
        //check the length of nameInput
        //if the length is a number within 2-256, return true
        //otherwise
        return false;
    }

    //this method is used to check the if the price is a positive number with two digit
    //since something with a price as 5.12795 (for example) doesn't exist, it's only 2 decimal points max
    public boolean priceValidator (List<ItemObject> list, String priceInput) {
        //try to parse the number as a float
        //if there's any error, it means that user has input something wrong
        //return false

        //check if the parsed number is 0 or more
        //if it's negative, means that user gave invalid price
        //return false

        //check if the parsed number's decimal points
        //if it has strictly longer than 2 decimal points
        //return false
        //if it has less than or equal to that, it's okay

        //reaching this point, means the validation has passed
        return true;
    }

    //this method is used to check if the serial number already existed in the list
    public boolean isNameAlreadyExisted (List<ItemObject> list, String nameInput, int index, boolean isAdding) {
        //create a loop through the size of list in the listWrapper
        //if it already existed this could mean two things:
        //  user is editing, editing valid info into another valid info of THE SAME INDEX
        //      if the index found in the loop is the same as given index, it's okay, break out of loop
        //      if the index found is something else, not the given index, return true
        //  user is adding, into something that already existed
        //      if so, return true

        //otherwise,
        return false; //meaning that number hasn't existed yet
    }

    //this method is used to check if the serial number already existed in the list
    public boolean isNumberAlreadyExisted (List<ItemObject> list, String numberInput, int index, boolean isAdding) {
        //create a loop through the size of list in the listWrapper
        //if it already existed this could mean two things:
        //  user is editing, editing valid info into another valid info of THE SAME INDEX
        //      if the index found in the loop is the same as given index, it's okay, break out of loop
        //      if the index found is something else, not the given index, return true
        //  user is adding, into something that already existed
        //      if so, return true

        //otherwise,
        return false; //meaning that number hasn't existed yet
    }

    //this method is used to add data into the block
    public void addItemToList(List<ItemObject> list, String numberInput, String nameInput, String priceInput) {
        //before reaching this point, all validation already existed, we can just add it
        //create a new ItemObject parameterized with the strings given
        //add that ItemObject to the list
    }

    //this method is used to add data into the block
    public void editItemToList(List<ItemObject> list, String numberInput, String nameInput, String priceInput,int index) {
        //before reaching this point, all validation already existed, we can just edit it
        //access the list with the given index
        //set the serialNumber of that index to numberInput
        //set the name of that index to nameInput
        //set the price of that index to princeInput
    }
}

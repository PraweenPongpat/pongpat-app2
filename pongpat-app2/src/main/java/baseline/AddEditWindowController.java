/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
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
    private int index = -1;

    @FXML
    void cancelButtonPushed() {
        //when cancel button is pushed,
        //simply call changeScene to change the scene back
        //since there is no new information to be passed
        changeScene();
    }

    @FXML
    void doneButtonPushed() {
        //when done button is pushed
        //we need to verify information that user filled first

        //read a string from itemNumberTextField
        String numberInput = itemNumberTextField.getText();
        //call method serialNumberValidator, passing in the string from textField
        //if return value is true, proceed
        //if return value is false
        if(!(serialNumberValidator(numberInput))) {
            //  prompt user that "wrong serial number format: A-XXX-XXX-XXX"
            errorDisplayLabel.setText("wrong serial number format: A-XXX-XXX-XXX");
            //  end the method, do nothing else
            return;
        }
        //read a string from itemNameTextField
        String nameInput = itemNameTextField.getText();
        //call method nameLengthValidator, passing in the string from textField
        //if return value is true, proceed
        //if return value is false
        if(!(nameLengthValidator(nameInput))) {
            //  prompt user that "the name of item must be length of 2-256 only"
            errorDisplayLabel.setText("the name of item must be length of 2-256 only");
            //  end the method, do nothing else
            return;
        }
        //read a string from itemPriceTextField
        String priceInput = itemPriceTextField.getText();
        //call method priceValidator, passing in the string from textField
        //if return value is true, proceed
        //if return value is false
        if(!(priceValidator(priceInput))) {
            //  prompt user that "price must be a positive number, with two digits only"
            errorDisplayLabel.setText("price must be a positive number, with two digits only");
            //  end the method, do nothing else
            return;
        }

        //***reaching this point, means all information is valid
        //next, check if item already exists
        //we are going check by serialNumber that it is since name from products can be the same, as long as number's unique
        //call method isNumberAlreadyExisted, passing in string from itemNumberTextField
        //if the return is true, means that the item with that exact serial number already existed
        if(isNumberAlreadyExisted(listWrapper.getListOfItem(),numberInput,index,listWrapper.getIsAdding())) {
            //  prompt user that "the inventory already has the item with this serial number"
            errorDisplayLabel.setText("the inventory already has the item with this serial number");
            //  end the method, do nothing else
            return;
        }
        //if the return value is false, means that list don't have this serial number, we can proceed

        //***reaching this point, all information is valid, no other error could happen
        errorDisplayLabel.setText("");

        //check 'isAdding'
        //if 'isAdding' is true, call method addItemToList passing in all 3 strings read from textFields
        if(listWrapper.getIsAdding()){
            listWrapper.addItemToList(numberInput,nameInput,priceInput);
        }
        //if 'isAdding' is false, call method editItemInList passing in all 3 strings read from textFields, index
        else {
            listWrapper.editItemToList(numberInput,nameInput,priceInput,index);
        }
        //***reaching this, an item has been added, or edited already
        //call method changeScene
        changeScene();
    }

    public void initializeListWrapper(ListWrapper listWrapper) {
        //this method is a receiver side from data passing process between scenes
        errorDisplayLabel.setText("");

        //set listWrapper instance variable to the one passed from another class (to initialize it)
        this.listWrapper = listWrapper;
        //set 'index' instance variable to 'index' contained in the listWrapper
        index = listWrapper.getIndex();

        //check 'isAdding'
        //when 'isAdding' is true, we are adding, set the topicLabel like so
        if(listWrapper.getIsAdding()){
            topicLabel.setText("Add an item to the inventory");
        }
        //when 'isAdding' is false, means we are editing, set the topicLabel
        else {
            topicLabel.setText("Edit an item in the inventory");

            //set itemNameTextField to the 'name' String contained in the list within the given index
            itemNameTextField.setText(listWrapper.getListOfItem().get(index).getName());
            //we must fill in textFields with the existed texts
            //set itemNumberTextField to the 'serialNumber' String contained in the list within the given index
            itemNumberTextField.setText(listWrapper.getListOfItem().get(index).getSerialNumber());
            //set itemPriceTextField to the 'Price' double contained in the list within the given index
            itemPriceTextField.setText(listWrapper.getListOfItem().get(index).getPrice());
        }

    }

    private void changeScene() {
        //this method is a sender side from data passing process between scenes

        //this method will always be call to pre-load information before the actual scene changing and change scene
        //since the app only have 2 scenes, this method will only be pointing to MainWindow.fxml

        //create a FXMLLoader pointing at MainWindow.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        //load the loader
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //create the controller from MainWindowController class
        MainWindowController controller = loader.getController();
        //call initializeListWrapper in mainWindow's controller
        //  the method will receive the information where it must be done after we loaded the controller
        //  if not, the information passed to that controller will not be successful
        controller.initializeListWrapper(listWrapper);

        //create a stage with this stage information from one of the controller we used in this scene
        assert root != null;
        Scene mainWindowScene = new Scene(root);

        //set the theme from J-metro
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(mainWindowScene);

        //set the scene and show it (now the scene will be moved)
        Stage window = (Stage) errorDisplayLabel.getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();
    }

    //other methods
    //this method is used to validate the serial number input format
    public boolean serialNumberValidator (String numberInput) {
        //make a char array based on numberInput
        char[] charArr = numberInput.toCharArray();
        //note that we mush check all char index

        //we know that the size must be 13 (included '-' and the first element must be alphabet)
        if(charArr.length!=13 || !(Character.isAlphabetic(charArr[0]))){
            return false;
        }

        //we also knew that element #1,5,9 must be '-'
        for(int i = 1; i<charArr.length; i++) {
            if((i == 1) || (i == 5) || (i == 9)){
                if(charArr[i]!='-') {
                    return false;
                }
            } else {
                if(!(Character.isAlphabetic(charArr[i])) && !(Character.isDigit(charArr[i])))
                    return false;
            }
        }
        //reaching this point, means every validation requirement has passed
        return true;
    }

    //this method is used to check the length in the item's name
    public boolean nameLengthValidator (String nameInput) {
        //check the length of nameInput
        //if the length is a number within 2-256, return true
        return nameInput.length() >= 2 && nameInput.length() <= 256;
    }

    //this method is used to check the if the price is a positive number with two digit
    //since something with a price as 5.12795 (for example) doesn't exist, it's only 2 decimal points max
    public boolean priceValidator (String priceInput) {
        //try to parse the number as a float
        double tempPrice;
        try {
            tempPrice = Double.parseDouble(priceInput);
        } catch (NumberFormatException e) {
            //if there's any error, it means that user has input something wrong
            //return false
            return false;
        }
        //check if the parsed number is 0 or more
        //if it's negative, means that user gave invalid price
        if(tempPrice<0.0) {
            //return false
            return false;
        }

        //check if the parsed number's decimal points
        String[] decimalPointsCheck = Double.toString(tempPrice).split("\\.");
        //if it has strictly longer than 2 decimal points
        //return false
        return decimalPointsCheck[1].length() <= 2;
        //if it has less than or equal to that, it's okay

        //reaching this point, means the validation has passed
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
        if(checkExisted(list,numberInput)){
            return isAdding || !list.get(index).getSerialNumber().equals(numberInput);
        }
        //otherwise,
        return false; //meaning that number hasn't existed yet
    }
    //a helper method to check isNumberAlreadyExisted
    private boolean checkExisted(List<ItemObject> list, String numberInput) {
        for (ItemObject itemObject : list) {
            if (itemObject.getSerialNumber().equals(numberInput))
                return true;
        }
        return false;
    }
}

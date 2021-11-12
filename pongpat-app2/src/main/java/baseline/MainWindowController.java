/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainWindowController {

    //FXML variables used for GUI
    //note that buttons will not have its name, since we only use its functionality when pushed
    @FXML
    private Label errorDisplayLabel;
    @FXML
    private TableColumn<?, ?> itemNameColumn;
    @FXML
    private TableColumn<?, ?> itemNumberColumn;
    @FXML
    private TableColumn<?, ?> itemPriceColumn;
    @FXML
    private TextField searchNameTextField;
    @FXML
    private TextField searchNumberTextField;
    @FXML
    private TableView<?> tableView;

    //listWrapper class that contain actual object for the app
    private ListWrapper listWrapper = new ListWrapper();
    //observableList uses to display items to tableView
    private ObservableList<ItemObject> observableList;
    //AddEditWindowController class's methods will be needed uses to pass information between scenes
    AddEditWindowController addEditWindowController = new AddEditWindowController();
    //index to keep up with the index chosen in the tableView
    private int index = -1;  //always default -1, meaning that nothing is selected in the tableView yet

    //other parameters... like
    // Scanner, File, FileChooser, ...
    // maybe added here, or as local variable
    // it depends on where it's actually being used

    @FXML
    void RemoveOneItemButtonPushed(ActionEvent event) {
        //when removeOneItem button is pushed
        //there MUST be a selected item in the tableView (from listener)

        //check the index
        //if index is less than zero (default as negative) meaning there's nothing selected in tableView
        //  display errorDisplayLabel to prompt user "you must select an item from the list before removing"
        //  end method, do not do anything else

        //if index is zero or greater, meaning there's something selected in the tableView
        //  call method removeOneItem in ListWrapper class to remove that index from the list
        //      the function should take in a (List, index)
        //  remove the index from observableList also, to update the tableView
    }

    @FXML
    void addItemButtonPushed(ActionEvent event) {
        //when addItem button is pushed, no need to check in the tableView

        //set value 'isAdding' in listWrapper to 'true', since we are adding
        //isAdding will determine the setup in the scene whether it's being added or edited

        //change scene to AddEditWindow.fxml process by calling changeScene method
    }

    @FXML
    void editItemButtonPushed(ActionEvent event) {
        //when editItem button is pushed
        //there MUST be a selected item in the tableView (from listener)

        //check the index
        //if index is less than zero (default as negative) meaning there's nothing selected in tableView
        //  display errorDisplayLabel to prompt user "you must select an item to edit its information"
        //  end method, do not do anything else

        //if index is zero or greater, meaning there's something selected in the tableView
        //  set value 'isAdding' in listWrapper to 'false', since we are editing
        //  change scene to AddEditWindow.fxml process by calling changeScene method
    }

    @FXML
    void removeAllItemButtonPushed(ActionEvent event) {
        //when removeAllItem button is pushed, no need to check in the tableView

        //call method removeAllItem in ListWrapper class, passed in the list from listWrapper
        //clear all data in the observableList to update tableView
    }

    @FXML
    void saveButtonPushed(ActionEvent event) {
        //when save button is pushed, we will be calling a fileChooser to get a File from it
        //if File is null, means user doesn't try to save file (click X (close) or cancel)
        //  set errorDisplayLabel to let user know that "save failed, please try again"
        //  end method

        //if file is not null,
        //check if the string (path name) in that file has what extension and calls FilesInputOutput method accordingly
        //for .txt, call saveListAsTSV in FilesInputOutput class
        //for .html, call saveListAsHTML in FilesInputOutput class
        //for .json, call saveListAsJSON in FilesInputOutput class
    }

    @FXML
    void loadButtonPushed(ActionEvent event) {
        //when load button is pushed, we will be calling a fileChooser to get a File from it
        //if File is null, means user doesn't try to save file (click X (close) or cancel)
        //  set errorDisplayLabel to let user know that "load failed, please try again"
        //  end method

        //if file is not null,
        //clear actual list
        //check if the string (path name) in that file has what extension and calls FilesInputOutput method accordingly
        //for .txt, call loadFromTSV in FilesInputOutput class
        //for .html, call loadFromHTML in FilesInputOutput class
        //for .json, call loadFromJSON in FilesInputOutput class
    }

    @FXML
    void searchNameButtonPushed(ActionEvent event) {
        //when searchName button is pushed, no need to check in the tableView

        //read the string from searchNameTextField
        //call method searchName in ListWrapper class, passing in actual list as (list, string)
        //where the string is what's read from the textField

        //check the return value
        //if method return -1, means search result is not found
        //  set errorDisplayLabel to prompt user "Sorry! we don't have any item with that name in stock!"
        //if method return non-positive, which represents the index found in the list
        //create a tempList, add the item in actual list from that index to it
        //clear observableList, this will clear the tableView
        //assigned the observableList with values within tempList (should have only one item to it) to set the tableView
    }

    @FXML
    void searchNumberButtonPushed(ActionEvent event) {
        //when searchNumber button is pushed, no need to check in the tableView

        //read the string from searchNumberTextField

        //check if the format that user entered matches the given format
        //call serialNumberValidator in the AddEditWindowController class
        //pass in the String read from the textField
        //if string is valid (returned value is true), proceed
        //if string is invalid (return value is false)
        //  set the errorDisplayLabel to prompt user as "you have entered invalid format serialNumber"

        //call method searchNumber in ListWrapper class, passing in actual list as (list, string)
        //where the string is what's read from the textField

        //check the return value
        //if method return -1, means search result is not found
        //  set errorDisplayLabel to prompt user "Sorry! we don't have any item with that serial number in stock!"
        //if method return non-positive, which represents the index found in the list
        //create a tempList, add the item in actual list from that index to it
        //clear observableList, this will clear the tableView
        //assigned the observableList with values within tempList (should have only one item to it) to set the tableView
    }

    @FXML
    void sortByNameButtonPushed(ActionEvent event) {
        //call a sortListByName method from ListSorter class pass in actual list
        //the returned list will be a sorted version of actual list
        //clear observableList (to clear tableView)
        //assigned the observableList with values within the list to set the tableView
    }

    @FXML
    void sortByNumberButtonPushed(ActionEvent event) {
        //call a sortListByNumber method from ListSorter class pass in actual list
        //the returned list will be a sorted version of actual list
        //clear observableList (to clear tableView)
        //assigned the observableList with values within the list to set the tableView
    }

    @FXML
    void sortByPriceButtonPushed(ActionEvent event) {
        //call a sortListByPrice method from ListSorter class pass in actual list
        //the returned list will be a sorted version of actual list
        //clear observableList (to clear tableView)
        //assigned the observableList with values within the list to set the tableView
    }

    public void initializeListWrapper(ListWrapper listWrapper) {
        //this method is a receiver side from data passing process between scenes

        //set listWrapper instance variable to the one passed from another class (to initialize it)

        //set the observableList to the list within listWrapper
        //set the tableView
    }

    private void changeScene() {
        //this method is a sender side from data passing process between scenes

        //this method will always be call to pre-load information before the actual scene changing and change scene
        //since the app only have 2 scenes, this method will only be pointing to AddEditWindow.fxml

        //create a FXMLLoader pointing at AddEditWindow.fxml
        //load the loader
        //create the controller from AddEditWindowController class

        //call initializeListWrapper in addEditWindow's controller
        //  the method will receive the information where it must be done after we loaded the controller
        //  if not, the information passed to that controller will not be successful

        //create a stage with this stage information from one of the controller we used in this scene
        //set the scene and show it (now the scene will be moved)
    }

    public void initialize() {
        //this method is called automatically each time the scene MainWindowController.fxml

        //set errorDisplayMessage to emptyString

        //init fileChooser to accept extensions of .json, .html and .txt

        //initialize observableList with the current information in the list in listWrapper

        //set up listener to tableView, to listen and get the index whenever user select something in the tableView
        //  each time something is selected, set 'index' to it (used in this class)
        //  also update the 'index' within listWrapper as well (used to pass info between classes)

        //set all columns in the tableView to look for a certain object in each ItemObject
        //itemNumberColumn is looking for 'serialNumber'
        //itemNameColumn is looking for 'name'
        //itemNameColumn is looking for 'price'

        //initialize the tableView
    }
}

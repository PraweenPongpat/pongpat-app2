/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindowController {

    //FXML variables used for GUI
    //note that buttons will not have its name, since we only use its functionality when pushed
    @FXML
    private Label errorDisplayLabel;
    @FXML
    private TableColumn<ItemObject, String> itemNameColumn;
    @FXML
    private TableColumn<ItemObject, String> itemNumberColumn;
    @FXML
    private TableColumn<ItemObject, String> itemPriceColumn;
    @FXML
    private TextField searchNameTextField;
    @FXML
    private TextField searchNumberTextField;
    @FXML
    private TableView<ItemObject> tableView;

    //listWrapper class that contain actual object for the app
    private ListWrapper listWrapper = new ListWrapper();
    //observableList uses to display items to tableView
    private ObservableList<ItemObject> observableList;
    //AddEditWindowController class's methods will be needed uses to pass information between scenes
    private final AddEditWindowController addEditWindowController = new AddEditWindowController();
    //index to keep up with the index chosen in the tableView
    private int index = -1;  //always default -1, meaning that nothing is selected in the tableView yet

    //other parameters... like
    private final FileChooser fileChooser = new FileChooser();
    private File file;
    private final FilesInputOutput filesInputOutput = new FilesInputOutput();
    private final ListSorter listSorter = new ListSorter();


    @FXML
    void removeOneItemButtonPushed() {
        //when removeOneItem button is pushed
        //there MUST be a selected item in the tableView (from listener)

        //check the index
        //if index is less than zero (default as negative) meaning there's nothing selected in tableView
        if(index < 0 || listWrapper.getIndex()<0) {
            //  display errorDisplayLabel to prompt user "you must select an item from the list before removing"
            errorDisplayLabel.setText("you must select an item from the list before removing");
            //  end method, do not do anything else
        }
            //if index is zero or greater, meaning there's something selected in the tableView
        else {
            //ensure to set errorDisplayLabel to empty string
            errorDisplayLabel.setText("");

            //  call method removeOneItem in ListWrapper class to remove that index from the list
            //      the function should take in a (List, index)
            listWrapper.removeOneItem(index);
            //  remove the index from observableList also, to update the tableView
            observableList.remove(index);
            tableView.setItems(observableList);
        }
    }

    @FXML
    void addItemButtonPushed() {
        //when addItem button is pushed, no need to check in the tableView

        //set value 'isAdding' in listWrapper to 'true', since we are adding
        //isAdding will determine the setup in the scene whether it's being added or edited
        listWrapper.setAdding(true);

        //change scene to AddEditWindow.fxml process by calling changeScene method
        changeScene();
    }

    @FXML
    void editItemButtonPushed() {
        //when editItem button is pushed
        //there MUST be a selected item in the tableView (from listener)

        //check the index
        //if index is less than zero (default as negative) meaning there's nothing selected in tableView
        if (index < 0 || listWrapper.getIndex()<0) {
            //  display errorDisplayLabel to prompt user "you must select an item to edit its information"
            errorDisplayLabel.setText("you must select an item to edit its information");
            //  end method, do not do anything else
        }
        //if index is zero or greater, meaning there's something selected in the tableView
        else {
            //ensure to set errorDisplayLabel to empty string
            errorDisplayLabel.setText("");

            //  set value 'isAdding' in listWrapper to 'false', since we are editing
            listWrapper.setAdding(false);
            //  change scene to AddEditWindow.fxml process by calling changeScene method
                changeScene();
        }
    }

    @FXML
    void removeAllItemButtonPushed() {
        //when removeAllItem button is pushed, no need to check in the tableView

        //call method removeAllItem in ListWrapper class, passed in the list from listWrapper
        listWrapper.removeAllItem(listWrapper.getListOfItem());
        //clear all data in the observableList to update tableView
        observableList.clear();
        tableView.setItems(observableList);
    }

    @FXML
    void saveButtonPushed() {
        //when save button is pushed, we will be calling a fileChooser to get a File from it
        fileChooser.setTitle("save file");
        Stage stage = (Stage) errorDisplayLabel.getScene().getWindow();
        file = fileChooser.showSaveDialog(stage);

        //if File is null, means user doesn't try to save file (click X (close) or cancel)
        if (file == null) {
            //  set errorDisplayLabel to let user know that "save failed, please try again"
            errorDisplayLabel.setText("save failed, please try again");
            //  end method
        }
        //if file is not null,
        else {
            //ensure to set errorDisplayLabel to empty string
            errorDisplayLabel.setText("");

            //check if the string (path name) in that file has what extension and calls FilesInputOutput method accordingly
            if (file.toString().contains(".txt")) {
                //for .txt, call saveListAsTSV in FilesInputOutput class
                filesInputOutput.saveListAsTSV(file,listWrapper.getListOfItem());
            }
            else if (file.toString().contains(".json")) {
                //for .json, call saveListAsJSON in FilesInputOutput class
                filesInputOutput.saveListAsJSON(file,listWrapper);
            }
            else if (file.toString().contains(".html")) {
                //for .html, call saveListAsHTML in FilesInputOutput class
                filesInputOutput.saveListAsHTML(file,listWrapper.getListOfItem());
            }
        }
    }

    @FXML
    void loadButtonPushed() {
        //when load button is pushed, we will be calling a fileChooser to get a File from it
        fileChooser.setTitle("save file");
        Stage stage = (Stage) errorDisplayLabel.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);

        //if File is null, means user doesn't try to save file (click X (close) or cancel)
        if (file == null) {
            //  set errorDisplayLabel to let user know that "load failed, please try again"
            errorDisplayLabel.setText("load failed, please try again");
            //  end method
        }

        //if file is not null,
        else {
            //ensure to set errorDisplayLabel to empty string
            errorDisplayLabel.setText("");

            //clear actual list and observableList
            listWrapper.getListOfItem().clear();
            observableList.clear();

            List<ItemObject> tempList = new ArrayList<>();
            //check if the string (path name) in that file has what extension and calls FilesInputOutput method accordingly
            if (file.toString().contains(".txt")) {
                //for .txt, call loadFromTSV in FilesInputOutput class
                tempList = filesInputOutput.loadFromTSV(file);
            }
            else if (file.toString().contains(".html")) {
                //for .html, call loadFromHTML in FilesInputOutput class
                tempList = filesInputOutput.loadFromHTML(file);
            }
            else if (file.toString().contains(".json")) {
                //for .json, call loadFromJSON in FilesInputOutput class
                tempList = filesInputOutput.loadFromJSON(file);
            }

            //check the size of the list that is returned from the function
            if ((tempList == null) || tempList.isEmpty()) {
                errorDisplayLabel.setText("the file is empty, or contaminated: file cannot be opened");
            } else {
                //ensure to set errorDisplayLabel to empty string
                errorDisplayLabel.setText("");

                //copy each element of that list
                for (ItemObject itemObject : tempList) {
                    listWrapper.getListOfItem().add(itemObject);
                }
                //set the observableList to the list
                observableList = FXCollections.observableArrayList(listWrapper.getListOfItem());
                //this is the update the tableView to the loaded list
                tableView.setItems(observableList);
            }
        }
    }

    @FXML
    void searchNameButtonPushed() {
        //when searchName button is pushed, no need to check in the tableView

        //we are only focus on finding one aspect at a time, either name or serial number
        //we will set the text of the other textField to emptyString
        searchNumberTextField.setText("");

        //read the string from searchNameTextField
        String nameInput = searchNameTextField.getText();

        //call method searchName in ListWrapper class, passing in actual list as (list, string)
        //where the string is what's read from the textField
        //
        List<Integer> sameNameList = listWrapper.searchName(nameInput);

        //check the return value
        //if method return empty list means it's not found
        if(sameNameList.isEmpty()) {
            //  set errorDisplayLabel to prompt user "Sorry! we don't have any item with that name in stock!"
            errorDisplayLabel.setText("Sorry! we don't have any item with that name in stock!");
        }
        //if method return non-positive, which represents the index found in the list
        else {
            //ensure to set errorDisplayLabel to empty string
            errorDisplayLabel.setText("");

            //create a tempList, add the item in actual list from that index to it
            List<ItemObject> tempList = new ArrayList<>();
            for (Integer integer : sameNameList) {
                tempList.add(listWrapper.getListOfItem().get(integer));
            }
            //clear observableList, this will clear the tableView
            observableList.clear();
            //assigned the observableList with values within tempList (should have only one item to it) to set the tableView
            observableList = FXCollections.observableArrayList(tempList);
            tableView.setItems(observableList);

            //since the search is found, reset the textField to empty string
            searchNameTextField.setText("");
        }
    }

    @FXML
    void searchNumberButtonPushed() {
        //when searchNumber button is pushed, no need to check in the tableView

        //we are only focus on finding one aspect at a time, either name or serial number
        //we will set the text of the other textField to emptyString
        searchNameTextField.setText("");

        //read the string from searchNumberTextField
        String numberInput = searchNumberTextField.getText();

        //check if the format that user entered matches the given format
        //call serialNumberValidator in the AddEditWindowController class
        //pass in the String read from the textField
        if(!(addEditWindowController.serialNumberValidator(numberInput))) {
            //if string is invalid (return value is false)
            //  set the errorDisplayLabel to prompt user as "you have entered invalid format serialNumber"
            errorDisplayLabel.setText("you have entered invalid format serialNumber");
            return;
        }
        //if string is valid (returned value is true), proceed

        //call method searchNumber in ListWrapper class, passing in actual list as (list, string)
        //where the string is what's read from the textField
        int indexResult = listWrapper.searchNumber(numberInput);
        //check the return value
        //if method return -1, means search result is not found
        if(indexResult < 0) {
            //  set errorDisplayLabel to prompt user "Sorry! we don't have any item with that serial number in stock!"
            errorDisplayLabel.setText("Sorry! we don't have any item with that serial number in stock!");
        }
        //if method return non-positive, which represents the index found in the list
        else {
            //ensure to set errorDisplayLabel to empty string
            errorDisplayLabel.setText("");

            //create a tempList, add the item in actual list from that index to it
            List<ItemObject> tempList = new ArrayList<>();
            tempList.add(new ItemObject(listWrapper.getListOfItem().get(indexResult).getSerialNumber(),
                    listWrapper.getListOfItem().get(indexResult).getName(),
                    Double.parseDouble(listWrapper.getListOfItem().get(indexResult).getPrice())));
            //clear observableList, this will clear the tableView
            observableList.clear();
            //assigned the observableList with values within tempList (should have only one item to it) to set the tableView
            observableList = FXCollections.observableArrayList(tempList);
            tableView.setItems(observableList);

            //since the search is found, reset the textField to empty string
            searchNumberTextField.setText("");
        }
    }

    @FXML
    void sortByNameButtonPushed() {
        //call a sortListByName method from ListSorter class pass in actual list
        //the returned list will be a sorted version of actual list
        listSorter.sortListByName(listWrapper.getListOfItem());
        //clear observableList (to clear tableView)
        //assigned the observableList with values within the list to set the tableView
        observableList = FXCollections.observableArrayList(listWrapper.getListOfItem());
        tableView.setItems(observableList);
    }

    @FXML
    void sortByNumberButtonPushed() {
        //call a sortListByNumber method from ListSorter class pass in actual list
        //the returned list will be a sorted version of actual list
        listSorter.sortListByNumber(listWrapper.getListOfItem());
        //clear observableList (to clear tableView)
        //assigned the observableList with values within the list to set the tableView
        observableList = FXCollections.observableArrayList(listWrapper.getListOfItem());
        tableView.setItems(observableList);
    }

    @FXML
    void sortByPriceButtonPushed() {
        //call a sortListByPrice method from ListSorter class pass in actual list
        //the returned list will be a sorted version of actual list
        listSorter.sortListByPrice(listWrapper.getListOfItem());
        //clear observableList (to clear tableView)
        //assigned the observableList with values within the list to set the tableView
        observableList = FXCollections.observableArrayList(listWrapper.getListOfItem());
        tableView.setItems(observableList);
    }

    @FXML
    void showAllItemButtonPushed() {
        //when show all button is pushed, we are refreshing the tableView to show all its item
        observableList.clear();
        observableList = FXCollections.observableArrayList(listWrapper.getListOfItem());
        tableView.setItems(observableList);

    }

    public void initializeListWrapper(ListWrapper listWrapper) {
        //this method is a receiver side from data passing process between scenes

        //set listWrapper instance variable to the one passed from another class (to initialize it)
        this.listWrapper = listWrapper;
        //set the observableList to the list within listWrapper
        observableList = FXCollections.observableArrayList(listWrapper.getListOfItem());
        //set the tableView
        tableView.setItems(observableList);
    }

    private void changeScene(){
        //this method is a sender side from data passing process between scenes

        //this method will always be call to pre-load information before the actual scene changing and change scene
        //since the app only have 2 scenes, this method will only be pointing to AddEditWindow.fxml

        //create a FXMLLoader pointing at AddEditWindow.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEditWindow.fxml"));
        Parent root = null;
        //load the loader
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //create the controller from AddEditWindowController class
        AddEditWindowController controller = loader.getController();
        //call initializeListWrapper in addEditWindow's controller
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

    public void initialize() {
        //set errorDisplayMessage to emptyString
        errorDisplayLabel.setText("");

        //init fileChooser to accept extensions of .json, .html and .txt
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text files", "*.txt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("html files", "*.html"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("json files", "*.json"));

        //initialize observableList with the current information in the list in listWrapper
        observableList = FXCollections.observableArrayList(listWrapper.getListOfItem());

        //set up listener to tableView, to listen and get the index whenever user select something in the tableView
        //  each time something is selected, set 'index' to it (used in this class)
        //  also update the 'index' within listWrapper as well (used to pass info between classes)
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    index = observableList.indexOf(tableView.getSelectionModel().getSelectedItem());
                    listWrapper.setIndex(index);
                });

        //set all columns in the tableView to look for a certain object in each ItemObject
        //itemNumberColumn is looking for 'serialNumber'
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        //itemNameColumn is looking for 'name'
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //itemNameColumn is looking for 'price'
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //initialize the tableView
        tableView.setItems(observableList);
    }
}

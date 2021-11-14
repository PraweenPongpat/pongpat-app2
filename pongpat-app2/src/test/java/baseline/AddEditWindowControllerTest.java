package baseline;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddEditWindowControllerTest {
    private final AddEditWindowController addEditWindowController = new AddEditWindowController();

    //test against requirement#1.1.1, price must not be negative
    @Test
    void priceValidationTest() {
        //test prices with negative, expected false
        boolean negativeTest = addEditWindowController.priceValidator("-1.00");
        assertFalse(negativeTest);

        //test prices with zero, expected true
        boolean zeroTest = addEditWindowController.priceValidator("0.00");
        assertTrue(zeroTest);

        //test price with positive, expected true
        boolean positiveTest = addEditWindowController.priceValidator("5.02");
        assertTrue(positiveTest);
    }

    //test against requirement#1.2, format A-xxx-xxx-xxx where A = letter, X = letter OR number
    @Test
    void serialNumberFormatValidationTest() {
        //test when A not a letter, expected false
        boolean aNonLetter1 = addEditWindowController.serialNumberValidator("1-111-111-111");
        assertFalse(aNonLetter1);
        boolean aNonLetter2 = addEditWindowController.serialNumberValidator("#-aaa-aaa-aaa");
        assertFalse(aNonLetter2);

        //test when the length doesn't match
        boolean lengthTooShortTest = addEditWindowController.serialNumberValidator("a-111-111-1");
        assertFalse(lengthTooShortTest);
        boolean lengthTooLongTest = addEditWindowController.serialNumberValidator("a-aaa-123-123-123");
        assertFalse(lengthTooLongTest);

        //test when x is neither letter nor number (special char)
        boolean specialCharTest = addEditWindowController.serialNumberValidator("a-###-%%%-^^^");
        assertFalse(specialCharTest);

        //test when format is correct
        boolean correctFormatTest = addEditWindowController.serialNumberValidator("A-AAA-123-456");
        assertTrue(correctFormatTest);
    }

    //test against requirement#1.2.1 serial number must be unique
    @Test
    void uniqueSerialNumberTest() {
        List<ItemObject> tempList = new ArrayList<>();
        tempList.add(new ItemObject("A-AAA-AAA-AAA","name1",15.15));
        tempList.add(new ItemObject("A-AAA-111-111","name2",2020.20));
        //we are passing in the same serial number that is existed
        //'index' parameter in the function is used when editing (edit something in the existed element in the list)

        //try adding a new item to the existing item, index = -1, isAdding = true
        boolean addExisted = addEditWindowController.isNumberAlreadyExisted(
                tempList,"A-AAA-AAA-AAA", -1,true);
        //we expected to return true, since the serial number already existed
        assertTrue(addExisted);

        //try editing an item to the existing number, index = that index, isAdding = false
        boolean editExisted = addEditWindowController.isNumberAlreadyExisted(
                tempList,"A-AAA-111-111",0,false);
        //we expected to return true, since the serial number already existed
        assertTrue(editExisted);

        //if editing to something new, we will expect false
        boolean editSomethingNew = addEditWindowController.isNumberAlreadyExisted(
                tempList,"A-AAA-AAA-123",0,false);
        assertFalse(editSomethingNew);
    }

    //test against requirement#1.3.1 name length must be 2-256 inclusive
    @Test
    void nameLengthTest() {
        //test with a short name (empty name, less than 2 name), expected false
        boolean emptyName = addEditWindowController.nameLengthValidator("");
        assertFalse(emptyName);
        boolean nameTooShort = addEditWindowController.nameLengthValidator("a");
        assertFalse(nameTooShort);

        //test with a really long name at 260 (over 256), expected false
        boolean nameTooLong = addEditWindowController.nameLengthValidator(
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+
        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+
        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        assertFalse(nameTooLong);

        //test with name at 2, 10, and 256, expected all to be true
        boolean minNameLength = addEditWindowController.nameLengthValidator("AA");
        assertTrue(minNameLength);
        boolean mediumNameLength = addEditWindowController.nameLengthValidator("AAAAAAAAAA");
        assertTrue(mediumNameLength);
        boolean maxNameLength = addEditWindowController.nameLengthValidator(
    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        assertTrue(maxNameLength);
    }


}
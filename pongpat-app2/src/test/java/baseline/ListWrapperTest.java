package baseline;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListWrapperTest {
    private final ListWrapper listWrapper = new ListWrapper();

    //test against requirement#1, size at least 1024
    @Test
    void sizeRequirementTest() {
        String tempNumber;
        String tempName;
        String tempPrice;
        //add the first 1000 items
        for(int i = 0; i<999;i++) {
            tempNumber = String.format("%s%03d","a-000-000-",i);
            tempName = "testItemName#"+i;
            tempPrice = Double.toString(i+0.01);
            listWrapper.addItemToList(tempNumber,tempName,tempPrice);
        }
        //add another 50 items
        for(int i = 0; i<50;i++) {
            tempNumber = String.format("%s%03d","b-000-000-",i);
            tempName = "testItemName#"+(i+1000);
            tempPrice = Double.toString(i+0.01+1000);
            listWrapper.addItemToList(tempNumber,tempName,tempPrice);
        }
        //item in the list is now over 1024
        boolean sizeTest = listWrapper.getListOfItem().size() > 1024;
        assertTrue(sizeTest);
    }

    //test against requirement#2, add item to the list
    //requirement#2.1 2.2 2.3 is not testable, since it deals with a label controller in fxml directly
    @Test
    void addItemTest() {
        //from the start, add something to the list
        listWrapper.addItemToList("A-111-111-111","name#1","123.12");
        //in we have one item now, expected size = 1
        assertEquals(1,listWrapper.getListOfItem().size());

        //add again, see the change in size to 2
        listWrapper.addItemToList("A-222-222-222","name#2","321.32");
        assertEquals(2,listWrapper.getListOfItem().size());
    }

    //test against requirement#3, remove a single item from the list
    @Test
    void removeSingleItemTest() {
        //first, add something to the list
        listWrapper.addItemToList("A-111-111-111","name#1","1111.11");
        listWrapper.addItemToList("A-222-222-222","name#2","2222.22");

        //now, try to remove name2 out, expected size = 1
        listWrapper.removeOneItem(1);
        assertEquals(1,listWrapper.getListOfItem().size());
        //remove another, expected size = 0
        listWrapper.removeOneItem(0);
        assertEquals(0,listWrapper.getListOfItem().size());
    }

    //test against requirement#4, remove all item from the list
    @Test
    void removeAllItemTest() {
        //first, add something to the list
        listWrapper.addItemToList("A-111-111-111","name#1","1111.11");
        listWrapper.addItemToList("A-222-222-222","name#2","2222.22");
        //expected size before removing = 2
        assertEquals(2,listWrapper.getListOfItem().size());

        //remove all item, expect a new size = 0
        listWrapper.removeAllItem(listWrapper.getListOfItem());
        assertEquals(0,listWrapper.getListOfItem().size());
    }

    //test against requirement#5,6,7 editing category
    //requirement#5.1 6.1 7.1 is not testable, since it deals with a label controller in fxml directly
    @Test
    void editingTest() {
        //note that before reaching the editing process, ALL the validation must be passed before.
        //regardless of what we are editing, all field must already pass all validations
        //We are just edit it

        //assume initial item
        listWrapper.addItemToList("A-AAA-AAA","nameTest","1111.11");

        //we are editing the information and test it later
        String editedNumber = "Z-ZZZ-ZZZ-ZZZ";
        String editedName = "newName";
        String price = "100.01";
        listWrapper.editItemToList(editedNumber,editedName,price,0);    //we are editing an index0
        //check each individual values that was edited
        //test against requirement#6 edit a serial number
        assertEquals("Z-ZZZ-ZZZ-ZZZ",listWrapper.getListOfItem().get(0).getSerialNumber());
        //test against requirement#7 edit a name
        assertEquals("newName",listWrapper.getListOfItem().get(0).getName());
        //test against requirement#5 edit a price
        assertEquals("100.01",listWrapper.getListOfItem().get(0).getPrice());
    }

    //test against requirement#11, search by serial number
    @Test
    void searchSerialNumberTest() {
        //add items to the list
        listWrapper.addItemToList("A-AAA-AAA-AAA","name0","100.05");
        listWrapper.addItemToList("B-BBB-BBB-BBB","name1","500.99");
        listWrapper.addItemToList("B-BBB-BBB-CCC","name2","555.99");

        //test on finding whole serial number
        //call a search, test the return list
        //test on found number, returned list will have all index of that number in it
        List<Integer> foundTest1 = listWrapper.searchNumber("A-AAA-AAA-AAA");
        //expected size of the list = 1 at index#0
        assertEquals(1,foundTest1.size());
        assertEquals(0,foundTest1.get(0));

        //test on finding substring of serial number
        //call a search, test the return list
        //test on found number, returned list will have all index of that number in it
        List<Integer> foundTest2 = listWrapper.searchNumber("BBB");
        //expected size of the list = 2 at index#1 and index#2
        assertEquals(2,foundTest2.size());
        assertEquals(1,foundTest2.get(0));
        assertEquals(2,foundTest2.get(1));

        //test on un-found search
        List<Integer> foundTest3 = listWrapper.searchNumber("ZZZ");
        //expected empty list
        assertTrue(foundTest3.isEmpty());
    }

    //test against requirement#12, search by name (name can be the same)
    @Test
    void searchSerialNameTest() {
        //add items to the list
        listWrapper.addItemToList("A-AAA-AAA-AAA","name0","100.05");
        listWrapper.addItemToList("B-BBB-BBB-BBB","name1","500.99");
        listWrapper.addItemToList("C-CCC-CCC-CCC","name1","800.88");

        //call a search, test the return list
        //test on found name, returned list will have all index of that name in it (name can be the same)
        List<Integer> foundTest = listWrapper.searchName("name1");
        //expected size of the list = 2, it should contain index#1 and index#2
        assertEquals(2,foundTest.size());
        assertEquals(1,foundTest.get(0));
        assertEquals(2,foundTest.get(1));

        //test on finding substring of the name
        List<Integer> foundTest1 = listWrapper.searchName("na");
        //expected size of the list = 3, since all name has "na"
        assertEquals(3,foundTest1.size());
        assertEquals(0,foundTest1.get(0));
        assertEquals(1,foundTest1.get(1));
        assertEquals(2,foundTest1.get(2));

        //test on not found list, expected emptyList in return
        List<Integer> notFoundTest = listWrapper.searchName("No name");
        assertTrue(notFoundTest.isEmpty());
    }
}
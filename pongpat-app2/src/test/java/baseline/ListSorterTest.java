package baseline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListSorterTest {
    private final ListSorter listSorter = new ListSorter();
    private final ListWrapper listWrapper = new ListWrapper();

    //helper function to add stuff to the list
    void addThingsToList(ListWrapper listWrapper) {
        listWrapper.addItemToList("A-999-999-999","name0","111.11");
        listWrapper.addItemToList("A-000-000-000","name1","333.33");
        listWrapper.addItemToList("A-555-555-555","name3","999.99");
        listWrapper.addItemToList("A-444-444-444","name2","222.22");
    }

    //test against requirement#8 sort by value (sort by price)
    @Test
    void sortByPriceTest() {
        //add things to list, and sort by price
        addThingsToList(listWrapper);
        listSorter.sortListByPrice(listWrapper.getListOfItem());

        //expected the price from 111.11, then 222.22, then 333.33, then 999.99
        assertEquals("111.11",listWrapper.getListOfItem().get(0).getPrice());
        assertEquals("222.22",listWrapper.getListOfItem().get(1).getPrice());
        assertEquals("333.33",listWrapper.getListOfItem().get(2).getPrice());
        assertEquals("999.99",listWrapper.getListOfItem().get(3).getPrice());
    }

    //test against requirement#9 sort by serial number
    @Test
    void sortBySerialNumberTest() {
        //add things to list, and sort by serial number
        addThingsToList(listWrapper);
        listSorter.sortListByNumber(listWrapper.getListOfItem());

        //expected the price from 000, 444, 555 then 999
        assertEquals("A-000-000-000",listWrapper.getListOfItem().get(0).getSerialNumber());
        assertEquals("A-444-444-444",listWrapper.getListOfItem().get(1).getSerialNumber());
        assertEquals("A-555-555-555",listWrapper.getListOfItem().get(2).getSerialNumber());
        assertEquals("A-999-999-999",listWrapper.getListOfItem().get(3).getSerialNumber());
    }

    //test against requirement#8 sort by value (sort by price)
    @Test
    void sortByNameTest() {
        //add things to list, and sort by price
        addThingsToList(listWrapper);
        listSorter.sortListByName(listWrapper.getListOfItem());

        //expected the price from name 0 1 2 3
        assertEquals("name0",listWrapper.getListOfItem().get(0).getName());
        assertEquals("name1",listWrapper.getListOfItem().get(1).getName());
        assertEquals("name2",listWrapper.getListOfItem().get(2).getName());
        assertEquals("name3",listWrapper.getListOfItem().get(3).getName());
    }
}

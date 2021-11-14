package baseline;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class FilesInputOutputTest {
    private final FilesInputOutput filesInputOutput = new FilesInputOutput();
    private final ListWrapper listWrapper = new ListWrapper();

    //helper method add things to list
    private void addThingsToList(ListWrapper wrapper) {
        wrapper.addItemToList("A-AAA-AAA-AAA","name0","111.11");
        wrapper.addItemToList("B-BBB-BBB-BBB","name1","222.22");
        wrapper.addItemToList("C-CCC-CCC-CCC","name2","333.33");
    }

    //test against requirement#13.1, save as TSV with .txt file
    @Test
    void saveTSVTest () {
        //create dummy items to the list
        addThingsToList(listWrapper);

        //save the list with the given file
        //the file will always be picked from fileChooser
        File file = new File("data\\testFileTSV.txt");
        filesInputOutput.saveListAsTSV(file,listWrapper.getListOfItem());

        //test to see if the file exists
        assertTrue(file.exists());
    }

    //test against requirement#13.2, load from TSV file
    @Test
    void loadFromTSVTest() {
        //make the original list
        ListWrapper tempWrapper = new ListWrapper();
        addThingsToList(tempWrapper);
        //load the content to another list
        File file = new File("data\\testFileTSV.txt");
        List<ItemObject> loadResultTest = filesInputOutput.loadFromTSV(file);

        //compare the two, check the both has same size, and its content
        assertEquals(tempWrapper.getListOfItem().size(),loadResultTest.size());
        for(int i = 0; i<loadResultTest.size();i++) {
            assertEquals(tempWrapper.getListOfItem().get(i).getSerialNumber(),loadResultTest.get(i).getSerialNumber());
            assertEquals(tempWrapper.getListOfItem().get(i).getName(),loadResultTest.get(i).getName());
            assertEquals(tempWrapper.getListOfItem().get(i).getPrice(),loadResultTest.get(i).getPrice());
        }
    }

    //test against requirement#13.1, save as HTML file
    @Test
    void saveHTMLTest() {
        //create dummy items to the list
        addThingsToList(listWrapper);

        //save the list with the given file
        //the file will always be picked from fileChooser
        File file = new File("data\\testFileHTML.html");
        filesInputOutput.saveListAsHTML(file,listWrapper.getListOfItem());

        //test to see if the file exists
        assertTrue(file.exists());
    }

    //test against requirement#13.2, load from HTML file
    @Test
    void loadFromHTMLTest() {
        //make the original list
        ListWrapper tempWrapper = new ListWrapper();
        addThingsToList(tempWrapper);
        //load the content to another list
        File file = new File("data\\testFileHTML.html");
        List<ItemObject> loadResultTest = filesInputOutput.loadFromHTML(file);

        //compare the two, check the both has same size, and its content
        assertEquals(tempWrapper.getListOfItem().size(),loadResultTest.size());
        for(int i = 0; i<loadResultTest.size();i++) {
            assertEquals(tempWrapper.getListOfItem().get(i).getSerialNumber(),loadResultTest.get(i).getSerialNumber());
            assertEquals(tempWrapper.getListOfItem().get(i).getName(),loadResultTest.get(i).getName());
            assertEquals(tempWrapper.getListOfItem().get(i).getPrice(),loadResultTest.get(i).getPrice());
        }
    }

    //test against requirement#13.1, save as JSON file
    @Test
    void saveJSONTest() {
        //create dummy items to the list
        addThingsToList(listWrapper);

        //save the list with the given file
        //the file will always be picked from fileChooser
        File file = new File("data\\testFileJSON.json");
        filesInputOutput.saveListAsJSON(file,listWrapper);

        //test to see if the file exists
        assertTrue(file.exists());
    }

    //test against requirement#13.2, load from HTML file
    @Test
    void loadFromJSONTest() {
        //make the original list
        ListWrapper tempWrapper = new ListWrapper();
        addThingsToList(tempWrapper);
        //load the content to another list
        File file = new File("data\\testFileJSON.json");
        List<ItemObject> loadResultTest = filesInputOutput.loadFromJSON(file);

        //compare the two, check the both has same size, and its content
        assertEquals(tempWrapper.getListOfItem().size(),loadResultTest.size());
        for(int i = 0; i<loadResultTest.size();i++) {
            assertEquals(tempWrapper.getListOfItem().get(i).getSerialNumber(),loadResultTest.get(i).getSerialNumber());
            assertEquals(tempWrapper.getListOfItem().get(i).getName(),loadResultTest.get(i).getName());
            assertEquals(tempWrapper.getListOfItem().get(i).getPrice(),loadResultTest.get(i).getPrice());
        }
    }
}
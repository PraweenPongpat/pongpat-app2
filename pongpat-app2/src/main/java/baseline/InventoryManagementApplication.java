/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.util.Objects;

public class InventoryManagementApplication extends Application {

    //start function to launch the root window
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
        Scene scene = new Scene(root);

        //set title of the stage (will always use the same stage along the app, except errorDisplay)
        //error display stage will always be a new stage when launched
        stage.setTitle("InventoryManagementApplication");

        //set the theme from J-metro
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        //load and display a new scene
        stage.setScene(scene);
        stage.show();
    }

    //main to launch GUI
    public static void main(String[] args) {
        launch(args);
    }
}

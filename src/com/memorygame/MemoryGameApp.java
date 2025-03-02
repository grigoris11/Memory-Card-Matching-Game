package com.memorygame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MemoryGameApp extends Application {

    // Μέθοδος εκκίνησης της εφαρμογής
    @Override
    public void start(Stage primaryStage) {
        // Δημιουργία κύριου μενού και ορισμός του στη σκηνή
        MainMenu mainMenu = new MainMenu(primaryStage);
        Scene scene = new Scene(mainMenu.getLayout(), 800, 600); // Ορισμός διαστάσεων παραθύρου

        // Ορισμός τίτλου παραθύρου
        primaryStage.setTitle("Memory Game");
        primaryStage.setScene(scene);
        primaryStage.show(); // Εμφάνιση παραθύρου
    }

    // Κύρια μέθοδος για εκκίνηση της εφαρμογής
    public static void main(String[] args) {
        launch(args); // Εκκίνηση της εφαρμογής JavaFX
    }
}

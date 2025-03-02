package com.memorygame;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {
    // Κύρια διάταξη του μενού
    private BorderPane mainLayout; // Διάταξη BorderPane
    private static List<String> recentPlayers = new ArrayList<>(); // Λίστα για τους πρόσφατους παίκτες

    public MainMenu(Stage primaryStage) {
        // Δημιουργία κύριας διάταξης
        mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: lightblue; -fx-padding: 20px;");

        // Ενότητα οδηγιών (επάνω δεξιά)
        VBox instructionsBox = new VBox(5);
        instructionsBox.setStyle("-fx-background-color: white; -fx-padding: 10px; -fx-border-color: black;");
        instructionsBox.setAlignment(Pos.TOP_RIGHT);

        // Προσθήκη οδηγιών
        Label instructionsTitle = new Label("Instructions");
        instructionsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label instruction1 = new Label("Hi! Thanks for playing our game.");
        Label instruction2 = new Label("1. Select a card type.");
        Label instruction3 = new Label("2. Pick the difficulty level.");
        Label instruction4 = new Label("3. Don't forget to write your name.");
        Label instruction5 = new Label("4. You can cancel your try anytime.");
        Label instruction6 = new Label("5. Win by revealing all cards within your tries!");

        instructionsBox.getChildren().addAll(
                instructionsTitle,
                instruction1,
                instruction2,
                instruction3,
                instruction4,
                instruction5,
                instruction6
        );

        // Ενότητα Πρόσφατοι Παίκτες (αριστερά)
        VBox recentPlayersBox = new VBox(5);
        recentPlayersBox.setStyle("-fx-background-color: white; -fx-padding: 10px; -fx-border-color: black;");
        recentPlayersBox.setAlignment(Pos.TOP_LEFT);

        // Προσθήκη τίτλου για τους πρόσφατους παίκτες
        Label recentPlayersTitle = new Label("Recent Players");
        recentPlayersTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        recentPlayersBox.getChildren().add(recentPlayersTitle);

        // Προβολή πρόσφατων παικτών
        for (String player : recentPlayers) {
            Label playerLabel = new Label(player);
            playerLabel.setStyle("-fx-font-size: 14px;");
            recentPlayersBox.getChildren().add(playerLabel);
        }

        // Κύρια διάταξη ελέγχου παιχνιδιού (Κέντρο)
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);

        // Προσθήκη πεδίων εισαγωγής και επιλογών
        Label welcomeLabel = new Label("Welcome to Memory Game!");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField playerNameField = new TextField();
        playerNameField.setPromptText("Enter your name");

        ComboBox<String> cardTypeSelector = new ComboBox<>();
        cardTypeSelector.getItems().addAll("Numbers", "Animals", "Objects");
        cardTypeSelector.setPromptText("Select Card Type");

        ComboBox<String> difficultySelector = new ComboBox<>();
        difficultySelector.getItems().addAll("Easy (4x4)", "Medium (8x8)", "Hard (10x10)");
        difficultySelector.setPromptText("Select Difficulty Level");

        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-background-color: yellow; -fx-font-size: 16px;");
        startButton.setOnAction(event -> {
            // Έναρξη παιχνιδιού
            String playerName = playerNameField.getText();
            String cardType = cardTypeSelector.getValue();
            String difficulty = difficultySelector.getValue();

            if (playerName.isEmpty() || cardType == null || difficulty == null) {
                welcomeLabel.setText("Please fill in all fields to start!");
                return;
            }

            GameBoard gameBoard = new GameBoard(primaryStage, playerName, cardType, difficulty);
            primaryStage.getScene().setRoot(gameBoard.getLayout());
        });

        layout.getChildren().addAll(
                welcomeLabel,
                playerNameField,
                cardTypeSelector,
                difficultySelector,
                startButton
        );

        // Κουμπί κλεισίματος εφαρμογής
        Button closeButton = new Button("Close Application");
        closeButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 16px;");
        closeButton.setOnAction(event -> {
            primaryStage.close(); // Κλείσιμο εφαρμογής
        });

        // Υποσημείωση στο κάτω δεξιά μέρος
        Label footerLabel = new Label("Created by Grigoris Koutlis AM:3212019098 Undergraduate Student of ICSD");
        footerLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");
        BorderPane.setAlignment(footerLabel, Pos.BOTTOM_RIGHT);

        // Προσθήκη διατάξεων στο κύριο μενού
        mainLayout.setTop(instructionsBox); // Οδηγίες στο πάνω δεξιά
        mainLayout.setLeft(recentPlayersBox); // Πρόσφατοι παίκτες στα αριστερά
        mainLayout.setCenter(layout); // Κύριος έλεγχος στο κέντρο
        mainLayout.setBottom(new VBox(closeButton, footerLabel)); // Κουμπί εξόδου και υποσημείωση στο κάτω μέρος

        BorderPane.setAlignment(closeButton, Pos.CENTER);
        BorderPane.setAlignment(footerLabel, Pos.BOTTOM_RIGHT);
    }

    // Προσθήκη πρόσφατου παίκτη στη λίστα
    public static void addRecentPlayer(String playerName, String cardType, boolean won) {
        String result = won ? "Win" : "Loss";
        recentPlayers.add(0, playerName + " - " + cardType + " - " + result);

        if (recentPlayers.size() > 10) {
            recentPlayers.remove(recentPlayers.size() - 1);
        }
    }

    // Επιστροφή της κύριας διάταξης
    public BorderPane getLayout() {
        return mainLayout;
    }
}


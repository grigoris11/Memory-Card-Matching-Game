package com.memorygame;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameBoard {
    // Κλάση για το ταμπλό του παιχνιδιού
    private GridPane layout; // Διάταξη πλέγματος για τις κάρτες
    private Card firstCard; // Πρώτη επιλεγμένη κάρτα
    private Card secondCard; // Δεύτερη επιλεγμένη κάρτα
    private int remainingTries; // Υπόλοιπες προσπάθειες
    private Label triesLabel; // Ετικέτα εμφάνισης προσπαθειών
    private List<Card> allCards; // Λίστα όλων των καρτών για τον έλεγχο της νίκης

    private Stage primaryStage; // Κύριο παράθυρο εφαρμογής
    private String difficulty; // Επίπεδο δυσκολίας
    private String cardType; // Τύπος καρτών
    private String playerName; // Όνομα παίκτη

    // Κατασκευαστής για το ταμπλό του παιχνιδιού
    public GameBoard(Stage primaryStage, String playerName, String cardType, String difficulty) {
        this.primaryStage = primaryStage; // Ανάθεση παραθύρου
        this.difficulty = difficulty; // Ανάθεση δυσκολίας
        this.cardType = cardType; // Ανάθεση τύπου καρτών
        this.playerName = playerName; // Ανάθεση ονόματος παίκτη

        // Δημιουργία διάταξης πλέγματος
        layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setStyle("-fx-background-color: lightblue; -fx-padding: 20px;");

        int gridSize;

        // Ορισμός μεγέθους πλέγματος και προσπαθειών ανάλογα με τη δυσκολία
        switch (difficulty) {
            case "Easy (4x4)":
                gridSize = 4;
                remainingTries = 8;
                break;
            case "Medium (8x8)":
                gridSize = 8;
                remainingTries = 12;
                break;
            case "Hard (10x10)":
                gridSize = 10;
                remainingTries = 12;
                break;
            default:
                gridSize = 4;
                remainingTries = 8;
        }

        // Εμφάνιση ετικέτας προσπαθειών
        triesLabel = new Label("Remaining Tries: " + remainingTries);
        triesLabel.setStyle("-fx-font-size: 16px;");

        // Δημιουργία όλων των καρτών και ανακάτεμα
        allCards = generateCards(gridSize * gridSize, cardType);
        List<Card> shuffledCards = new ArrayList<>(allCards);
        Collections.shuffle(shuffledCards);

        // Προσθήκη καρτών στο πλέγμα
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Card card = shuffledCards.remove(0);
                layout.add(card.getButton(), j, i);
                card.getButton().setOnAction(event -> handleCardFlip(card));
            }
        }

        // Κουμπί ακύρωσης προσπάθειας
        Button cancelButton = new Button("Cancel Try");
        cancelButton.setStyle("-fx-background-color: yellow; -fx-font-size: 16px;");
        cancelButton.setOnAction(event -> {
            MainMenu mainMenu = new MainMenu(primaryStage);
            primaryStage.getScene().setRoot(mainMenu.getLayout());
        });

        // Προσθήκη ετικέτας και κουμπιού ακύρωσης
        layout.add(triesLabel, 0, gridSize, gridSize, 1);
        layout.add(cancelButton, 0, gridSize + 1, gridSize, 1);
    }

    // Χειρισμός αναστροφής κάρτας
    private void handleCardFlip(Card card) {
        if (card.isFlipped()) {
            return; // Αν η κάρτα είναι ήδη ανοιχτή, αγνοήστε
        }

        card.flip(); // Αναστροφή κάρτας

        if (firstCard == null) {
            firstCard = card; // Επιλογή πρώτης κάρτας
        } else if (secondCard == null) {
            secondCard = card; // Επιλογή δεύτερης κάρτας

            if (firstCard.getId() == secondCard.getId()) {
                // Ταιριαστές κάρτες μένουν ανοιχτές
                firstCard = null;
                secondCard = null;

                // Έλεγχος αν όλες οι κάρτες έχουν αποκαλυφθεί
                checkWinCondition();
            } else {
                // Μη ταιριαστές κάρτες, αναποδογύρισμα μετά από καθυστέρηση
                remainingTries--;
                triesLabel.setText("Remaining Tries: " + remainingTries);

                if (remainingTries == 0) {
                    endGame(false); // Αποτυχία αν οι προσπάθειες τελειώσουν
                    return;
                }

                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> {
                    firstCard.flip();
                    secondCard.flip();
                    firstCard = null;
                    secondCard = null;
                });
                pause.play();
            }
        }
    }

    // Έλεγχος για νίκη
    private void checkWinCondition() {
        boolean allRevealed = allCards.stream().allMatch(Card::isFlipped);

        if (allRevealed) {
            endGame(true); // Νίκη όταν όλες οι κάρτες είναι ανοιχτές
        }
    }

    // Τερματισμός παιχνιδιού
    private void endGame(boolean won) {
        MainMenu.addRecentPlayer(playerName, cardType, won);

        layout.getChildren().clear();
        Label endMessage = new Label(won ? "Congratulations! You won!" : "Game Over! You lost!");
        endMessage.setStyle("-fx-font-size: 20px;");
        layout.add(endMessage, 0, 0);

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(event -> {
            MainMenu mainMenu = new MainMenu(primaryStage);
            primaryStage.getScene().setRoot(mainMenu.getLayout());
        });
        layout.add(backButton, 0, 1);
    }

    // Δημιουργία καρτών
    private List<Card> generateCards(int totalCards, String cardType) {
        List<Card> cards = new ArrayList<>();
        int uniqueCards = totalCards / 2;

        for (int i = 1; i <= uniqueCards; i++) {
            cards.add(new Card(i, cardType));
            cards.add(new Card(i, cardType));
        }
        return cards;
    }

    // Επιστροφή διάταξης
    public GridPane getLayout() {
        return layout;
    }
}

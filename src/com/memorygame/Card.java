package com.memorygame;

import javafx.scene.control.Button;

public class Card {
    // Δημιουργία κλάσης για την κάρτα
    private int id; // Αναγνωριστικό της κάρτας
    private String type; // Τύπος της κάρτας
    private Button button; // Κουμπί για την εμφάνιση της κάρτας
    private boolean isFlipped; // Κατάσταση της κάρτας (γυρισμένη ή όχι)

    // Κατασκευαστής της κλάσης Card
    public Card(int id, String type) {
        this.id = id; // Αρχικοποίηση του αναγνωριστικού
        this.type = type; // Αρχικοποίηση του τύπου
        this.isFlipped = false; // Ορισμός αρχικής κατάστασης σε μη γυρισμένη

        // Δημιουργία κουμπιού με κίτρινο φόντο
        this.button = new Button("?");
        this.button.setStyle("-fx-background-color: yellow; -fx-font-size: 18px;");
    }

    // Μέθοδος για αναστροφή της κάρτας
    public void flip() {
        if (isFlipped) {
            // Επιστροφή σε κλειστή κάρτα
            button.setText("?");
            isFlipped = false;
        } else {
            // Αλλαγή εμφάνισης ανάλογα με τον τύπο κάρτας
            switch (type) {
                case "Numbers":
                    button.setText(Integer.toString(id)); // Αριθμοί
                    break;
                case "Animals":
                    button.setText(getAnimalEmoji(id)); // Ζώα
                    break;
                case "Objects":
                    button.setText(getObjectEmoji(id)); // Αντικείμενα
                    break;
            }
            isFlipped = true; // Ορισμός σε γυρισμένη κατάσταση
        }
    }

    // Μέθοδος για λήψη emoji ζώων
    private String getAnimalEmoji(int id) {
        String[] animals = {"🐶", "🐱", "🐭", "🐹", "🐰", "🦊", "🐻", "🐼"};
        return animals[id % animals.length]; // Επιστροφή αντίστοιχου emoji
    }

    // Μέθοδος για λήψη emoji αντικειμένων
    private String getObjectEmoji(int id) {
        String[] objects = {"🚗", "✈️", "🚀", "🚲", "⏰", "🎸", "📱", "🖥"};
        return objects[id % objects.length]; // Επιστροφή αντίστοιχου emoji
    }

    // Επιστρέφει το ID της κάρτας
    public int getId() {
        return id;
    }

    // Επιστρέφει το κουμπί της κάρτας
    public Button getButton() {
        return button;
    }

    // Επιστρέφει την κατάσταση της κάρτας (αν είναι γυρισμένη ή όχι)
    public boolean isFlipped() {
        return isFlipped;
    }
}

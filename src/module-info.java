module MemoryGameApp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.memorygame to javafx.fxml, javafx.graphics; // Allow reflective access
    exports com.memorygame; // Explicitly export the package
}

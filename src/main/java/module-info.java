module org.example.harjoitustyo_kahvinkeitin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;


    opens org.example.harjoitustyo_kahvinkeitin to javafx.fxml;
    exports org.example.harjoitustyo_kahvinkeitin;
}
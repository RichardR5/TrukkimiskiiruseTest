module org.example.trukkimiskiirusetest2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.trukkimiskiirusetest2 to javafx.fxml;
    exports org.example.trukkimiskiirusetest2;
}
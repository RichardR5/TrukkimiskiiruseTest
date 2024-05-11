package org.example.trukkimiskiirusetest2;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ViganeSisendErind extends Exception{

    private String sõnum;

    // Konstrueerib isendi kindla sõnumiga
    public ViganeSisendErind(String sõnum) {
        this.sõnum = sõnum;
    }

    // Kuvab vea kohta teate
    public void kuvaTeade() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Vigane sisend");
        alert.setHeaderText(null);
        Label info = new Label(sõnum);
        info.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16));
        alert.getDialogPane().setContent(info);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:logo.png"));
        alert.showAndWait();
    }
}

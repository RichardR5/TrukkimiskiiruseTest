package org.example.trukkimiskiirusetest2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Peamenüü extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label testiInfo = new Label("Tee trükkimiskiiruse test, et saada \nteada enda trükkimiskiirus!");
        Label mituSõna = new Label("Sisesta mitu sõna soovid testis trükkida (5, 10, 25, 50)");
        testiInfo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        mituSõna.setFont(Font.font("Arial", FontWeight.SEMI_BOLD,16));

        TextField sõnadeArvSisestus = new TextField();
        sõnadeArvSisestus.setPrefWidth(50);
        sõnadeArvSisestus.setFont(Font.font("Arial", 20));

        Button start = new Button("Alusta testi!");
        start.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Kui vajutatakse "Alusta testi!" nuppu
        start.setOnMouseClicked(mouseEvent -> {
            String sõnadeArvTekst = sõnadeArvSisestus.getText();
            sõnadeArvSisestus.clear();

            // Kui kasutaja on kõik õigesti sisestanud, siis sulgeb peamenüü ja avab uue akna, kus algab test
            try {
                int sõnadeArv = leiaSõnadeArv(sõnadeArvTekst, Arrays.asList(5, 10, 25, 50));
                primaryStage.close();
                alustaTestiga(sõnadeArv);
            // Kui tekib viga sisendiga
            } catch (ViganeSisendErind e) {
                e.kuvaTeade();
            }
        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(sõnadeArvSisestus, start);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(15);
        vBox.getChildren().addAll(testiInfo, mituSõna, hBox);

        BorderPane root = new BorderPane();
        root.setCenter(vBox);

        BackgroundFill bf = new BackgroundFill(Color.LIGHTSTEELBLUE, null, null);
        Background bg = new Background(bf);
        root.setBackground(bg);

        // Tehtud lehel https://logo.com/
        primaryStage.getIcons().add(new Image("file:logo.png"));

        Scene scene = new Scene(root, 425, 180);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trükkimiskiiruse test");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Meetod alustab trükkimiskiiruse testi uues aknas
     * @param sõnadeArv mitu sõna kasutaja trükib
     */
    private void alustaTestiga(int sõnadeArv) {
        Stage newStage = new Stage();
        TrükkimiskiiruseTest trükkimiskiiruseTest = new TrükkimiskiiruseTest(sõnadeArv);
        trükkimiskiiruseTest.start(newStage);
    }

    /**
     * Meetod leiab sõnade arvu kasutaja sisendist
     * @param sõnuTestisTekst kasutaja sisend
     * @param sobivadArvud sobivad sõnade arvud
     * @return tagastab sõnade arvu, mida kasutaja trükkimiskiiruse testis trükkida tahab
     */
    private int leiaSõnadeArv(String sõnuTestisTekst, List<Integer> sobivadArvud) throws ViganeSisendErind {
        int sõnuTestis;

        // Kui kasutaja sisend ei ole number
        if (!onNumbriline(sõnuTestisTekst)) throw new ViganeSisendErind("Palun sisesta number!");
        else {
            sõnuTestis = Integer.parseInt(sõnuTestisTekst);
            // Kui kasutaja sisend ei ole sobiv arv
            if (!sobivadArvud.contains(sõnuTestis)) throw new ViganeSisendErind("Palun sisesta number valikust (5, 10, 25, 50)");
        }

        return sõnuTestis;
    }

    /**
     * Kontrollib kas String on numbriline
     * @param sõnuTestisTekst kasutaja sisend
     * @return tagastab tõeväärtuse true või false (on numbriline või ei ole)
     */
    private boolean onNumbriline(String sõnuTestisTekst) {
        try {
            Integer.parseInt(sõnuTestisTekst);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


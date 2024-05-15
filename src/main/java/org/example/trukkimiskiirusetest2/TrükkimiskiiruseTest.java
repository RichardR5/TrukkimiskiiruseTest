package org.example.trukkimiskiirusetest2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class TrükkimiskiiruseTest extends Peamenüü {

    private int sõnadeArv;
    private int sekundid = 2;
    private long sisestuseAlgus;
    private int vigu = 0;

    public TrükkimiskiiruseTest(int sõnadeArv) {
        this.sõnadeArv = sõnadeArv;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Geneereerib sõnad, mida kasutaja trükkima hakkab
        List<String> sõnad = SõnadeGeneraator.genereeriSõnad(sõnadeArv);
        String esimeneSõna = sõnad.get(0);
        AtomicInteger indeks = new AtomicInteger(1);

        Label kirjutaSõna = new Label("↓ Kirjuta sõna ↓");
        kirjutaSõna.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label hetkeSõna = new Label("3");
        hetkeSõna.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 18));
        TextField sõnadeSisestus = new TextField();
        sõnadeSisestus.setPrefWidth(50);
        sõnadeSisestus.setEditable(false);

        // Testi juhised
        kuvaJuhised(hetkeSõna, esimeneSõna, sõnadeSisestus);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));
        vBox.getChildren().addAll(kirjutaSõna, hetkeSõna, sõnadeSisestus);

        BorderPane root = new BorderPane();
        root.setCenter(vBox);

        Scene scene = new Scene(root, 300, 150);

        // Kui vajutatakse ENTER
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                // Kui sõna on valesti kirjutatud, lisatakse viga
                if (!hetkeSõna.getText().equals(sõnadeSisestus.getText())) vigu++;

                // Kui kõik sõnad on ära trükitud ehk test lõppenud
                if (indeks.get() == sõnad.size()) {
                    long sisestuseLõpp = System.nanoTime();
                    // Arvutab kasutaja trükikiiruse
                    double trükikiirus = arvutaWPM(sisestuseLõpp, vigu);
                    // Kasutaja saab tagasiside
                    primaryStage.close();
                    try {
                        tagasiside(trükikiirus);
                    } catch (IOException viga) {
                        System.out.println(viga.getMessage());
                    }
                } else {
                    // Ekraanile ilmub uus sõna ja sisestuse rida tühjendatakse
                    hetkeSõna.setText(sõnad.get(indeks.getAndIncrement()));
                    sõnadeSisestus.clear();
                }
            }
        });

        BackgroundFill bf = new BackgroundFill(Color.LIGHTSTEELBLUE, null, null);
        Background bg = new Background(bf);
        root.setBackground(bg);

        // Tehtud lehel https://logo.com/
        primaryStage.getIcons().add(new Image("file:logo.png"));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Trükkimiskiiruse test");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Meetod leiab kasutaja testi tulemuse (sõnu minutis)
     * @param sisestuseLõpp sisestuse lõpp, arvutatakse kulunud aega
     * @param vigu mitu viga kasutaja tegi
     * @return tagastab kasutaja trükkimiskiiruse (sõnu minutis)
     */
    private double arvutaWPM(long sisestuseLõpp, int vigu) {
        // Kui kasutaja tegi liiga palju vigu, siis tulemus ei loe
        double sõnadeArvDouble = sõnadeArv;
        if (vigu >= sõnadeArvDouble / 2) {
            return 0;
        }

        double aeg = (sisestuseLõpp - sisestuseAlgus) / 1000000000.0;

        // Iga viga lisab ajale 2 sekundit + 10% koguajast
        double veaPenalti = vigu * (2 + 0.1 * aeg);
        aeg += veaPenalti;

        return Math.round((60/aeg) * sõnadeArv * 10) / 10.0;
    }

    /**
     * Meetodi eesmärk on teha countdown testi alguseni
     * @param hetkeSõna Label, kus toimub countdown ning hiljem ilmuvad sõnad
     * @param esimeneSõna sõna, mis määratakse countdowni lõpus hetkeSõna Label'isse
     * @param sõnadeSisestus tekstisisestuse rida
     */
    private void countdown(Label hetkeSõna, String esimeneSõna, TextField sõnadeSisestus) {
        Timeline countdown = new Timeline();
        countdown.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), event -> {
                    if (sekundid == 0) {
                        hetkeSõna.setText("LÄKS!");
                    } else if (sekundid == -1) {
                        // Ekraanile ilmub esimene sõna ning test algab
                        hetkeSõna.setText(esimeneSõna);
                        sõnadeSisestus.setEditable(true);
                        sisestuseAlgus = System.nanoTime();
                        countdown.stop();
                    } else {
                        hetkeSõna.setText(String.valueOf(sekundid));
                    }
                    sekundid--;
                })
        );

        // Mängib 4 korda, sest  "2" - "1" - "LÄKS!" - esimeneSõna
        countdown.setCycleCount(4);
        countdown.play();
    }

    /**
     * Meetodi kuvab teavituse trükkimiskiiruse testi juhistega
     * @param hetkeSõna vajalik countdown'i alustamiseks
     * @param esimeneSõna vajalik countdown'i alustamiseks
     * @param sõnadeSisestus vajalik countdown'i alustamiseks
     */
    private void kuvaJuhised(Label hetkeSõna, String esimeneSõna, TextField sõnadeSisestus) {
        VBox alertid = new VBox();
        alertid.setSpacing(10);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Trükkimiskiiruse testi juhised");
        alert.setHeaderText(null);
        Label info1 = new Label("Trüki võimalikult kiiresti ekraanile ilmuvad sõnad.");
        Label info2 = new Label("Kui sõna on kirjutatud siis vajuta ENTER, et liikuda järgmise sõna juurde.");
        Label info3 = new Label("NB! Iga vea eest lisatakse aega juurde.\nÄra eksi rohkem kui poolte sõnadega, muidu tulemus ei loe!");
        info1.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16));
        info2.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16));
        info3.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        alertid.getChildren().addAll(info1, info3, info2);
        alert.getDialogPane().setContent(alertid);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:logo.png"));
        alert.showAndWait();

        // Kui on vajutatud "OK", siis hakkab countdown
        countdown(hetkeSõna, esimeneSõna, sõnadeSisestus);
    }

    /**
     * Meetodi eesmärk on andmefailist leida kasutaja parim tulemus
     * @param trükikiirus tulemus, mille kasutaja just sai
     * @return tagastab (sessiooni) parima tulemuse
     */
    private double tööFailiga(double trükikiirus) throws IOException {
        // Lisab just saadud tulemuse faili
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("andmed.bin", true))) {
            dos.writeDouble(trükikiirus);
        }

        // Hakkab otsima parimat tulemust
        double parimKiirus = Double.MIN_VALUE;
        try (DataInputStream dis = new DataInputStream(new FileInputStream("andmed.bin"))) {
            while (dis.available() > 0) {
                double järgmineTulemus = dis.readDouble();
                if (järgmineTulemus > parimKiirus) parimKiirus = järgmineTulemus;
            }
        } catch (IOException e) {
            System.out.println("Faili, kust tulemusi lugeda, ei leitud!");
        }

        return parimKiirus;
    }

    /**
     * Meetod kuvab kasutajale tema trükkimiskiirusetesti tagasiside
     * @param trükikiirus kasutaja saavutatud trükkimiskiirus
     */
    private void tagasiside(double trükikiirus) throws IOException {
        // Tagasiside sõltuvalt tulemusest (ja vigadest)
        String tagasisideTekst = tagasisideTekst(trükikiirus);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tagasiside");
        alert.setHeaderText(null);
        Label info = new Label(tagasisideTekst);
        info.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16));
        alert.getDialogPane().setContent(info);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:logo.png"));

        ButtonType uusTest = new ButtonType("Uus test", ButtonBar.ButtonData.OK_DONE);
        ButtonType lõpeta = new ButtonType("Lõpeta", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(uusTest, lõpeta);

        // Kui vajutatakse "Uus test" siis avaneb peamenüü
        // Kui vajutatakse "Lõpeta" siis programm lõpetab oma töö ja andmete fail kustutakse
        Optional<ButtonType> tulemus = alert.showAndWait();
        if (tulemus.isPresent() && tulemus.get() == uusTest) {
            new Peamenüü().start(new Stage());
        } else {
            new File("andmed.bin").delete();
        }
    }

    /**
     * Meetod leiab teksti, mida kasutajale tagasisideks näidata
     * @param trükikiirus kasutaja trükikiirus
     * @return tagastab sobiva teksti
     */
    private String tagasisideTekst(double trükikiirus) throws IOException {
        if (trükikiirus == 0) return "Tegid liiga palju vigu! Sinu tulemus ei loe.";
        return "Trükkimiskiirus: " + trükikiirus + " sõna minutis\nVigu: " + vigu +
                "\nSinu senine parim tulemus on " + tööFailiga(trükikiirus) + " sõna minutis. ";
    }
}

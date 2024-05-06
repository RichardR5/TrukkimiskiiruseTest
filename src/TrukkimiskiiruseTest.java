import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TrukkimiskiiruseTest {

    // Klassimuutujad testija jaoks
    private int sõnadeArv;
    private List<String> sõnad;

    public TrukkimiskiiruseTest(int sõnadeArv, List<String> sõnad) {
        this.sõnadeArv = sõnadeArv;
        this.sõnad = sõnad;
    }

    /**
     * Meetod trükiKiiruseMõõtja käivitab testi, kasutaja hakkab sisesta sõnu ning saab hiljem tagasisidet enda soorituse kohta
     */
    public double trükiKiiruseMõõtja() {
        double trükiAeg = 0;
        int vigu = 0;

        for (String sõna : sõnad) {
            System.out.println("Trüki: " + sõna);
            Scanner scanner = new Scanner(System.in);

            long sisestuseAlgus = System.nanoTime();

            String sisestatudSõna = scanner.next();

            long sisestuseLõpp = System.nanoTime();

            double aegaKulus = (sisestuseLõpp - sisestuseAlgus) / 1000000000.0; //mind aitas stackoverflow :)
            trükiAeg += aegaKulus;

            if (!sisestatudSõna.equals(sõna)) {
                vigu += 1;
            }
        }

        // Iga vea eest lisab trükiajale juurde ühe sekundi
        trükiAeg = Math.round(trükiAeg * 100) / 100.0;
        double trükiAegVigadega = trükiAeg + 1.0 * vigu;

        // Arvutab trükkimiskiiruse "sõnu minutis", ümardatud ühe komakohani
        double trükkimisKiirus = Math.round(sõnadeArv * (60.0/trükiAegVigadega) * 10) / 10.0;

        // Tagasiside kasutajale
        String viguSõne = " viga";
        if (vigu == 1) {
            viguSõne = " vea";
        }

        System.out.println("--------------------------------------------------------------");
        System.out.println("Sul kulus " + sõnadeArv + " sõna sisestamiseks " + trükiAeg + " sekundit, tegid " + vigu + viguSõne);
        System.out.println("Trükkimiskiirus: " + trükkimisKiirus + " sõna minutis");

        return trükkimisKiirus;
    }


}

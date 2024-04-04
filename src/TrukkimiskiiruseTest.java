import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TrukkimiskiiruseTest {
    /**
     * Küsib kasutajalt, mitme sõna pikkust testi kasutaja soovib.
     * Loeb kasutaja sisendit ning genereerib vastava sõnade arvuga listi.
     * Seejärel hakkab testima kasutaja trükikiirust
     */
    public static void main(String[] args) throws InterruptedException {
        Scanner mituSõnaSoovib = new Scanner(System.in);
        System.out.println("Alustad trükkimiskiiruse testi!");

        boolean lubatudArvSõnu = false;
        List<Integer> lubatudArvud = new ArrayList<>(Arrays.asList(5, 10, 25, 50));
        int sõnadeArv = 0;

        // Tsükkel, mis käib niikaua kuni kasutaja sisestab lubatud arvu sõnu
        while (!lubatudArvSõnu) {
            System.out.println("Mitut sõna soovid trükkida? (5, 10, 25, 50) ");

            // Scanner loeb kasutajasisendist, mitut sõna kasutaja trükkima soovib hakata
            sõnadeArv = mituSõnaSoovib.nextInt();

            if (lubatudArvud.contains(sõnadeArv)) {
                lubatudArvSõnu = true;
            } else {
                System.out.println("Valisid vale arvu sõnu!\n");
            }
        }

        // Sõnad, mida kasutaja peab sisestama hakkama, tulevad Lausegeneraatori klassist meetodi genereeriLause abil.
        // genereeriLause võtab argumendiks Scanneri loetud arvu, ehk mitut sõna kasutaja soovib sisestama hakata.
        List<String> sõnad = SonadeGeneraator.genereeriSõnad(sõnadeArv);

        // Countdown programmi alguseni
        System.out.println("\nOle valmis, test kohe hakkab");
        TimeUnit.SECONDS.sleep(1);
        for (int i = 3; i > 0; i--) {
            System.out.println(i);
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println("LÄKS!\n");
        TimeUnit.MILLISECONDS.sleep(500);

        trükiKiiruseMõõtja(sõnad, sõnadeArv);
    }

    /**
     * Meetod trükiKiiruseMõõtja käivitab testi, kasutaja hakkab sisesta sõnu ning saab hiljem tagasisidet enda soorituse kohta
     *
     * @param sõnadeList, sõnad mida kasutaja hakkab trükkima
     * @param mituSõnaKasutajaSisestadaSoovib
     */
    public static void trükiKiiruseMõõtja(List<String> sõnadeList, int mituSõnaKasutajaSisestadaSoovib) {
        double trükiAeg = 0;
        int vigu = 0;

        for (String sõna : sõnadeList) {
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
        double trükkimisKiirus = Math.round(mituSõnaKasutajaSisestadaSoovib * (60.0/trükiAegVigadega) * 10) / 10.0;

        // Tagasiside kasutajale
        String viguSõne = " viga";
        if (vigu == 1) {
            viguSõne = " vea";
        }

        System.out.println("--------------------------------------------------------------");
        System.out.println("Sul kulus " + mituSõnaKasutajaSisestadaSoovib + " sõna sisestamiseks " + trükiAeg + " sekundit, tegid " + vigu + viguSõne);
        System.out.println("Trükkimiskiirus: " + trükkimisKiirus + " sõna minutis");
    }

}

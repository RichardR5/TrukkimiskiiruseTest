import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Peaklass {
    /**
     * Küsib kasutajalt, mitme sõna pikkust testi kasutaja soovib.
     * Loeb kasutaja sisendit ning genereerib vastava sõnade arvuga listi.
     * Seejärel hakkab testima kasutaja trükikiirust
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Alustad trükkimiskiiruse testi!");

        // Vajalikud muutujad
        List<Integer> lubatudArvud = Arrays.asList(5, 10, 25, 50);
        int sõnadeArv;
        Scanner mituSõnaSoovib = new Scanner(System.in);

        // Tsükkel, mis käib niikaua kuni kasutaja sisestab lubatud arvu sõnu
        while (true) {
            System.out.println("Mitut sõna soovid trükkida? (5, 10, 25, 50) ");

            // Scanner loeb kasutajasisendist, mitut sõna kasutaja trükkima soovib hakata
            if (lubatudArvud.contains(sõnadeArv = mituSõnaSoovib.nextInt())) {
                break;
            } else {
                System.out.println("Valisid vale arvu sõnu!\n");
            }
        }

        // Sõnad, mida kasutaja peab sisestama hakkama, tulevad Lausegeneraatori klassist meetodi genereeriLause abil.
        // genereeriLause võtab argumendiks Scanneri loetud arvu, ehk mitut sõna kasutaja soovib sisestama hakata.
        List<String> sõnad = SonadeGeneraator.genereeriSõnad(sõnadeArv);

        // Countdown programmi alguseni
        countdown(3);

        // Alustab testi
        // 2. RÜHMATÖÖ JUURES SIIN PEAKS HAKKAMA TULEMUSTE FAILI SALVESTAMINE, ET SALVESTADA KASUTAJA PERSONAL BEST
        double trükkimisKiirus = new TrukkimiskiiruseTest(sõnadeArv, sõnad).trükiKiiruseMõõtja();
        System.out.println(trükkimisKiirus);
    }

    /**
     * Meetodi eesmärgiks on printida countdown sekundist 'sekund' kuni üheni (kaasa arvatud)
     * @param sekund mitmendast sekundist countdown algab
     * @throws InterruptedException
     */
    public static void countdown(int sekund) throws InterruptedException {
        System.out.println("\nOle valmis, test kohe hakkab");
        TimeUnit.SECONDS.sleep(1);

        for (int i = sekund; i > 0; i--) {
            System.out.println(i);
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("LÄKS!\n");
        TimeUnit.MILLISECONDS.sleep(500);
    }
}

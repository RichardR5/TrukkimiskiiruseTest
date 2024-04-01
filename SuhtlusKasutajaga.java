import java.util.List;
import java.util.Scanner;

public class SuhtlusKasutajaga {
    /**
     * Küsib kasutajalt, mitme sõna pikkust lauset kasutaja soovib.
     * Loeb kasutaja sisendit ning genereerib vastava sõnade arvuga lause.
     * Seejärel kontrollib kasutaja sisendit, kui kirjaviga pole, leiab trükkimiskiiruse.
     */
    public static void main(String[] args) {
        Scanner mituSõnaSoovib = new Scanner(System.in);
        System.out.println("Alustad trükkimiskiiruse testi!");
        System.out.println("Mitut sõna soovid trükkida? (5, 10, 20, 30) ");
        // Scanner loeb kasutajasisendist, mitut sõna kasutaja trükkima soovib hakata
        int sõnadeArv = mituSõnaSoovib.nextInt();
        // Sõnad, mida kasutaja peab sisestama hakkama, tulevad Lausegeneraatori klassist meetodi genereeriLause abil.
        // genereeriLause võtab argumendiks Scanneri loetud arvu, ehk mitut sõna kasutaja soovib sisestama hakata.
        List<String> sõnad = Lausegeneraator.genereeriLause(sõnadeArv);
        String aegaKulus = trükiKiiruseMõõtja(sõnad, sõnadeArv);
    }

    /**
     * Meetod trükiKiiruseMõõtja käivitab "taimeri", kui kasutaja trükib sõna korrektselt, siis tagastatakse talle peale
     * soovitud arvu sõnade trükkimist, kui palju aega tal kulus.
     *
     * @param sõnadeList
     * @param mituSõnaKasutajaSisestadaSoovib
     * @return
     */
    public static String trükiKiiruseMõõtja(List<String> sõnadeList, int mituSõnaKasutajaSisestadaSoovib) {
        double trükiAeg = 0;
        for (int i = 0; i < mituSõnaKasutajaSisestadaSoovib; i++) {
            for (String sõna : sõnadeList) {
                Scanner scanner = new Scanner(System.in);
                long sisestuseAlgus = System.currentTimeMillis();
                String sisestatudSõna = scanner.next();
                long sisestuseLõpp = System.currentTimeMillis();
                long aegaKulus = (sisestuseLõpp - sisestuseAlgus) / 1000; //mind aitas stackoverflow :)
                if (sisestatudSõna.equals(sõna)) {
                    trükiAeg += aegaKulus;
                }
            }
        } return "Sul kulus " + mituSõnaKasutajaSisestadaSoovib + " sõna sisestamiseks " + trükiAeg;
    }

}

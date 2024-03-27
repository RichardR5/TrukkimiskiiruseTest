import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Lausegeneraator {
    /**
     * Genereerib vastava pikkusega lause valides suvalisi sõnu
     * @param sõnadeArv Määrab mitme sõnaga lause genereeritakse
     * @return Tagastab listi milles on lause sõnad
     */
    public static List<String> genereeriLause(int sõnadeArv) {
        // loob listi 29052 sõnast, kust lause sõnu hakatakse valima
        List<String> sõnad = sõnadFailist();

        // loob listi lauseSõnad, kus hoiatakse lause sõnu
        List<String> lauseSõnad = new ArrayList<>();

        // tsükkel, mis iga iteratsiooniga genereerib suvalise arvu
        // seda suvalist arvu kasutatakse suvalise sõna valimiseks
        // suvaline sõna lisatakse lauseSõnade listi
        for (int i = 0; i < sõnadeArv; i++) {
            Random random = new Random();
            int suvalineNr = random.nextInt(sõnad.size());

            lauseSõnad.add(sõnad.get(suvalineNr));
        }

        return lauseSõnad;
    }

    /**
     * Leiab failist "kõigePopulaarsemadSõnad.txt" sobivad sõnad
     * @return Tagastab listi sõnadest
     */
    public static List<String> sõnadFailist() {
        // loob listi sõnad, kus hoiatakse sõnu
        List<String> sõnad = new ArrayList<>();

        // hakkab failist järjest ridu lugema
        try (Scanner scanner = new Scanner(new File("kõigePopulaarsemadSõnad.txt"))) {
            while (scanner.hasNextLine()) {
                String rida = scanner.nextLine();

                // lõikab rea tükkideks ning leiab vajalikud muutujad "esinemisi" ja "sõna"
                String[] lõigutudRida = rida.split(" ");
                int esinemisi = Integer.parseInt(lõigutudRida[lõigutudRida.length - 2]);
                String sõna = lõigutudRida[lõigutudRida.length - 1];

                // sõna on sobilik kui ta ei sisalda sidekriipsu ning kui sõna "esinemisi" on rohkem kui 10
                // väiksemate esinemistega sõnad on väga haruldased ja/või erilise kirjapildiga ning ei sobi meie programmi
                if (!sõna.contains("-") && esinemisi > 10) {
                    sõnad.add(sõna);
                }
            }
        // kui tekib probleem faili leidmisega
        } catch (FileNotFoundException e) {
            System.out.println("Faili ei leitud!");
        }
        return sõnad;
    }
    // main meetod, mida kasutasime testimiseks
    public static void main(String[] args) {
        int sõnuLauses = 20;
        System.out.println(genereeriLause(sõnuLauses));
    }
}

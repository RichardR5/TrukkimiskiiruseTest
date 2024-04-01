import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SonadeGeneraator {
    /**
     * Genereerib vastava pikkusega testi valides suvalisi sõnu
     * @param sõnadeArv Määrab mitme sõnaga testi genereeritakse
     * @return Tagastab listi milles on testi sõnad
     */
    public static List<String> genereeriSõnad(int sõnadeArv) {
        // loob listi 29052 sõnast, kust testi sõnu hakatakse valima
        List<String> sõnad = sõnadFailist();

        // loob listi testiSõnad, kus hoiatakse testi sõnu
        List<String> testiSõnad = new ArrayList<>();

        // tsükkel, mis iga iteratsiooniga genereerib suvalise arvu
        // seda suvalist arvu kasutatakse suvalise sõna valimiseks
        // suvaline sõna lisatakse testiSõnade listi
        for (int i = 0; i < sõnadeArv; i++) {
            Random random = new Random();
            int suvalineNr = random.nextInt(sõnad.size());

            testiSõnad.add(sõnad.get(suvalineNr));
        }

        return testiSõnad;
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
    /*//main meetod, mida kasutasime testimiseks
    public static void main(String[] args) {
        int sõnuTestis = 3;
        System.out.println(genereeriSõnad(sõnuTestis));
    }*/
}

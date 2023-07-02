
import java.util.UnknownFormatConversionException;
import java.io.BufferedReader; // permet de faire des lectures en memoire tempon (Buffer) afin d'ameliorer les performances eiter les appels systeme
import java.io.FileReader; // Permet de lire les fichiers 
import java.io.IOException; // Gerer les exceptions liees uax fichiers 

public class DictionnaryCracker extends PasswordHacherCracher {

    public   BufferedReader bufferedReader ;

    public DictionnaryCracker() {
        System.out.println("\nUne instance de la classe DictionnaryCracker cree avec succees\n");

        try{

            bufferedReader = new BufferedReader(new FileReader("../dictionnary.txt")); // Chargement du fichier en memoire tampon


        }catch (IOException e){
            System.err.println("Erreur lors de la l'ouverture du fichier : " + e.getMessage());


        }

    }


    // Cette methode va retourner le mot de passe si celui ci se trouve au niveau du dictionnaire de mots
    /*
     * Comment ??
     * On lit chaque ligne du dictionnaire puis on le compare avec le mot de passe
     * - Si c'est pas le bon on prend une autre ligne et compare 
     * Ainsi de suite jusqu'a tomber sur le bon mot de passe ou non
     */

    @Override
    public String getPassword() {
        String password = "";


        // Scanner scanner = new Scanner(System.in);
        System.out.println("Vueillez entrer le mot de passe a casser par le Dictionnaire");
        password = scanner.next();

        // On charge le dictionnaire
        
        try{

           // BufferedReader bufferedReader = new BufferedReader(new FileReader(dictionnaryPath)); // Chargement du fichier en memoire tampon

            String line;

            // La methode readLine() permet de lire une ligne
            // Tant que la ligne lu n'est pas vide

            while ((line = bufferedReader.readLine()) != null) {
               System.out.println(line);
               String truePassword = getTruePassword(password, line);

                if (truePassword != null) {
                    return "Le mot de passe est: " + truePassword;
                }
            
            }
            
          
            bufferedReader.close(); // liberer les ressources appropriees du fichier

            } catch (IOException e){
                System.err.println("Erreur lors de la l'ouverture du fichier : " + e.getMessage());
            }

        return "Desole Diambar mais ce mot ne figure pas dans le dictionnaire";
    }

    public String getTruePassword(String password, String dictionaryWord) {

        if (dictionaryWord.equalsIgnoreCase(password)){
            String goodPassword = dictionaryWord;

            return goodPassword;

        }

        return null;
    }




    public String getMotDePasse(){

        try {
            // Lire la ligne suivante du fichier
            return bufferedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // En cas d'erreur ou de fin de fichier
    }


    public void fermerDictionary() {
        try {
            // Fermer le lecteur de fichier
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

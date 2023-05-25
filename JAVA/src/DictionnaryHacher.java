import java.io.BufferedReader; // permet de faire des lectures en memoire tempon (Buffer) afin d'ameliorer les performances eiter les appels systeme
import java.io.FileReader; // Permet de lire les fichiers 
import java.io.IOException; // Gerer les exceptions liees uax fichiers 
import java.security.MessageDigest; // permet de calculer la somme de controle d'une technique a l'aide de getINstance;
import java.security.NoSuchAlgorithmException; // Gere les exceptions au cas la technique n'est pas gerree par la plateforme
import java.util.Scanner;


class DictionnaryHacher extends PasswordHacherCracher{

    public DictionnaryHacher() {
        System.out.println("Une instance de la classe DictionnaryHacher cree avec succees");
    }

    // Cette methode va retourner le mot de passe correspond au hach
    /*
     * Comment ??
     * On lit chaque ligne du dictionnaire puis on  compare ses haches avec le hach pris en entree
     * - Si c'est pas le bon on prend une autre ligne et on fait la meme chose
     * Ainsi de suite jusqu'a tomber sur le bon hach et on retourne le mot de passe 
     */

    @Override
    public String getPassword(){
        String hach = "";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Vueillez entrer le Hach a trouver par le Dctionnaire");
        //hach = "cd0acfe085eeb0f874391fb9b8009bed";

        do {
            hach = scanner.next();
            if(hach.length() != 32 && hach.length() != 40 && hach.length() != 64 && hach.length() !=96 && hach.length() !=128)
                System.out.println("Aucun techniques de Hachage supporte ne fournit ce nombre de caracteres");
       

        }while (hach.length() != 32 && hach.length() != 40 && hach.length() != 64 && hach.length() !=96 && hach.length() !=128);
        

        String[] hashingTechniques = {
            "MD5",
            "SHA-1",
            "SHA-256",
            "SHA-384",
            "SHA-512"
        };


        String dictionnaryPath = "dictionnary.txt"; //Chemin d'acces au dictionnaire de donnees

        try{

            BufferedReader bufferedReader = new BufferedReader(new FileReader(dictionnaryPath)); // Chargement du fichier en memoire tampon

            String line;

            // La methode readLine() permet de lire une ligne
            // Tant que la ligne lu n'est pas vide

            while ((line = bufferedReader.readLine()) != null) {

                
                for (int i = 0 ; i < hashingTechniques.length ; i++){
                    String wordHach = getHach(line, hashingTechniques[i]);

                    if (wordHach.equals(hach)){
                        return line;

                    }

                } 
            
            }
            
          
            bufferedReader.close(); // liberer les ressources appropriees du fichier

            } catch (IOException e){
                System.err.println("Erreur lors de la l'ouverture du fichier : " + e.getMessage());
            }

        return "Desole Diambar mais ce hash n'a pas de correspondant  dans le dictionnaire";


        
    }


    public String getHach(String wordDictionary, String typeHach) {
        try {

            MessageDigest mDigest = MessageDigest.getInstance(typeHach);

            byte[] hashBytes = mDigest.digest(wordDictionary.getBytes());

            StringBuilder stringBuilder = new StringBuilder();// Permet de convertir la sequence en caracteres hexadecimaux

            for (byte b : hashBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return null;


    }

    public boolean  compareHach(String hach , String combinaisonHach){
        if (hach.equals(combinaisonHach))
            return true;
        
        return false;

    }

}
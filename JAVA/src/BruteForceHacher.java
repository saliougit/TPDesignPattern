import java.security.MessageDigest; // permet de calculer la somme de controle d'une technique a l'aide de getINstance;
import java.security.NoSuchAlgorithmException; // Gere les exceptions au cas la technique n'est pas gerree par la plateforme
import java.util.Scanner;


public class BruteForceHacher  extends PasswordHacherCracher{

    public BruteForceHacher() {

        System.out.println("\nUne instance de la classe BruteForceHacher cree avec succees\n");

    }

   
    public String getPassword() {
        String hach = "";
        int  passwordLength = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Vueillez entrer le Hach a trouver par Brute Force");
        //hach = "cd0acfe085eeb0f874391fb9b8009bed";

        //if (hach.length() != 32 || hach.length() != 40 || hach.length() != 64 || hach.length() !=96 || hach.length() !=128)
        do {
            hach = scanner.next();
            if(hach.length() != 32 && hach.length() != 40 && hach.length() != 64 && hach.length() !=96 && hach.length() !=128)
                System.out.println("Aucun techniques de Hachage supporte ne fournit ce nombre de caracteres");
       

        }while (hach.length() != 32 && hach.length() != 40 && hach.length() != 64 && hach.length() !=96 && hach.length() !=128);
        
        System.out.println("Vueillez entrer la taille du mot de passe");
        passwordLength = controle(scanner);





        char[] combinaison = new char[passwordLength];

        return getTrueCombinaison(passwordLength, combinaison, 0, hach);
    }

    public  String getTrueCombinaison(int passwordLength, char[] combinaison, int position, String passwordHash) {

        String currentCombinaison = "";

        // Tableau qui contient quelques techniques de Hachage supporte par java

        String[] hashingTechniques = {
            "MD5",
            "SHA-1",
            "SHA-256",
            "SHA-384",
            "SHA-512"
        };

        if (passwordLength == position) {
            currentCombinaison = arrayToString(combinaison);
            
            for (int i = 0; i < hashingTechniques.length; i++) {
                
                String currentHash = getHach(hashingTechniques[i], currentCombinaison);
              //  System.out.println(currentHash+ "\n");
                
                if (currentHash.equals(passwordHash)){
                    return "Le mot de passe est :"+currentCombinaison;
                    
                }

            }
            
        
            return null;
        }
        else {
            for (char c = 'a'; c <= 'z'; c++) {
                combinaison[position] = c;
                String result = getTrueCombinaison(passwordLength, combinaison, position + 1, passwordHash);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;

        

    }


    //Fonction qui permet de retourner un Hach avec la technique utilisee;

    public String getHach(String technique, String currentCombinaison){

        try {

            MessageDigest mDigest = MessageDigest.getInstance(technique);

            byte[] hashBytes = mDigest.digest(currentCombinaison.getBytes());

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


    String arrayToString(char [] array){
        return new String(array);
    }


    public  int controle(Scanner scanner ) {
        int number = 0;
        do {
            System.out.print("Entrez un entier entre 1 et 10: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Oupps !! entrer un entier valide entre 1 et 10 : ");
                scanner.next(); // Ignorer l'entrée invalide
            }
            number = scanner.nextInt();
        } while (number < 0|| number >= 10);

        return number;
    }
    
}




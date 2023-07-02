// 
import java.util.Scanner;


public abstract class PasswordHacherCracher {

    Scanner scanner = new Scanner(System.in);
    String dictionnaryPath = "dictionnary.txt"; //Chemin d'acces au dictionnaire de donnees

    public PasswordHacherCracher(){};

    public abstract String getPassword();


        
}

import java.util.Scanner;


public class Main {

    public static void main(String[] args){
       
        Scanner inputScanner = new Scanner(System.in);


        int choice = menu(inputScanner);

        Host client = new Host();

        if (choice == 1){
            System.out.println("\nVous avez choisi d'attaquer par vous meme");
            System.out.print("\nVeuillez saisir votre mot de passe : ");
            String password = inputScanner.next();

            client.sendRequest1(password);
            
        }else {
            System.out.println("\nVous avez choisi l'attaque du Dictionnaire");

            client.sendRequest2();
        }

    }



    public static int menu(Scanner scanner){

        int choice = 0;
        System.out.println("\nWelcome sur KayDevinerr".toUpperCase());
        System.out.println("\n(1) Deviner mot de passe par vous meme\n(2) Deviner par le Dictionnaire\n");
        do {
            System.out.print("\nEntrez un entier entre 1 et 2 : ");
            while (!scanner.hasNextInt()) {
                System.out.print("Oupps !! entrer un entier valide entre 1 et 2 : ");
                scanner.next(); // Ignorer l'entrée invalide
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 2);

        return choice;

    }
    
}

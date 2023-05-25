import java.util.Scanner;


public class Principale {

    public static void main(String[] arguments){

        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        int choice2 = 0;

        

        System.out.println("\nBienvenue sur KH (Kay Hacher)----\n\nQue voulez vous faire ? ");
        System.out.println("1- Casser mot de passe simple\n2- Casser mot de passe Hache\n");

        choice = controle(scanner);

        if (choice == 1){
            System.out.println("\nCassage mot de passe Simple"); 
            System.out.println("\n1- Casser par Brute force\n2- Casser par Dictionnaire");
            choice2 = controle(scanner);
        
        }
        else {
            System.out.println("\nCassage mot de passe Hache "); 
            System.out.println("\n3- Casser par Brute force\n4- Casser par Dictionnaire");
            choice2 = controle2(scanner);

        }

        PasswordHacherCracher passwordHacherCracher = FabriqueCrackerHacherPassword.getInstance(choice2);

        long startTime = System.currentTimeMillis();
        
        String resultat = passwordHacherCracher.getPassword();

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println(resultat);
        System.out.println("Temps d'exécution : " + elapsedTime + " millisecondes.");

    }

    public static int controle(Scanner scanner ) {
        int number = 0;
        do {
            System.out.print("\nEntrez un entier entre 1 et 2 : ");
            while (!scanner.hasNextInt()) {
                System.out.print("Oupps !! entrer un entier valide entre 1 et 2 : ");
                scanner.next(); // Ignorer l'entrée invalide
            }
            number = scanner.nextInt();
        } while (number > 2 || number <= 0);

        return number;
    }
    public static int controle2(Scanner scanner ) {
        int number = 0;
        do {
            System.out.print("\nEntrez un entier entre 3 et 4 : ");
            while (!scanner.hasNextInt()) {
                System.out.print("Oupps !! entrer un entier valide entre 3 et 4 : ");
                scanner.next(); // Ignorer l'entrée invalide
            }
            number = scanner.nextInt();
        } while (number > 4 || number < 3);

        return number;
    }
    
}

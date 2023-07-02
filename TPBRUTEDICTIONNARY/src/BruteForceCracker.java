import java.util.Scanner;


class BruteForceCracker  extends PasswordHacherCracher {


    // Constructeur de la classe BruteForce
    public BruteForceCracker(){
        System.out.println("\nUne instance de la classe BruteForceCracker cree avec succees\n");
    };


    // Cette methode retourne la bonne combinaison
    // Comment ???
    /*
        On definit deux fonctions 
        1- getPassword() : 
         - qui se charge de creer le tableau de taille = length(password)
         - Aussi d'appeller la fonction getTrueCombinaison
        
        2- getTrueCombinaison qui est recursive :
         - Elle verifie d'abord si on est a la derniere position dans ce cas
            elle verifie si on a la bonne combinasion Si c'est le cas elle returne la bonne combinasion
            Sinon elle retourne null et on incremente puis reappele la methode de facon recursive 
            de sorte qu'elle essaye toutes les combinaisons possibles.
            Ainsi de suite jusqu a trouver la bonne combinaison
     *   
     * 
     */


    @Override
    public String getPassword() {

        String password = "";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Vueillez entrer le mot de passe a casser par Brute Force");
        password = scanner.next();

        char[] combinaison = new char[password.length()];

        return getTrueCombinaison(password, combinaison, 0);
    }

    public String getTrueCombinaison(String password, char[] combinaison , int position) {


        // [a--z] : intervalle de char supporte.\

        char debut = 'a';
        char fin = 'z';

        if (position == password.length()) {

            System.out.println(combinaison);

            String currentCombinaison = arrayToString(combinaison);

            if (password.equals(currentCombinaison)) {

                return "Le mot de passe est: " + currentCombinaison;
            }
            return null;
        } else {
            for (char c = debut; c <= fin; c++) {
                combinaison[position] = c;

                String result = getTrueCombinaison(password, combinaison, position + 1);
                if (result != null) {
                    return result;
                }
               
            }
        }
        return null;
    }

    String arrayToString(char [] array){
        return new String(array);
    }

}
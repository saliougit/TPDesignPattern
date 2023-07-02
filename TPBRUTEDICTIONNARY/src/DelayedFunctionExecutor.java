import java.util.Timer;
import java.util.TimerTask;

public class DelayedFunctionExecutor {
    public static void executeWithDelay(Runnable function, long delayInMillis) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                function.run();
                timer.cancel(); // Annuler le timer après l'exécution de la fonction
            }
        };

        timer.schedule(task, delayInMillis);
    }

    public static void main(String[] args) {
        System.out.println("Début du programme");

        Runnable myFunction = () -> System.out.println("La fonction est exécutée après le délai");

        executeWithDelay(myFunction, 5000); // Exécute la fonction après un délai de 5000 millisecondes (5 secondes)

        System.out.println("Fin du programme");
    }
}
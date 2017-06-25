import java.util.Random;
import java.util.Scanner;

public class Producer implements Runnable {
    private Drop drop;
    private Scanner scanner;

    public Producer(Drop drop) {
        this.drop = drop;
        scanner = new Scanner(System.in);
    }

    public void run() {
        String importantInfo[] = {
            "Mares eat oats",
            "Does eat oats",
            "Little lambs eat ivy",
            "A kid will eat ivy too"
        };
        String s;
        while((s = scanner.nextLine()).equals("Stop") == false) {
            drop.put(s);
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {}
        }
       drop.put("stop");
    }
}
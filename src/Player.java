import java.util.Arrays;
import java.util.Scanner;

public class Player {
    public static void runPlayer(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Turing Machine");
        while (true) {
            System.out.println("What would you like to play? (random for random, numbers for cards)");
            String line = sc.nextLine();
            if (line.equals("exit")){
                return;
            }
            Game g;
            if (line.equals("random")){
                g = GameGenerator.generateGame();
            }
            else if (line.startsWith("random extreme")){
                Scanner sc3 = new Scanner(line);
                sc3.next();
                sc3.next();
                g = GameGenerator.generateGameExtreme(sc3.nextInt());
            }
            else{
                if (new Scanner(line).hasNextInt()) {
                    g = GameGenerator.generateGame(line);
                }
                else{
                    Scanner sc3 = new Scanner(line);
                    sc3.next();
                    int seed = sc3.nextInt();
                    g = GameGenerator.generateGame(sc3.nextLine(),seed);
                }
            }
            System.out.println("Type \"check # # # card #\" to check a card, or \"guess # # #\" to make a final guess.");
            while(true){
                line = sc.nextLine();
                if (line.startsWith("guess")){
                    Scanner sc2 = new Scanner(line);
                    sc2.next();
                    int[] guess = new int[3];
                    for (int i = 0; i < 3; i++) {
                        guess[i] =sc2.nextInt();
                    }
                    boolean b = g.checkSolution(guess);
                    if (b){
                        System.out.println("Correct! You completed the game in " + g.turnNum + " turns and " + g.totalChecksMade + " checks made.");
                    }
                    else{
                        System.out.println("Incorrect, the answer was " + Arrays.toString(g.getSolution()));
                    }
                    break;
                }
                else{
                    Scanner sc2 = new Scanner(line);
                    sc2.next();
                    int[] guess = new int[3];
                    for (int i = 0; i < 3; i++) {
                        guess[i] =sc2.nextInt();
                    }
                    sc2.next();
                    int cardNum = sc2.nextInt()-1;
                    System.out.println("Your query resulted in " + (g.checkCard(cardNum,guess) ? "Check" : "X"));
                }
            }
            System.out.println("Would you like the bot to try the same game?");
            if (sc.nextLine().equalsIgnoreCase("yes")){
                ComplexSolver.solveGame(g.cloneGame());
            }
            System.out.println("Would you like to play again?");
            if (!sc.nextLine().equalsIgnoreCase("yes")){
                return;
            }
        }
    }
}

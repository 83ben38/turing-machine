import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ComplexSolver {
    public static void solveGame(Game g){
        ArrayList<Condition>[] possibleConditions = new ArrayList[6];
        int totalPossibilities = 1;
        for (int i = 0; i < 6; i++) {
            possibleConditions[i] = new ArrayList<>(g.cards.get(i).conditions);

        }
        for (int i = 0; i < 6; i++) {
            totalPossibilities *= possibleConditions[i].size();
            if (totalPossibilities > 100000){
                break;
            }
        }
        while (totalPossibilities > 100000){
            int[] bestIndices = new int[3];
            for (int i = 0; i < 3; i++) {
                bestIndices[i] = -1;
            }
            int[] bestLengths = new int[3];
            for (int i = 0; i < 6; i++) {
                int k = i;
                for (int j = 0; j < 3; j++) {
                    if (k == -1){
                        break;
                    }
                    if (possibleConditions[k].size() >= bestLengths[j]){
                        bestLengths[j] = possibleConditions[k].size();
                        int z=  k;
                        k = bestIndices[j];
                        bestIndices[j] = z;
                    }
                }
            }
            int bestSums = Integer.MAX_VALUE;
            int[] bestGuess = new int[0];
            for (int i = 0; i < 125; i++) {
                int[] guess = new int[]{i%5 + 1, (i/5)%5 + 1, (i/25) + 1};
                int[][] current = new int[2][3];
                for (int m = 0; m < bestIndices.length; m++) {
                    ArrayList<Condition> c = possibleConditions[bestIndices[m]];
                    for (int j = 0; j < c.size(); j++) {
                        if (c.get(j).check(guess)){
                            current[0][m]++;
                        }
                        else{
                            current[1][m]++;
                        }
                    }
                }
                int sumSquares = 0;
                for (int m = 0; m < current.length; m++) {
                    for (int j = 0; j < current[m].length; j++) {
                        sumSquares += current[m][j] * current[m][j];
                    }
                }
                if (sumSquares < bestSums) {
                    bestSums = sumSquares;
                    bestGuess = guess;
                }
            }
            for (int i = 0; i < 3; i++) {
                boolean check = g.checkCard(bestIndices[i],bestGuess);
                System.out.println("Checking card " + (bestIndices[i]+1) + " Using guess " + Arrays.toString(bestGuess) + " Result: " + (check ? "Check" : "X"));
                for (int j = 0; j < possibleConditions[bestIndices[i]].size(); j++) {
                    if (possibleConditions[bestIndices[i]].get(j).check(bestGuess)!=check){
                        possibleConditions[bestIndices[i]].remove(j);
                        j--;
                    }
                }
            }
            totalPossibilities = 1;
            for (int i = 0; i < 6; i++) {
                totalPossibilities *= possibleConditions[i].size();
                if (totalPossibilities > 100000){
                    break;
                }
            }
        }
        SimpleSolver.solveGame(g,possibleConditions(possibleConditions,0));
    }
    public static ArrayList<Condition[]> possibleConditions(ArrayList<Condition>[] cards, int on){
        if (on == 5){
            ArrayList<Condition[]> ac = new ArrayList<>();
            for (int i = 0; i < cards[5].size(); i++) {
                ac.add(new Condition[]{cards[5].get(i)});
            }
            return ac;
        }
        ArrayList<Condition[]> pastPossibleConditions = possibleConditions(cards,on+1);
        ArrayList<Condition[]> ac = new ArrayList<>();
        int size = 6-on;
        for (int i = 0; i < pastPossibleConditions.size(); i++) {
            for (int j = 0; j < cards[on].size(); j++) {
                Condition[] t = new Condition[size];
                for (int k = 1; k < size; k++) {
                    t[k] = pastPossibleConditions.get(i)[k-1];
                }
                t[0] = cards[on].get(j);
                ac.add(t);
            }
        }
        return ac;
    }
}

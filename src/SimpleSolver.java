import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class SimpleSolver {
    public static void solveGame(Game g){
        int possibleCombos = 1;
        for (int i = 0; i < g.cards.size(); i++) {
            possibleCombos *= g.cards.get(i).conditions.size();
        }
        ArrayList<Condition[]> conditions = possibleConditions(g.cards,0);
        HashMap<Condition[],int[]> solutions = new HashMap<>();
        for (int i = 0; i < conditions.size(); i++) {
            int[] solution = solution(conditions.get(i));
            if (solution.length == 0){
                conditions.remove(i);
                i--;
            }
            else{
                solutions.put(conditions.get(i),solution);
            }
        }
        System.out.println("Possible conditions: " + conditions.size());
        while (true) {
            ArrayList<Condition[]>[] best = new ArrayList[8];
            int bestSums = Integer.MAX_VALUE;
            int[] bestGuess = new int[0];
            int[] bestCardNums = new int[0];
            for (int i = 0; i < 125; i++) {
                for (int j = 0; j < g.cards.size(); j++) {
                    int[] guess = new int[]{i%5 + 1, (i/5)%5 + 1, (i/25) + 1};
                    ArrayList<Condition[]>[] current = new ArrayList[2];
                    for (int m = 0; m < 2; m++) {
                        current[m] = new ArrayList<>();
                    }
                    int[] toCheck = new int[]{j};
                    for (int m = 0; m < conditions.size(); m++) {
                        Condition[] c = conditions.get(m);
                        int slotToGoIn = 0;
                        for (int n = 0; n < toCheck.length; n++) {
                            if (c[toCheck[n]].check(guess)){
                                slotToGoIn++;
                            }
                        }
                        current[slotToGoIn].add(c);
                    }
                    int sumSquares = 0;
                    for (int m = 0; m < current.length; m++) {
                        HashSet<int[]> done = new HashSet<>();
                        for (int k = 0; k < current[m].size(); k++) {
                            done.add(solutions.get(current[m].get(k)));
                        }
                        sumSquares += done.size() * done.size();
                    }
                    if (sumSquares < bestSums) {
                        best = current;
                        bestSums = sumSquares;
                        bestGuess = guess;
                        bestCardNums = toCheck;
                    }
                }
            }
            for (int i = 0; i < 125; i++) {
                for (int j = 0; j < g.cards.size() - 1; j++) {
                    for (int k = 0; k < g.cards.size(); k++) {
                        int[] guess = new int[]{i%5 + 1, (i/5)%5 + 1, (i/25) + 1};
                        ArrayList<Condition[]>[] current = new ArrayList[4];
                        for (int m = 0; m < 4; m++) {
                            current[m] = new ArrayList<>();
                        }
                        int[] toCheck = new int[]{j,k};
                        for (int m = 0; m < conditions.size(); m++) {
                            Condition[] c = conditions.get(m);
                            int slotToGoIn = 0;
                            for (int n = 0; n < toCheck.length; n++) {
                                slotToGoIn *= 2;
                                if (c[toCheck[n]].check(guess)){
                                    slotToGoIn++;
                                }
                            }
                            current[slotToGoIn].add(c);
                        }
                        int sumSquares = 0;
                        for (int m = 0; m < current.length; m++) {
                            HashSet<int[]> done = new HashSet<>();
                            for (int z = 0; z < current[m].size(); z++) {
                                done.add(solutions.get(current[m].get(z)));
                            }
                            sumSquares += done.size() * done.size();
                        }
                        if (sumSquares < bestSums) {
                            best = current;
                            bestSums = sumSquares;
                            bestGuess = guess;
                            bestCardNums = toCheck;
                        }
                    }
                }
            }
            for (int i = 0; i < 125; i++) {
                for (int j = 0; j < g.cards.size() - 2; j++) {
                    for (int k = j + 1; k < g.cards.size() - 1; k++) {
                        for (int l = k + 1; l < g.cards.size(); l++) {
                            int[] guess = new int[]{i % 5 + 1, (i / 5) % 5 + 1, (i / 25) + 1};
                            ArrayList<Condition[]>[] current = new ArrayList[8];
                            for (int m = 0; m < 8; m++) {
                                current[m] = new ArrayList<>();
                            }
                            int[] toCheck = new int[]{j, k, l};
                            for (int m = 0; m < conditions.size(); m++) {
                                Condition[] c = conditions.get(m);
                                int slotToGoIn = 0;
                                for (int n = 0; n < toCheck.length; n++) {
                                    slotToGoIn *= 2;
                                    if (c[toCheck[n]].check(guess)) {
                                        slotToGoIn++;
                                    }
                                }
                                current[slotToGoIn].add(c);
                            }
                            int sumSquares = 0;
                            for (int m = 0; m < current.length; m++) {
                                HashSet<int[]> done = new HashSet<>();
                                for (int z = 0; z < current[m].size(); z++) {
                                    done.add(solutions.get(current[m].get(z)));
                                }
                                sumSquares += done.size() * done.size();
                            }
                            if (sumSquares < bestSums) {
                                best = current;
                                bestSums = sumSquares;
                                bestGuess = guess;
                                bestCardNums = toCheck;
                            }
                        }
                    }
                }
            }
            int start = 0;
            int end = best.length;
            for (int i = 0; i < 3; i++) {
                if (i < bestCardNums.length) {
                    boolean check = g.checkCard(bestCardNums[i], bestGuess);
                    System.out.println("Checking card " + (bestCardNums[i] + 1) + " Using guess " + Arrays.toString(bestGuess) + " Result: " + (check ? "Check" : "X"));

                    int mid = ((end - start) / 2) + start;
                    if (check) {
                        start = mid;
                    } else {
                        end = mid;
                    }
                    boolean solutionFound = true;
                    int[] solution = new int[0];
                    sus : for (int j = start; j < end; j++) {
                        if (solution.length == 0){
                            solution = solutions.get(best[j].get(0));
                        }
                        for (int k = 0; k < best[j].size(); k++) {
                            if (!Arrays.equals(solutions.get(best[j].get(k)), solution)){
                                solutionFound = false;
                                break sus;
                            }
                        }
                    }
                    if (solutionFound) {
                        if (g.checkSolution(solution)) {
                            System.out.println("I found the right solution: " + Arrays.toString(solution));
                        } else {
                            System.out.println("I guessed " + Arrays.toString(solution) + " but it was wrong.");
                        }
                        return;
                    }
                }
            }
            conditions = best[start];
        }
    }
    public static ArrayList<Condition[]> possibleConditions(ArrayList<Card> cards, int on){
        if (on == cards.size()-1){
            ArrayList<Condition[]> ac = new ArrayList<>();
            for (int i = 0; i < cards.getLast().conditions.size(); i++) {
                ac.add(new Condition[]{cards.getLast().conditions.get(i)});
            }
            return ac;
        }
        ArrayList<Condition[]> pastPossibleConditions = possibleConditions(cards,on+1);
        ArrayList<Condition[]> ac = new ArrayList<>();
        int size = cards.size()-on;
        for (int i = 0; i < pastPossibleConditions.size(); i++) {
            for (int j = 0; j < cards.get(on).conditions.size(); j++) {
                Condition[] t = new Condition[size];
                for (int k = 1; k < size; k++) {
                    t[k] = pastPossibleConditions.get(i)[k-1];
                }
                t[0] = cards.get(on).conditions.get(j);
                ac.add(t);
            }
        }
        return ac;
    }
    public static int[] solution(Condition[] conditions){
        int possibleLeft = 125;
        boolean[] possible = new boolean[125];
        for (int i = 0; i < conditions.length; i++) {
            for (int j = 0; j < possible.length; j++) {
                if (!possible[j]){
                    if (!conditions[i].check(new int[]{
                            j%5 + 1, (j/5)%5 + 1, (j/25) + 1
                    })){
                        possible[j] = true;
                        possibleLeft--;
                    }
                }
            }
        }
        if (possibleLeft != 1){
            return new int[0];
        }
        for (int i = 0; i < 125; i++) {
            if (!possible[i]){
                return new int[]{
                        i%5 + 1, (i/5)%5 + 1, (i/25) + 1
                };
            }
        }
        return new int[0];
    }

    public static void solveGame(Game g, ArrayList<Condition[]> conditions){
        HashMap<Condition[],int[]> solutions = new HashMap<>();
        for (int i = 0; i < conditions.size(); i++) {
            int[] solution = solution(conditions.get(i));
            if (solution.length == 0){
                conditions.remove(i);
                i--;
            }
            else{
                solutions.put(conditions.get(i),solution);
            }
        }
        System.out.println("Possible conditions: " + conditions.size());
        while (true) {
            ArrayList<Condition[]>[] best = new ArrayList[8];
            int bestSums = Integer.MAX_VALUE;
            int[] bestGuess = new int[0];
            int[] bestCardNums = new int[0];
            for (int i = 0; i < 125; i++) {
                for (int j = 0; j < g.cards.size(); j++) {
                    int[] guess = new int[]{i%5 + 1, (i/5)%5 + 1, (i/25) + 1};
                    ArrayList<Condition[]>[] current = new ArrayList[2];
                    for (int m = 0; m < 2; m++) {
                        current[m] = new ArrayList<>();
                    }
                    int[] toCheck = new int[]{j};
                    for (int m = 0; m < conditions.size(); m++) {
                        Condition[] c = conditions.get(m);
                        int slotToGoIn = 0;
                        for (int n = 0; n < toCheck.length; n++) {
                            if (c[toCheck[n]].check(guess)){
                                slotToGoIn++;
                            }
                        }
                        current[slotToGoIn].add(c);
                    }
                    int sumSquares = 0;
                    for (int m = 0; m < current.length; m++) {
                        HashSet<int[]> done = new HashSet<>();
                        for (int k = 0; k < current[m].size(); k++) {
                            done.add(solutions.get(current[m].get(k)));
                        }
                        sumSquares += done.size() * done.size();
                    }
                    if (sumSquares < bestSums) {
                        best = current;
                        bestSums = sumSquares;
                        bestGuess = guess;
                        bestCardNums = toCheck;
                    }
                }
            }
            for (int i = 0; i < 125; i++) {
                for (int j = 0; j < g.cards.size() - 1; j++) {
                    for (int k = 0; k < g.cards.size(); k++) {
                        int[] guess = new int[]{i%5 + 1, (i/5)%5 + 1, (i/25) + 1};
                        ArrayList<Condition[]>[] current = new ArrayList[4];
                        for (int m = 0; m < 4; m++) {
                            current[m] = new ArrayList<>();
                        }
                        int[] toCheck = new int[]{j,k};
                        for (int m = 0; m < conditions.size(); m++) {
                            Condition[] c = conditions.get(m);
                            int slotToGoIn = 0;
                            for (int n = 0; n < toCheck.length; n++) {
                                slotToGoIn *= 2;
                                if (c[toCheck[n]].check(guess)){
                                    slotToGoIn++;
                                }
                            }
                            current[slotToGoIn].add(c);
                        }
                        int sumSquares = 0;
                        for (int m = 0; m < current.length; m++) {
                            HashSet<int[]> done = new HashSet<>();
                            for (int z = 0; z < current[m].size(); z++) {
                                done.add(solutions.get(current[m].get(z)));
                            }
                            sumSquares += done.size() * done.size();
                        }
                        if (sumSquares < bestSums) {
                            best = current;
                            bestSums = sumSquares;
                            bestGuess = guess;
                            bestCardNums = toCheck;
                        }
                    }
                }
            }
            for (int i = 0; i < 125; i++) {
                for (int j = 0; j < g.cards.size() - 2; j++) {
                    for (int k = j + 1; k < g.cards.size() - 1; k++) {
                        for (int l = k + 1; l < g.cards.size(); l++) {
                            int[] guess = new int[]{i % 5 + 1, (i / 5) % 5 + 1, (i / 25) + 1};
                            ArrayList<Condition[]>[] current = new ArrayList[8];
                            for (int m = 0; m < 8; m++) {
                                current[m] = new ArrayList<>();
                            }
                            int[] toCheck = new int[]{j, k, l};
                            for (int m = 0; m < conditions.size(); m++) {
                                Condition[] c = conditions.get(m);
                                int slotToGoIn = 0;
                                for (int n = 0; n < toCheck.length; n++) {
                                    slotToGoIn *= 2;
                                    if (c[toCheck[n]].check(guess)) {
                                        slotToGoIn++;
                                    }
                                }
                                current[slotToGoIn].add(c);
                            }
                            int sumSquares = 0;
                            for (int m = 0; m < current.length; m++) {
                                HashSet<int[]> done = new HashSet<>();
                                for (int z = 0; z < current[m].size(); z++) {
                                    done.add(solutions.get(current[m].get(z)));
                                }
                                sumSquares += done.size() * done.size();
                            }
                            if (sumSquares < bestSums) {
                                best = current;
                                bestSums = sumSquares;
                                bestGuess = guess;
                                bestCardNums = toCheck;
                            }
                        }
                    }
                }
            }
            int start = 0;
            int end = best.length;
            for (int i = 0; i < 3; i++) {
                if (i < bestCardNums.length) {
                    boolean check = g.checkCard(bestCardNums[i], bestGuess);
                    System.out.println("Checking card " + (bestCardNums[i] + 1) + " Using guess " + Arrays.toString(bestGuess) + " Result: " + (check ? "Check" : "X"));

                    int mid = ((end - start) / 2) + start;
                    if (check) {
                        start = mid;
                    } else {
                        end = mid;
                    }

                    boolean solutionFound = true;
                    int[] solution = new int[0];
                    sus : for (int j = start; j < end; j++) {
                        if (solution.length == 0 && best.length > 0){
                            solution = solutions.get(best[j].get(0));
                        }
                        for (int k = 0; k < best[j].size(); k++) {
                            if (!Arrays.equals(solutions.get(best[j].get(k)), solution)){
                                solutionFound = false;
                                break sus;
                            }
                        }
                    }
                    if (solutionFound) {
                        if (g.checkSolution(solution)) {
                            System.out.println("I found the right solution: " + Arrays.toString(solution));
                        } else {
                            System.out.println("I guessed " + Arrays.toString(solution) + " but it was wrong.");
                        }
                        return;
                    }
                }
                conditions = best[start];
            }
        }
    }
}

import java.util.ArrayList;

public class SimpleSolver {
    public static void solveGame(Game g){
        int possibleCombos = 1;
        for (int i = 0; i < g.cards.size(); i++) {
            possibleCombos *= g.cards.get(i).conditions.size();
        }
        if (possibleCombos > 100000){
            System.out.println("Too many possibilities");
            return;
        }
        ArrayList<Condition[]> conditions = possibleConditions(g.cards,0);
        ArrayList<int[]> solutions = new ArrayList<>();
        for (int i = 0; i < conditions.size(); i++) {
            int[] solution = solution(conditions.get(i));
            if (solution.length == 0){
                conditions.remove(i);
                i--;
            }
            else{
                solutions.add(solution);
            }
        }
        System.out.println("Possible conditions: " + conditions.size());

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
                for (int k = 0; k < size-1; k++) {
                    t[k] = pastPossibleConditions.get(i)[k];
                }
                t[size-1] = cards.get(on).conditions.get(j);
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
}

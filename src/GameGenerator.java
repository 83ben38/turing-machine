import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameGenerator {
    public static Game generateGame(){
        ArrayList<Integer> cards = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            int randomNum = (int) (Math.random() * GameManager.cards.size());
            while (cards.contains(randomNum)){
                randomNum = (int) (Math.random() * GameManager.cards.size());
            }
            cards.add(randomNum);
        }
        ArrayList<Card> newCards = new ArrayList<>();
        System.out.print("Using cards ");
        for (int i = 0; i < cards.size(); i++) {
            newCards.add(GameManager.cards.get(cards.get(i)));
            System.out.print((cards.get(i)+1) + " ");
        }
        System.out.println();
        Game g = generateGame(newCards);
        if (g == null){
            return generateGame();
        }
        return g;
    }
    public static Game generateGameExtreme(int numCardsPerCard){
        ArrayList<Integer> cards = new ArrayList<>(6*numCardsPerCard);
        for (int i = 0; i < 6*numCardsPerCard; i++) {
            int randomNum = (int) (Math.random() * GameManager.cards.size());
            while (cards.contains(randomNum)){
                randomNum = (int) (Math.random() * GameManager.cards.size());
            }
            cards.add(randomNum);
        }
        ArrayList<Card> newCards = new ArrayList<>();
        System.out.print("Using cards ");
        for (int i = 0; i < cards.size()/numCardsPerCard; i++) {
            Card c = GameManager.cards.get(cards.get(i*numCardsPerCard));
            System.out.print((cards.get(i*numCardsPerCard)+1) + " ");
            for (int j = 0; j < numCardsPerCard-1; j++) {
                c = c.mergeCard(GameManager.cards.get(cards.get(i*numCardsPerCard + j+1)));
                System.out.print((cards.get(i*numCardsPerCard +j+1)+1) + " ");
            }
            newCards.add(c);
        }
        System.out.println();
        Game g = generateGame(newCards);
        if (g == null){
            return generateGame();
        }
        return g;
    }
    public static Game generateGame(int[] cards){
        ArrayList<Card> newCards = new ArrayList<>();
        for (int i = 0; i < cards.length; i++) {
            newCards.add(GameManager.cards.get(cards[i]));
            System.out.print((cards[i]+1) + " ");
        }
        return generateGame(newCards);
    }
    public static Game generateGame(int[] cards, int seed){
        ArrayList<Card> newCards = new ArrayList<>();
        for (int i = 0; i < cards.length; i++) {
            newCards.add(GameManager.cards.get(cards[i]));
            System.out.print((cards[i]+1) + " ");
        }
        return generateGame(newCards, seed);
    }
    public static Game generateGame(String cards){
        Scanner sc = new Scanner(cards);
        int[] cards2 = new int[6];
        for (int i = 0; i < 6; i++) {
            cards2[i] = sc.nextInt()-1;
        }
        return generateGame(cards2);
    }
    public static Game generateGame(String cards, int seed){
        Scanner sc = new Scanner(cards);
        int[] cards2 = new int[6];
        for (int i = 0; i < 6; i++) {
            cards2[i] = sc.nextInt()-1;
        }
        return generateGame(cards2, seed);
    }
    public static Game generateGame(ArrayList<Card> cards){
        int possibleConditions = 1;
        for (int i = 0; i < cards.size(); i++) {
            possibleConditions *= cards.get(i).conditions.size();
            if (possibleConditions > 100000){
                System.out.println("Too many possibilities, cannot do a seeded run. Generating a random run.");
                return generateGameUnseeded(cards);
            }
        }

        ArrayList<Condition[]> conditions = possibleConditions(cards,0);
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
        if (conditions.isEmpty()){
            return null;
        }
        int seed = (int) (conditions.size()*Math.random());
        System.out.println("Using seed " + seed);
        Condition[] c = conditions.get(seed);
        return new Game(cards,new ArrayList<>(List.of(c)),solution(c));
    }
    public static Game generateGame(ArrayList<Card> cards, int seed){
        int possibleConditions = 1;
        for (int i = 0; i < cards.size(); i++) {
            possibleConditions *= cards.get(i).conditions.size();
            if (possibleConditions > 100000){
                System.out.println("Too many possibilities, cannot do a seeded run. Generating a random run.");
                return generateGameUnseeded(cards);
            }
        }
        ArrayList<Condition[]> conditions = possibleConditions(cards,0);
        for (int i = 0; i < conditions.size(); i++) {
            int[] solution = solution(conditions.get(i));
            if (solution.length == 0){
                conditions.remove(i);
                i--;
            }
        }
        if (conditions.isEmpty()){
            return null;
        }
        System.out.println("Using seed " + seed);
        Condition[] c = conditions.get(seed);
        return new Game(cards,new ArrayList<>(List.of(c)),solution(c));
    }
    public static ArrayList<Condition[]> possibleConditions(ArrayList<Card> cards, int on){
        if (on >= cards.size()-1){
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
    public static Game generateGameUnseeded(ArrayList<Card> cards){
        while (true) {
            ArrayList<Condition> conditions = new ArrayList<>();
            for (Card card : cards) {
                conditions.add(card.conditions.get((int) (Math.random() * card.conditions.size())));
            }
            int[] solution = solution(conditions.toArray(new Condition[0]));
            if (solution.length == 3){
                return new Game(cards,conditions,solution);
            }
        }
    }
}


import java.util.ArrayList;

public class Game {
    ArrayList<Card> cards;
    int turnNum;
    int checksMade;
    int totalChecksMade;
    int[] lastGuess;
    private ArrayList<Condition> conditions;
    private int[] solution;
    private boolean isUsable = true;
    public Game(ArrayList<Card> cards,ArrayList<Condition> conditions, int[] solution){
        this.cards = cards;
        this.conditions = conditions;
        this.solution = solution;
        turnNum = 0;
        checksMade = 3;
        totalChecksMade = 0;
    }
    public Game cloneGame(){
        return new Game(cards,conditions,solution);
    }
    public int[] getSolution(){
        isUsable = false;
        return solution;
    }
    public boolean checkCard(int cardNum, int[] nums){
        if (!isUsable){
            throw new GameException("You Already Lost");
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]  < 1 || nums[i] > 5){
                throw new GameException("Invalid Guess");
            }
        }
        totalChecksMade++;
        if (checksMade == 3){
            lastGuess = nums;
            turnNum++;
            checksMade = 1;
        }
        else{
            boolean valid = true;
            for (int i = 0; i < 3; i++) {
                if (nums[i]!=lastGuess[i]){
                    valid = false;
                }
            }
            if (valid){
                checksMade++;
            }
            else{
                lastGuess = nums;
                turnNum++;
                checksMade = 1;
            }
        }
        return conditions.get(cardNum).check(nums);
    }
    public boolean checkSolution(int[] nums){
        if (!isUsable){
            throw new GameException("You Already Lost");
        }
        isUsable = false;
        for (int i = 0; i < 3; i++) {
            if (nums[i] != solution[i]){
                return false;
            }
        }
        return true;
    }
}

import java.util.ArrayList;
import java.util.List;

public class Card {
    public ArrayList<Condition> conditions = new ArrayList<>();
    public Card mergeCard(Card c){
        Card d = new Card();
        d.conditions.addAll(conditions);
        d.conditions.addAll(c.conditions);
        return d;
    }
}

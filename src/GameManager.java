import java.util.*;

public class GameManager {
    static HashMap<String,Condition> conditions = new HashMap<>();
    static{
        String[] colors = new String[]{"blue","yellow","purple"};
        String[] comparisons = new String[]{"equals","greater","less"};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 1; k <= 5; k++) {
                    int finalI = i;
                    int finalK = k;
                    int finalJ = j;
                    conditions.put(colors[i]+comparisons[j]+k, nums -> switch (finalJ){
                        case 0 -> nums[finalI]== finalK;
                        case 1 -> nums[finalI]>finalK;
                        default -> nums[finalI]<finalK;
                    });
                }
            }
        }
        String[] oddities = new String[]{"even","odd"};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                int finalI = i;
                int finalJ = j;
                conditions.put(colors[i]+oddities[j],nums -> nums[finalI]%2==finalJ);
            }
        }
        String[] numbers = new String[]{"zero","one","two","three"};
        for (int i = 1; i <= 5; i++) {
            for (int j = 0; j < 4; j++) {
                int finalI = i;
                int finalJ = j;
                conditions.put(numbers[j]+i, nums -> count(finalI,nums)==finalJ);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if (i != k) {
                        int finalI = i;
                        int finalK = k;
                        int finalJ = j;
                        conditions.put(colors[i] + comparisons[j] + colors[k], nums -> switch (finalJ) {
                            case 0 -> nums[finalI] == nums[finalK];
                            case 1 -> nums[finalI] > nums[finalK];
                            default -> nums[finalI] < nums[finalK];
                        });
                    }
                }
            }
        }
        String[] maxComparisons = new String[]{"smallest","biggest","strictbiggest","strictsmallest","middle","strictmiddle"};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                int finalI = i;
                int finalJ = j;
                 conditions.put(colors[i]+maxComparisons[j],nums -> switch (finalJ){
                     case 0 -> nums[finalI] <= nums[0] && nums[finalI] <= nums[1] && nums[finalI] <= nums[2];
                     case 1 -> nums[finalI] >= nums[0] && nums[finalI] >= nums[1] && nums[finalI] >= nums[2];
                     case 2 -> nums[finalI] > nums[(finalI+1)%3] && nums[finalI] >nums[(finalI+2)%3];
                     case 3 -> nums[finalI] < nums[(finalI+1)%3] && nums[finalI] < nums[(finalI+2)%3];
                     case 4 -> (nums[finalI] <= nums[(finalI+1)%3] && nums[finalI] >= nums[(finalI+2)%3])||(nums[finalI] >= nums[(finalI+1)%3] && nums[finalI] <= nums[(finalI+2)%3]);
                     default ->(nums[finalI] < nums[(finalI+1)%3] && nums[finalI] > nums[(finalI+2)%3])||(nums[finalI] > nums[(finalI+1)%3] && nums[finalI] < nums[(finalI+2)%3]);
                 });
            }
        }
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            conditions.put("more"+oddities[i], nums -> countEven(nums) > 1 == (finalI == 0));
            conditions.put("sum"+oddities[i],nums -> (nums[0]+nums[1]+nums[2])%2==finalI);
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                int finalI = i;
                int finalJ = j;
                conditions.put(numbers[j]+oddities[i], nums -> switch (finalI){
                    case 0 -> countEven(nums);
                    default -> 3-countEven(nums);
                } == finalJ);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if (i != k) {
                        for (int l = 4; l <= 8; l++) {
                            int finalI = i;
                            int finalK = k;
                            int finalJ = j;
                            int finalL = l;
                            conditions.put(colors[i]+"plus"+colors[k]+comparisons[j]+l, nums -> switch (finalJ) {
                                case 0 -> nums[finalI]+nums[finalK]==finalL;
                                case 1 -> nums[finalI]+nums[finalK]>finalL;
                                default -> nums[finalI]+nums[finalK]<finalL;
                            });
                        }
                    }
                }
            }
        }
        for (int i = 3; i <= 5; i++) {
            int finalI = i;
            conditions.put("summultiple"+i,nums -> (nums[0]+nums[1]+nums[2])%finalI==0);
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 4; j <= 8; j++) {
                int finalI = i;
                int finalJ = j;
                conditions.put("sum"+comparisons[i]+j, nums -> switch (finalI) {
                    case 0 -> (nums[0]+nums[1]+nums[2]) == finalJ;
                    case 1 -> (nums[0]+nums[1]+nums[2]) > finalJ;
                    default -> (nums[0]+nums[1]+nums[2]) < finalJ;
                });
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if (i != k) {
                        for (int l = 0; l < 3; l++) {
                            if (l != i && l != k) {
                                int finalI = i;
                                int finalK = k;
                                int finalJ = j;
                                int finalL = l;
                                conditions.put(colors[i] + "plus" + colors[k] + comparisons[j] + colors[l], nums -> switch (finalJ) {
                                    case 0 -> nums[finalI] + nums[finalK] == nums[finalL];
                                    case 1 -> nums[finalI] + nums[finalK] > nums[finalL];
                                    default -> nums[finalI] + nums[finalK] < nums[finalL];
                                });
                            }
                        }
                    }
                }
            }
        }
        conditions.put("norepitition",nums -> countDuplicates(nums)==1);
        conditions.put("pair",nums -> countDuplicates(nums)==2);
        conditions.put("trio",nums -> countDuplicates(nums)==3);
        conditions.put("nopair",nums -> countDuplicates(nums)!=2);
        conditions.put("ascending", nums -> nums[0] < nums[1] && nums[1] < nums[2]);
        conditions.put("descending", nums -> nums[0] > nums[1] && nums[1] > nums[2]);
        conditions.put("noorder",nums -> !((nums[0] < nums[1] && nums[1] < nums[2])||(nums[0] > nums[1] && nums[1] > nums[2])));
        conditions.put("noascending",nums -> nums[1] != nums[0]+1 && nums[2] != nums[1]+1);
        conditions.put("2ascending", nums -> nums[1] == nums[0]+1 || nums[2] == nums[1]+1 && !(nums[1] == nums[0]+1 && nums[2] == nums[1]+1));
        conditions.put("3ascending",nums -> nums[1] == nums[0]+1 && nums[2] == nums[1]+1);
        conditions.put("noascendingordescending",nums -> nums[1] != nums[0]+1 && nums[2] != nums[1]+1 && nums[1] != nums[0]-1 && nums[2] != nums[1]-1);
        conditions.put("nodescending",nums -> nums[1] != nums[0]-1 && nums[2] != nums[1]-1);
        conditions.put("2descending", nums -> nums[1] == nums[0]-1 || nums[2] == nums[1]-1 && !(nums[1] == nums[0]-1 && nums[2] == nums[1]-1));
        conditions.put("3descending",nums -> nums[1] == nums[0]-1 && nums[2] == nums[1]-1);
        conditions.put("2ascendingordescending",nums ->  (nums[1] == nums[0]+1 || nums[2] == nums[1]+1 && !(nums[1] == nums[0]+1 && nums[2] == nums[1]+1)) ||
                (nums[1] == nums[0]-1 || nums[2] == nums[1]-1 && !(nums[1] == nums[0]-1 && nums[2] == nums[1]-1)));
        conditions.put("3ascendingordescending", nums -> (nums[1] == nums[0]+1 && nums[2] == nums[1]+1) ||
                (nums[1] == nums[0]-1 && nums[2] == nums[1]-1));
        conditions.put("nopower",nums -> !isPower(2,represent(nums)) && !isPower(3,represent(nums)));
        conditions.put("square",nums -> isPower(2,represent(nums)));
        conditions.put("cube",nums -> isPower(3,represent(nums)));
    }
    public static boolean isPower(int power, int value){
        int r = (int) Math.pow(value,1.0/power);
        int total = r;
        for (int i = 0; i < power-1; i++) {
            total *= r;
        }
        return total == value;
    }
    public static int represent(int[] nums){
        return nums[0]*100 + nums[1] + nums[2];
    }
    public static int countDuplicates(int[] nums){
        int[] count = new int[5];
        for (int i = 0; i < nums.length; i++) {
            count[nums[i]-1]++;
        }
        Arrays.sort(count);
        return count[4];
    }
    public static int countEven(int[] nums){
        int total = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]%2==0){
                total++;
            }
        }
        return total;
    }
    public static int count(int r, int[] nums){
        int total = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == r){
                total++;
            }
        }
        return total;
    }
    public static Card card(String... things){
        Card c = new Card();
        for (int i = 0; i < things.length; i++) {
            c.conditions.add(conditions.get(things[i]));
            if (conditions.get(things[i])==null){
                throw new GameException("Card not found: " + things[i]);
            }
        }
        return c;
    }
    public static void addCards(){
        cards.add(card("blueequals1","bluegreater1"));
        cards.add(card("blueless3","blueequals3","bluegreater3"));
        cards.add(card("yellowless3","yellowequals3","yellowgreater3"));
        cards.add(card("yellowless4","yellowequals4","yellowgreater4"));
        cards.add(card("blueeven","blueodd"));
        cards.add(card("yelloweven","yellowodd"));
        cards.add(card("purpleeven","purpleodd"));
        cards.add(card("zero1","one1","two1","three1"));
        cards.add(card("zero3","one3","two3","three3"));
        cards.add(card("zero4","one4","two4","three4"));
        cards.add(card("yellowlessblue","yellowequalsblue","yellowgreaterblue"));
        cards.add(card("purplelessblue","purpleequalsblue","purplegreaterblue"));
        cards.add(card("purplelessyellow","purpleequalsyellow","purplegreateryellow"));
        cards.add(card("bluestrictsmallest","yellowstrictsmallest","purplestrictsmallest"));
        cards.add(card("bluestrictbiggest","yellowstrictbiggest","purplestrictbiggest"));
        cards.add(card("moreeven","moreodd"));
        cards.add(card("zeroeven","oneeven","twoeven","threeeven"));
        cards.add(card("sumeven","sumodd"));
        cards.add(card("yellowplusblueless6","yellowplusblueequals6","yellowplusbluegreater6"));
        cards.add(card("trio","pair","norepitition"));
        cards.add(card("nopair","pair"));
        cards.add(card("ascending","descending","noorder"));
        cards.add(card("sumless6","sumequals6","sumgreater6"));
        cards.add(card("noascending","2ascending","3ascending"));
        cards.add(card("noascendingordescending","2ascendingordescending","3ascendingordescending"));
        cards.add(card("blueless3","yellowless3","purpleless3"));
        cards.add(card("blueless4","yellowless4","purpleless4"));
        cards.add(card("blueequals1","yellowequals1","purpleequals1"));
        cards.add(card("blueequals3","yellowequals3","purpleequals3"));
        cards.add(card("blueequals4","yellowequals4","purpleequals4"));
        cards.add(card("bluegreater1","yellowgreater1","purplegreater1"));
        cards.add(card("bluegreater3","yellowgreater3","purplegreater3"));
        cards.add(card("blueeven","blueodd","yelloweven","yellowodd","purpleeven","purpleodd"));
        cards.add(card("bluesmallest","yellowsmallest","purplesmallest"));
        cards.add(card("bluebiggest","yellowbiggest","purplebiggest"));
        cards.add(card("summultiple3","summultiple4","summultiple5"));
        cards.add(card("blueplusyellowequals4","bluepluspurpleequals4","yellowpluspurpleequals4"));
        cards.add(card("blueplusyellowequals6","bluepluspurpleequals6","yellowpluspurpleequals6"));
        cards.add(card("blueequals1","bluegreater1","yellowequals1","yellowgreater1","purpleequals1","purplegreater1"));
        cards.add(card("blueequals3","bluegreater3","blueless3","yellowequals3","yellowgreater3","yellowless3","purpleequals3","purplegreater3","purpleless3"));
        cards.add(card("blueequals4","bluegreater4","blueless4","yellowequals4","yellowgreater4","yellowless4","purpleequals4","purplegreater4","purpleless4"));
        cards.add(card("bluestrictsmallest","yellowstrictsmallest","purplestrictsmallest","bluestrictbiggest","yellowstrictbiggest","purplestrictbiggest"));
        cards.add(card("yellowlessblue","yellowequalsblue","yellowgreaterblue","purplelessblue","purpleequalsblue","purplegreaterblue"));
        cards.add(card("yellowlessblue","yellowequalsblue","yellowgreaterblue","purplelessyellow","purpleequalsyellow","purplegreateryellow"));
        cards.add(card("zero1","one1","two1","zero3","one3","two3"));
        cards.add(card("zero3","one3","two3","zero4","one4","two4"));
        cards.add(card("zero1","one1","two1","zero4","one4","two4"));
        cards.add(card("yellowlessblue","yellowequalsblue","yellowgreaterblue","purplelessblue","purpleequalsblue","purplegreaterblue","purplelessyellow","purpleequalsyellow","purplegreateryellow"));
        //Expansion cards
        cards.add(card("bluemiddle","yellowmiddle","purplemiddle"));
        cards.add(card("bluestrictmiddle","yellowstrictmiddle","purplestrictmiddle"));

        cards.add(card("nopower","square","cube"));

        cards.add(card("bluemiddle","yellowmiddle","purplemiddle","bluesmallest","yellowsmallest","purplesmallest","bluebiggest","yellowbiggest","purplebiggest"));
        cards.add(card("three1","three2","three3","three4","three5","purpleequals3","yellowequals3","blueequals3","summultiple3","threeeven","threeodd","cube","3ascending","3descending"));
        cards.add(card("blueplusyellowequals7","purpleplusyellowequals7","bluepluspurpleequals7","blueplusyellowless7","bluepluspurpleless7","purpleplusyellowless7","blueplusyellowgreater7","bluepluspurplegreater7","purpleplusyellowgreater7"));
        cards.add(card("zero1","zero2","zero3","zero4","zero5","zeroeven","zeroodd","norepitition","nopair","noascending","nodescending","noorder","nopower"));

        cards.add(card("blueplusyellowequalspurple","blueplusyellowlesspurple","blueplusyellowgreaterpurple"));
        cards.add(card("blueplusyellowequalspurple","bluepluspurpleequalsyellow","purpleplusyellowequalsblue"));
        cards.add(card("blueplusyellowequalspurple","blueplusyellowlesspurple","blueplusyellowgreaterpurple","bluepluspurpleequalsyellow","bluepluspurplelessyellow","bluepluspurplegreateryellow","purpleplusyellowequalsblue","purpleplusyellowlessblue","purpleplusyellowgreaterblue"));

    }
    public static ArrayList<Card> cards = new ArrayList<>();
    public static void main(String[] args) {
        addCards();
        Player.runPlayer();
    }
    public static void runBot(int times){
        int totalTurnNum = 0;
        int totalChecksMade= 0;
        for (int i = 0; i < times; i++) {
            Game game = GameGenerator.generateGame();
            SimpleSolver.solveGame(game);
            totalTurnNum+=game.turnNum;
            totalChecksMade += game.totalChecksMade;
        }
        System.out.println("Average Turns used: " + ((double)totalTurnNum / times) + " Average Checks made: " + ((double)totalChecksMade / times));

    }
}

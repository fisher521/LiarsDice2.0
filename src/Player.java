import java.util.Random;
import java.util.Scanner;
/**
 * Player Class
 *
 * @author Fisher
 * @version 6/28/18
 */
public class Player {
    static Random generator = new Random();
    private static Scanner reader = new Scanner(System.in);

    String name;
    int numberOfDice;
    int [] rolls;

    Player(String name) {
        if (name.equals("")) {
            this.name = "???";
        }
        else {
            this.name = name;
        }
        rolls = new int[5];
        numberOfDice = 5;
        rollDice();
    }

    //mutators and accessors
    public String getName() {
        return name;
    }
    public int getNumberOfDice() {
        return numberOfDice;
    }
    public int getRoll(int rollNumber) {
        return rolls[rollNumber];
    }
    public int[] getRolls() {
        return rolls;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setNumberOfDice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }
    public void setRolls(int[] rolls) {
        this.rolls = rolls;
    }
    public void loseDice() {
        numberOfDice--;
    }

    //behavior methods
    void rollDice(){
        for(int i = 0; i < numberOfDice; i++) {
            rolls[i] = generator.nextInt(6) + 1;
        }
    }
    void printRolls() {
        for (int i = 1; i <= numberOfDice; i++) {
            System.out.print(rolls[i - 1]);
            if (numberOfDice - i >= 1) {
                System.out.print(", ");
            }
        }
    }
    void firstTurn() {
        System.out.println("Bid a quantity and face value.\nQuantity: ");
        Game.quantity = UtilityMethods.inputPosInt();
        System.out.println("Face Value: ");
        Game.faceValue = UtilityMethods.inputPosInt();

    }
    String turn() {
        String decision;

        System.out.println("\n" + name.toUpperCase() + "'S TURN");
        while (true) {
            System.out.println("Would you like to make a higher bid or challenge the current bid? (b/c)");
            decision = reader.nextLine();

            if (decision.equals("c") || decision.equals("b")) {
                return decision;
            }
        }
    }
    void bid () {
        while (true) {
            System.out.println("Would you like to enter a quantity or a face value? (q/f)");
            String decision = reader.nextLine();

            if (decision.equals("q")) {
                Game.quantity = UtilityMethods.inputHigherValue("quantity", Game.quantity);
                break;
            } else if (decision.equals("f")) {
                Game.faceValue = UtilityMethods.inputHigherValue("face value", Game.faceValue);
                break;
            }
        }
    }
    int challenge () {
        //prints dice rolls for all players
        for (Player player : Game.playerArrayList) {

            System.out.print("\n" + player.getName() + "'s Rolls: ");
            player.printRolls();
        }

        //counts matching faces
        int matchingFaces = 0;
        for (Player player : Game.playerArrayList) {

            for (int i = 0; i < player.getNumberOfDice(); i++) {
                if (player.getRoll(i) == Game.faceValue) {
                    matchingFaces++;
                }
            }
        }
        System.out.println("\n\nMatching faces: " + matchingFaces);

        //returns index of the loser of the challenge
        if (matchingFaces >= Game.quantity) {
            return Game.playerArrayList.indexOf(this);
        } else {
            if (Game.playerArrayList.indexOf(this) > 0) {
                return Game.playerArrayList.indexOf(this) - 1;
            } else {
                return Game.playerArrayList.size() - 1;
            }
        }
    }
}


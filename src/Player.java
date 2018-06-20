import java.util.Random;
import java.util.Scanner;
/**
 * Player Class
 *
 * @author Fisher
 * @version 6/19/18
 */
public class Player {
    private static Random generator = new Random();
    private static Scanner reader = new Scanner(System.in);

    private String name;
    private int numberOfDice;
    private int [] rolls;

    public Player(String name) {
        this.name = name;
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
    public void rollDice(){
        for(int i = 0; i < numberOfDice; i++) {
            rolls[i] = generator.nextInt(6) + 1;
        }
    }
}

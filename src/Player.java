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

    int challenge (int quantity, int faceValue) {

        //prints dice rolls for all players
        for (Player player : Game.playerArrayList) {

            System.out.print("\n" + player.getName() + "'s Rolls: ");
            player.printRolls();
        }

        //counts matching faces
        int matchingFaces = 0;
        for (Player player : Game.playerArrayList) {

            for (int i = 0; i < player.getNumberOfDice(); i++) {
                if (player.getRoll(i) == faceValue) {
                    matchingFaces++;
                }
            }
        }
        System.out.println("\nMatching faces: " + matchingFaces);

        //returns index of the loser of the challenge
        if (matchingFaces >= quantity) {
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


import java.util.Scanner;
/**
 * Liars Dice Game
 *
 * @author Fisher
 * @version 6/18/18
 */
public class Game {
    private static Player[] players;
    public static void main (String [] args) {
        Scanner reader = new Scanner(System.in);

        //Creates an array of players objects named "players"
        System.out.println("How many players will be playing?");
        players = new Player[UtilityMethods.inputInt("Invalid number!")];
        int firstPlayer = players.length - 1;

        //Creates player objects
        for (int i = 0; i < players.length; i++) {
            System.out.println("Player " + (i + 1) + ", what is your name? ");
            players[i] = new Player(reader.nextLine());
        }

        while(isGoing()) {

            //dice rolls
            for (Player player : players) {
                System.out.println(player.getName() + ", press enter to roll the dice.");
                reader.nextLine();
                player.rollDice();

                System.out.print("Rolls: ");
                for (int x = 0; x < player.getRolls().length - 1; x++) {
                    System.out.print(player.getRolls()[x] + ", ");
                }
                System.out.print(player.getRolls()[player.getRolls().length - 1]);

                System.out.println();
                UtilityMethods.pause("continue");
            }

            //turn 1
            System.out.println(players[firstPlayer].getName().toUpperCase() + "'S TURN");
            System.out.println("Bid a quantity and face value.\nQuantity: ");
            int quantity = reader.nextInt();
            reader.nextLine();
            System.out.println("Face Value: ");
            int faceValue = reader.nextInt();
            reader.nextLine();
            System.out.println(players[firstPlayer].getName().toUpperCase() + " bid " + quantity + " " + faceValue + "'s.");

            //subsequent turns
            boolean round = true;
            int currentPlayer = 0;
            while (round) {
                System.out.println("\n" + players[currentPlayer].getName().toUpperCase() + "'S TURN");
                while (true) {
                    System.out.println("Would you like to challenge or make a higher bid? (c/b)");
                    String decision = reader.nextLine();

                    if (decision.equals("c")) {

                        //prints dice rolls for all players
                        for (Player player : players) {
                            System.out.print("\n" + player.getName() + "'s Rolls: ");
                            for (int i = 1; i <= player.getNumberOfDice(); i++) {
                                System.out.print(player.getRolls()[i - 1]);
                                if (player.getNumberOfDice() - i >= 1) {
                                    System.out.print(", ");
                                }
                            }
                        }

                        //counts matching faces
                        int matchingFaces = 0;
                        for (Player player : players) {
                            for (int i = 0; i < player.getNumberOfDice(); i++) {
                                if (player.getRoll(i) == faceValue) {
                                    matchingFaces++;
                                }
                            }
                        }
                        System.out.println("\nMatching faces: " + matchingFaces);

                        //penalizes loser and ends round
                        if (matchingFaces >= quantity) {
                            players[currentPlayer].loseDice();
                            System.out.println(players[currentPlayer].getName() + " has lost a die and now has " + players[currentPlayer].getNumberOfDice() + " dice!");
                        } else {
                            if (currentPlayer > 0) {
                                players[currentPlayer - 1].loseDice();
                            } else {
                                players[players.length - 1].loseDice();
                            }
                            System.out.println(players[players.length - 1].getName() + " has lost a die and now has " + players[players.length - 1].getNumberOfDice() + " dice!");
                        }

                        round = false;
                        System.out.println();

                        if (isGoing()) {
                            UtilityMethods.pause("begin new round");
                        }

                        if (currentPlayer < players.length - 1) {
                            currentPlayer++;
                        }
                        else {
                            currentPlayer = 0;
                        }
                        break;
                    }
                    if (decision.equals("b")) {
                        while (true) {
                            System.out.println("Would you like to enter a higher face value or a higher quantity? (f/q)");
                            String bidDecision = reader.nextLine();

                            if (bidDecision.equals("f")) {
                                faceValue = UtilityMethods.inputHigherValue("face value", faceValue);
                                break;
                            } else if (bidDecision.equals("q")) {
                                quantity = UtilityMethods.inputHigherValue("quantity", quantity);
                                break;
                            }
                        }
                        System.out.println(players[currentPlayer].getName() + " bid " + quantity + " " + faceValue + "'s.");

                        if (currentPlayer < players.length - 1) {
                            currentPlayer++;
                        }
                        else {
                            currentPlayer = 0;
                        }
                        break;
                    }
                }
            }
        }
    }
    private static boolean isGoing() {
        int playersLeft = players.length;
        for (Player player : players) {
            if (player.getNumberOfDice() == 0) {
                playersLeft--;
            }
        }
        return playersLeft != 1;
    }
}

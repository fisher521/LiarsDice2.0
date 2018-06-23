import java.util.*;
/**
 * Liars Dice Game
 *
 * @author Fisher
 * @version 6/18/18
 */
public class Game {
    private static ArrayList<Player> playerArrayList;
    private static int firstPlayer;
    private static int currentPlayer;
    private static int roundLoser;
    public static void main (String [] args) {
        Scanner reader = new Scanner(System.in);

        //Creates an arrayList of Player objects
        System.out.println("How many players will be playing?");
        int playerCount = UtilityMethods.inputPosInt("Invalid number!");
        playerArrayList = new ArrayList<>(playerCount);
        int playersLeft = playerCount;
        firstPlayer = playerCount - 1;

        //Creates Player objects
        for (int i = 0; i < playerCount; i++) {
            System.out.println("Player " + (i + 1) + ", what is your name? ");
            playerArrayList.add(new Player(reader.nextLine()));
        }

        while(playersLeft != 1) {

            //dice rolls
            for (Player player : playerArrayList) {

                System.out.println(player.getName() + ", press enter to roll the dice.");
                reader.nextLine();
                player.rollDice();

                System.out.print("Rolls: ");
                printRolls(player);

                System.out.println();
                UtilityMethods.pause("continue");
            }

            //turn 1
            System.out.println(playerArrayList.get(firstPlayer).getName().toUpperCase() + "'S TURN");
            System.out.println("Bid a quantity and face value.\nQuantity: ");
            int quantity = UtilityMethods.inputPosInt("Invalid number!");
            System.out.println("Face Value: ");
            int faceValue = UtilityMethods.inputPosInt("Invalid number!");
            System.out.println(playerArrayList.get(firstPlayer).getName().toUpperCase() + " bid " + quantity + " " + faceValue + "'s.");

            //subsequent turns
            boolean round = true;
            currentPlayer = firstPlayer;
            advanceCurrentPlayer();
            while (round) {
                System.out.println("\n" + playerArrayList.get(currentPlayer).getName().toUpperCase() + "'S TURN");
                while (true) {
                    System.out.println("Would you like to challenge or make a higher bid? (c/b)");
                    String decision = reader.nextLine();

                    if (decision.equals("c")) {

                        //prints dice rolls for all players
                        for (Player player : playerArrayList) {

                            System.out.print("\n" + player.getName() + "'s Rolls: ");
                            printRolls(player);
                        }

                        //counts matching faces
                        int matchingFaces = 0;
                        for (Player player : playerArrayList) {

                            for (int i = 0; i < player.getNumberOfDice(); i++) {
                                if (player.getRoll(i) == faceValue) {
                                    matchingFaces++;
                                }
                            }
                        }
                        System.out.println("\nMatching faces: " + matchingFaces);

                        //penalizes loser and ends round
                        if (matchingFaces >= quantity) {
                            playerArrayList.get(currentPlayer).loseDice();
                            roundLoser = currentPlayer;
                        } else {
                            if (currentPlayer > 0) {
                                playerArrayList.get(currentPlayer - 1).loseDice();
                                roundLoser = currentPlayer - 1;
                            } else {
                                playerArrayList.get(playerArrayList.size() - 1).loseDice();
                                roundLoser = playerArrayList.size() - 1;
                            }
                        }
                        System.out.println(playerArrayList.get(roundLoser).getName() + " has lost a die and now has " + playerArrayList.get(roundLoser).getNumberOfDice() + " dice!");
                        if (playerArrayList.get(roundLoser).getNumberOfDice() == 0) {
                            playerArrayList.remove(roundLoser);
                            playersLeft--;
                        }
                        advanceFirstPlayer();
                        round = false;
                        System.out.println();

                        if (playersLeft != 1) {
                            UtilityMethods.pause("begin new round");
                        }

                        if (currentPlayer < playerArrayList.size() - 1) {
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
                        System.out.println(playerArrayList.get(currentPlayer).getName() + " bid " + quantity + " " + faceValue + "'s.");

                        if (currentPlayer < playerArrayList.size() - 1) {
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
        for (Player player : playerArrayList) {
            if (player == null)
                continue;

            if (player.getNumberOfDice() > 0) {
                System.out.print(player.getName() + " wins!");
            }
        }
    }
    private static void printRolls(Player player) {
        for (int i = 1; i <= player.getNumberOfDice(); i++) {
            System.out.print(player.getRolls()[i - 1]);
            if (player.getNumberOfDice() - i >= 1) {
                System.out.print(", ");
            }
        }
    }
    private static void advanceFirstPlayer() {
        firstPlayer = roundLoser;
        while (true) {
            if (playerArrayList.get(firstPlayer) != null) {
                break;
            }
            else {
                if (firstPlayer < playerArrayList.size() - 1) {
                    firstPlayer++;
                }
                else {
                    firstPlayer = 0;
                }
            }
        }
    }
    private static void advanceCurrentPlayer() {
        while (true) {
            if (currentPlayer < playerArrayList.size() - 1) {
                currentPlayer++;
            } else {
                currentPlayer = 0;
            }
            if (playerArrayList.get(currentPlayer) != null) {
                break;
            }
        }
    }
}

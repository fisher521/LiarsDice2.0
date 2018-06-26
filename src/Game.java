import java.util.*;
/**
 * Liars Dice Game
 *
 * @author Fisher
 * @version 6/18/18
 */
public class Game {
    static ArrayList<Player> playerArrayList;
    private static int firstPlayer;
    private static int currentPlayer;
    public static Player lastPlayer;
    private static int roundLoser;

    static int quantity;
    static int faceValue;
    public static void main (String [] args) {
        Scanner reader = new Scanner(System.in);

        //Creates an arrayList of Player objects
        System.out.println("How many players will be playing?");
        int playerCount = UtilityMethods.inputPosInt();

        System.out.println("How many bot players will be playing?");
        int botCount = UtilityMethods.inputBoundInt(1, BotPlayer.nameLinkedList.size());

        int totalPlayerCount = playerCount + botCount;
        playerArrayList = new ArrayList<>(totalPlayerCount);
        firstPlayer = totalPlayerCount - 1;

        //Creates Player objects
        for (int i = 0; i < playerCount; i++) {
            System.out.println("Player " + (i + 1) + ", what is your name? ");
            playerArrayList.add(new Player(reader.nextLine()));
        }
        //Creates BotPlayer objects
        System.out.println();
        for (int i = 0; i < botCount; i++) {
            playerArrayList.add(new BotPlayer());
            System.out.println("Added " + playerArrayList.get(playerCount + i).getName());
        }

        while(totalPlayerCount != 1) {

            //dice rolls
            for (Player player : playerArrayList) {
                System.out.println(player.getName() + ", press enter to roll the dice.");
                reader.nextLine();
                player.rollDice();

                System.out.print("Rolls: ");
                player.printRolls();

                System.out.println();
                UtilityMethods.pause("continue");
            }

            //turn 1
            System.out.println(playerArrayList.get(firstPlayer).getName().toUpperCase() + "'S TURN");
            System.out.println("Bid a quantity and face value.\nQuantity: ");
            quantity = UtilityMethods.inputPosInt();
            System.out.println("Face Value: ");
            faceValue = UtilityMethods.inputPosInt();
            System.out.println(playerArrayList.get(firstPlayer).getName().toUpperCase() + " bid " + quantity + " " + faceValue + "'s.");

            //subsequent turns
            boolean round = true;
            currentPlayer = firstPlayer;
            advanceCurrentPlayer();

            turns:
            while (round) {
                String decision = playerArrayList.get(currentPlayer).turn();

                switch (decision) {
                    case "c":
                        if (currentPlayer > 0) {
                            lastPlayer = playerArrayList.get(currentPlayer - 1);
                        } else {
                            lastPlayer = playerArrayList.get(playerArrayList.size() - 1);
                        }

                        System.out.println(playerArrayList.get(currentPlayer).getName() + " challenged " + lastPlayer.getName() + "'s bid of " + quantity + " " + faceValue + "'s!");
                        roundLoser = playerArrayList.get(currentPlayer).challenge();

                        //penalizes loser
                        playerArrayList.get(roundLoser).loseDice();
                        System.out.println(playerArrayList.get(roundLoser).getName() + " has lost a die and now has " + playerArrayList.get(roundLoser).getNumberOfDice() + " dice!");
                        if (playerArrayList.get(roundLoser).getNumberOfDice() == 0) {
                            playerArrayList.remove(roundLoser);
                            totalPlayerCount--;
                        }

                        //ends round
                        advanceFirstPlayer();
                        advanceCurrentPlayer();
                        round = false;
                        System.out.println();

                        if (totalPlayerCount != 1) {
                            UtilityMethods.pause("begin new round");
                        }
                        break;
                    case "b":
                        playerArrayList.get(currentPlayer).bid();
                        System.out.println(playerArrayList.get(currentPlayer).getName() + " bid " + quantity + " " + faceValue + "'s.");
                        advanceCurrentPlayer();
                }
            }
        }
        System.out.print(playerArrayList.get(currentPlayer).getName() + " wins!");
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
        if (currentPlayer < playerArrayList.size() - 1) {
            currentPlayer++;
        } else {
            currentPlayer = 0;
        }
    }
}

import java.util.*;
/**
 * Liars Dice Game
 *
 * @author Fisher
 * @version 6/28/18
 */
public class Game {
    static ArrayList<Player> playerArrayList;
    private static int firstPlayerIndex;
    private static int currentPlayerIndex;
    static Player lastPlayer;
    private static int roundLoser;

    static int quantity;
    static int faceValue;
    public static void main (String [] args) {
        Scanner reader = new Scanner(System.in);

        //Creates an arrayList of Player objects
        System.out.println("How many human players will be playing?");
        int playerCount = UtilityMethods.inputPosInt();

        System.out.println("How many bot players will be playing?");
        int botCount = UtilityMethods.inputBoundInt(0, BotPlayer.nameLinkedList.size());

        int totalPlayerCount = playerCount + botCount;
        playerArrayList = new ArrayList<>(totalPlayerCount);

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

        firstPlayerIndex = totalPlayerCount - 1;
        System.out.println();
        while(totalPlayerCount != 1) {

            //dice rolls
            for (Player player : playerArrayList) {
                if (!(player instanceof BotPlayer)) {
                    System.out.println(player.getName() + ", press enter to roll the dice.");
                    reader.nextLine();
                    player.rollDice();

                    System.out.print("Rolls: ");
                    player.printRolls();

                    System.out.println();
                    UtilityMethods.pause("continue");
                }
            }

            //turn 1
            System.out.println(playerArrayList.get(firstPlayerIndex).getName().toUpperCase() + "'S TURN");
            playerArrayList.get(firstPlayerIndex).firstTurn();
            System.out.println(playerArrayList.get(firstPlayerIndex).getName() + " bid " + quantity + " " + faceValue + "'s.");

            //subsequent turns
            boolean round = true;
            currentPlayerIndex = firstPlayerIndex;
            advanceCurrentPlayer();

            while (round) {
                if (currentPlayerIndex > 0) {
                    lastPlayer = playerArrayList.get(currentPlayerIndex - 1);
                } else {
                    lastPlayer = playerArrayList.get(playerArrayList.size() - 1);
                }
                String decision = playerArrayList.get(currentPlayerIndex).turn();

                switch (decision) {
                    case "c":
                        System.out.println(playerArrayList.get(currentPlayerIndex).getName() + " challenged " + lastPlayer.getName() + "'s bid of " + quantity + " " + faceValue + "'s!");
                        UtilityMethods.pause("continue");
                        roundLoser = playerArrayList.get(currentPlayerIndex).challenge();

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
                        playerArrayList.get(currentPlayerIndex).bid();
                        System.out.println(playerArrayList.get(currentPlayerIndex).getName() + " bid " + quantity + " " + faceValue + "'s.");
                        advanceCurrentPlayer();
                }
            }
        }
        System.out.print(playerArrayList.get(currentPlayerIndex).getName() + " wins!");
    }

    private static void advanceFirstPlayer() {
        firstPlayerIndex = roundLoser;
        while (true) {
            if (playerArrayList.get(firstPlayerIndex) != null) {
                break;
            }
            else {
                if (firstPlayerIndex < playerArrayList.size() - 1) {
                    firstPlayerIndex++;
                }
                else {
                    firstPlayerIndex = 0;
                }
            }
        }
    }
    private static void advanceCurrentPlayer() {
        if (currentPlayerIndex < playerArrayList.size() - 1) {
            currentPlayerIndex++;
        } else {
            currentPlayerIndex = 0;
        }
    }
}

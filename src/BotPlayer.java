import java.util.*;
/**
 * Bot Player Class
 *
 * @author Fisher
 * @version 6/19/18
 */
public class BotPlayer extends Player {
    static LinkedList<String> nameLinkedList = new LinkedList<>();
    private static int nameNumber = 0;

    static {
        nameLinkedList.add("[BOT] Forrest");
        nameLinkedList.add("[BOT] Samantha");
        nameLinkedList.add("[BOT] Cathy");
        nameLinkedList.add("[BOT] Jonathan");
        nameLinkedList.add("[BOT] Justin");
        nameLinkedList.add("[BOT] Jazmine");
        nameLinkedList.add("[BOT] Allen");
        nameLinkedList.add("[BOT] Justin");
        nameLinkedList.add("[BOT] William");
        nameLinkedList.add("[BOT] Angela");
        nameLinkedList.add("[BOT] Gavin");
        nameLinkedList.add("[BOT] Michael");
        nameLinkedList.add("[BOT] Jennifer");
        nameLinkedList.add("[BOT] Sneha");
        nameLinkedList.add("[BOT] Nalin");
        nameLinkedList.add("[BOT] Edward");
        nameLinkedList.add("[BOT] Fisher");
        nameLinkedList.add("[BOT] Ayush");
    }

    public BotPlayer() {

        super("");
        name = nameLinkedList.get(nameNumber);

        nameNumber++;
    }

    //behavior methods
    @Override
    String turn() {
        //counts how many of the current face the computer has
        int botMatchingFaces = 0;
        for (int i = 0; i < numberOfDice; i++) {
            if (rolls[i] == Game.faceValue) {
                botMatchingFaces++;
            }
        }
        //the computer's guess on how many of the current face the player has
        int guessMatchingFaces = generator.nextInt(3) + 1;

        String decision;
        System.out.println("\n" + name.toUpperCase() + "'S TURN");

        if (Game.quantity > botMatchingFaces + guessMatchingFaces || Game.faceValue > 6 || Game.quantity > botMatchingFaces + Game.lastPlayer.getNumberOfDice()) {
            return "c";
        }
        else {
            return "b";
        }
    }

    @Override
    void bid() {
        int j = 0;
        int botHigherMatchingFaces = 0;
        for (int i = Game.faceValue + 1; i <= 6; i++) {
             for (j = 0; j <= numberOfDice; j++) {
                if (rolls[j] == Game.faceValue) {
                    botHigherMatchingFaces++;
                }
                if (botHigherMatchingFaces == Game.quantity) {
                    break;
                }
            }
        }
        if (botHigherMatchingFaces == Game.quantity) {
            Game.faceValue = j;
        }
    }

    @Override
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
        System.out.println("\nMatching faces: " + matchingFaces);

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


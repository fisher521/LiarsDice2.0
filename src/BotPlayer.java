import java.util.*;
/**
 * Bot Player Class
 *
 * Inherits Player variables and methods, but is automatic
 *
 * @author Fisher
 * @version 6/28/18
 */
class BotPlayer extends Player {
    static LinkedList<String> nameLinkedList = new LinkedList<>();

    //adds bot names to a list (thanks to Michael for help here)
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
        nameLinkedList.add("[BOT] Jenni");
        nameLinkedList.add("[BOT] Sneha");
        nameLinkedList.add("[BOT] Nalin");
        nameLinkedList.add("[BOT] Edward");
        nameLinkedList.add("[BOT] Fisher");
        nameLinkedList.add("[BOT] Ayush");
    }

    public BotPlayer() {
        super("");

        int nameIndex = generator.nextInt(nameLinkedList.size());
        name = nameLinkedList.get(nameIndex);
        nameLinkedList.remove(nameIndex);
    }

    //behavior methods
    @Override
    void firstTurn() {
        //bids a random quantity of its most common face
        int mostCommonFace = 1;
        int mostCommonFaceQuantity = 0;
        int matchingFaces;
        for (int face = 1; face <= 6; face++) {
            matchingFaces = 0;
            for (int i = 0; i < numberOfDice; i++) {
                if (rolls[i] == face) {
                    matchingFaces++;
                }
            }
            if (matchingFaces >= mostCommonFaceQuantity) {
                mostCommonFace = face;
                mostCommonFaceQuantity = matchingFaces;
            }
        }
        Game.quantity = generator.nextInt(mostCommonFaceQuantity) + 1;
        Game.faceValue = mostCommonFace;
    }
    @Override
    String turn() {
        //counts how many of the current face the computer has
        int botMatchingFaces = 0;
        for (int i = 0; i < numberOfDice; i++) {
            if (rolls[i] == Game.faceValue) {
                botMatchingFaces++;
            }
        }
        //the computer's guess on how many of the current face all players have
        int guessMatchingFaces = 0;
        int totalNumberOfDice = 0;
        for(int i = 0; i < (Game.playerArrayList.size() - 1); i++) {
            guessMatchingFaces += generator.nextInt(2) + 1;
            totalNumberOfDice += Game.playerArrayList.get(i).getNumberOfDice();
        }

        System.out.println("\n" + name.toUpperCase() + "'S TURN");

        if (Game.quantity > botMatchingFaces + guessMatchingFaces || Game.faceValue > 6 || Game.quantity > botMatchingFaces + totalNumberOfDice) {
            return "c";
        }
        else {
            return "b";
        }
    }
    @Override
    void bid() {

        //checks if the bot has the same quantity of a higher face value
        int face;
        int botHigherMatchingFaces = 0;
        for (face = Game.faceValue + 1; face < 6; face++) {
             for (int j = 0; j < numberOfDice; j++) {
                if (rolls[j] == face) {
                    botHigherMatchingFaces++;
                }
                if (botHigherMatchingFaces == Game.quantity) {
                    break;
                }
            }
        }
        if (botHigherMatchingFaces == Game.quantity) {
            Game.faceValue = face;
        }
        else {
            Game.quantity++;
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
        System.out.println("\n\nMatching faces: " + matchingFaces);

        //returns index of the loser of the challenge (Thanks to Dan I for help with this part)
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


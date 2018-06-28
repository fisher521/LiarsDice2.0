import java.util.Scanner;
/**
 * Liars Dice Main Menu
 *
 * @author Fisher
 * @version 6/28/18
 */
public class Menu {
    public static void main (String [] args) {
        System.out.println ("Welcome to Liar's Dice!");
        menu:
        while (true) {
            Scanner reader = new Scanner(System.in);
            System.out.print("\n");

            System.out.println("Choose an option: ");
            System.out.println("1) Play");
            System.out.println("2) View Rules");
            System.out.println("3) View About");
            System.out.println("4) Quit");

            String response = reader.nextLine();
            switch (response) {
                case "1":
                    Game.main(null);
                    break;
                case "2":
                    System.out.println("Rules: ");
                    System.out.println("The game is played over multiple rounds.");
                    System.out.println("To begin each round, all players roll their dice, keeping their rolls hidden from other players.");
                    System.out.println("" +
                            "The first player then states a bid consisting of a face (1's, 5's, etc.) and a quantity. " +
                            "\nThe quantity represents the player's guess as to how many of each face have been rolled by all the players at the table,\nincluding themselves. " +
                            "\nFor example, a player might bid \"five 2's.\"");
                    System.out.println("Each subsequent player can either then bid a higher quantity of any face, bid the same quantity of a higher face,\nor they can challenge the previous bid. ");
                    System.out.println("If the player challenges the previous bid, all players reveal their dice. " +
                            "\nIf the bid is matched or exceeded, the challenger loses a die. Otherwise, the bidder loses a die.");
                    System.out.println("A new round then starts.");
                    System.out.println ("The loser of the previous round begins the next round. ");
                    //System.out.println ("In the event that the game comes down to two players with only a single dice each, bids are then made on the sum of both dice instead the quantity of faces rolled.")
                    System.out.println("This is repeated until only one player has dice left. That player is the winner.");
                    break;
                case "3":
                    System.out.println("About: ");
                    System.out.println("Created by Fisher Sun");
                    System.out.println("Credits to dicegamedepot.com for the rules");
                    System.out.println("Special Thanks to Michael, Dan, Jonathan, and Mrs. DiBenedetto for all the help (this could not be done without you all)");
                    break;
                case "4":
                    System.out.println("See you another time!");
                    break menu;
            }
        }
    }
}

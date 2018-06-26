import java.util.Scanner;
/**
 * Utility Methods
 *
 * Methods to assist in making programs fool-proof.
 *
 * @author Fisher
 * @version 6/19/18
 */
public class UtilityMethods {
    private static Scanner reader = new Scanner(System.in);

    public static int inputPosInt() {
        int integer;
        while (true) {
            try {
                String response = reader.nextLine();
                integer = Integer.parseInt(response);
                if (integer > 0) {
                    break;
                }
                else {
                    System.out.println("Invalid number!");
                }
            } catch (NumberFormatException exception) {
                System.out.println("Invalid number!");
            }
        }
        return integer;
    }
    public static int inputBoundInt(int lowerBound, int upperBound) {
        int integer;
        while (true) {
            try {
                String response = reader.nextLine();
                integer = Integer.parseInt(response);
                if (integer >= lowerBound && integer <= upperBound) {
                    break;
                }
                else {
                    System.out.println("Invalid number!");
                }
            } catch (NumberFormatException exception) {
                System.out.println("Invalid number!");
            }
        }
        return integer;
    }

    public static void pause(String reason) {
        System.out.println("Press enter to " + reason + "...");
        reader.nextLine();
        //clears screen
        for (int i = 1; i < 20; i++) {
            System.out.println("\n");
        }
    }

    public static int inputHigherValue (String something, int oldValue) {
        while (true) {
            System.out.println("Enter a higher " + something + ":");
            int newValue = inputPosInt();
            if (newValue > oldValue) {
                return newValue;
            }
        }
    }
}


package Beginning;
import java.util.Scanner;

public class UserInConsoleOut {

    public static void greet(String name) {
        System.out.println("Hello," + name + "!");
    }

    public static boolean isValidName(String name) {

        return name.matches("[a-zA-Z]+") && name.length() >= 2;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueGreeting = true;

        while (continueGreeting) {
            System.out.print("Enter a name (or 'Goodbye' to quit): ");
            String name = scanner.nextLine();

            if (name.equalsIgnoreCase("Goodbye")) {
                continueGreeting = false;
            } else if (name.trim().isEmpty() || !isValidName(name) ) {
                System.out.println("Please enter a valid name.");
            } else {
                greet(name);
            }
        }

        System.out.println("Goodbye!");
        scanner.close();

    }
}

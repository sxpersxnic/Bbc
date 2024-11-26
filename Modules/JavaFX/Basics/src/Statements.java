public class Statements {
    public static void main(String[] args) {
        // Übung 1
        int age = 16;
        System.out.println("Übung 1: " + age);

        // Übung 2
        int num1 = 1;
        int num2 = 2;
        int sum = num1 + num2;

        System.out.println("Übung 2: " + num1 +  " + " + num2 + " = " + sum);

        greet("Melvin");
    }

    public static void greet(String name) {
        System.out.println("Übung 3: Hallo, " + name + "!");
    }
}
public class TypeCasting {
    public static void main(String [] args){
        int myNumber = 9;
        double myDouble = myNumber;
        System.out.println(myDouble);

        String myStringNumber = "10";
        int number = Integer.parseInt(myStringNumber);
        System.out.println(number);

        // Übung 1
        int uebung1int = 11;
        double uebung1double = uebung1int;
        System.out.println(uebung1double);

        // Übung 2
        double uebung2double = 12.4;
        int uebung2int = (int) uebung2double;
        System.out.println(uebung2int);

        // Übung 3
        String uebung3string = "32";
        int uebung3int = Integer.parseInt(uebung3string);
        System.out.println(uebung3int);
    }
}

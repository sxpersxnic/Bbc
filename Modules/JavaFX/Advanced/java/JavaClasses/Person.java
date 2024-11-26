package JavaClasses;

public class Person {
    // Attribute
    String name;
    int age;

    // Konstruktor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Methode
    public void introduce() {
        System.out.println("Hallo, mein Name ist " + name + " und ich bin " + age + "Jahre alt.");
    }
}

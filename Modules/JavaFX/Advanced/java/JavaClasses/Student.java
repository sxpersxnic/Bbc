package JavaClasses;

public class Student {

    public static int studentCount = 0;



    public Student() {
        studentCount++;
    }



    public static int getStudentCount() {
        return studentCount;
    }

    public static void main(String[] args) {
        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();
        System.out.println("Anzahl der Studenten: " + Student.getStudentCount());
    }
}




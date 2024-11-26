# Prüfungsvorbereitung - schriftliche Prüfung Java - 28.11.23

---

## Geprüfte Themen






- ## [Deklaration und Initialisierung von Variablen](#Abschnitt-1)
  - ### [Deklaration](#Abschnitt-1-1)
  - ### [Initialisierung](#Abschnitt-1-2)






- ## [If-Bedingungen mit and / or etc.](#Abschnitt-2)
  - ### [If-Statement](#Abschnitt-2-1)
    - #### [If](#Abschnitt-2-1-1)
    - #### [else](#Abschnitt-2-1-2)
    - #### [else if](#Abschnitt-2-1-3)
  - ### [Operatoren](#Abschnitt-2-2)
    - #### [Arithmetische Operatoren](#Abschnitt-2-2-1)
    - #### [Vergleichsoperatoren](#Abschnitt-2-2-2)
    - #### [Logische Operatoren](#Abschnitt-2-2-3)
    - #### [Zuweisungsoperatoren](#Abschnitt-2-2-4)





- ## [For- und While-Schlaufen](#Abschnitt-3)
  - ### [For](#Abschnitt-3-1)
  - ### [While](#Abschnitt-3-2)





- ## [Arrays (Initialisierung, Werte schreiben und lesen)](#Abschnitt-4)
  - ### [Array erstellen](#Abschnitt-4-1)
  - ### [Array durchgehen](#Abschnitt-4-2)





- ## [***Statische*** Klassen und Methoden](#Abschnitt-5)





- ## [Klassen mit Getter, Setter und Konstruktor](#Abschnitt-6)
  - ### [Setter](#Abschnitt-6-1)
  - ### [Getter](Abschnitt-6-2)
  - ### [Konstruktor](#Abschnitt-6-3)





- ## [Enums](#Abschnitt-7)





---


<a name="Abschnitt-1"></a>

## Deklaration und Initialisierung von Variablen





<a name="Abschnitt-1-1"></a>

### Deklaration

Beim deklarieren teilst du dem Compiler mit, dass du eine Variabel mit einem bestimmten Datentypen erstellen möchtest.
In diesem Prozess reserviert der Compiler schon den Speicherplatz für die Variabel.
**Beispiel einer Deklaration:**


````java
    public class StatementTypesExample { 
       public static void main(String[] args) {
           int number;
           char character;
           boolean isTrue;
       }
    }
````





<a name="Abschnitt-1-2"></a>

### Initialisierung

Bei der Initialisierung einer Variabel wird ihr ein Wert mitgegeben. Zuerst wurde bei der Deklaration lediglich der 
Datentyp bestimmt, doch nun fehlen noch die Daten.


````java
   public class StatementTypesExample {
       public static void main(String[] args) {
           number = 10;
           character = 'A';
           isTrue = true;
       }
   }
````


---



<a name="Abschnitt-2"></a>

## If-Bedingungen mit and / or etc.



<a name="Abschnitt-2-1"></a>

## If-Statement


<a name="Abschnitt-2-1-1"></a>

## If

```python

if a == b:
  print("equal")
else:
  print("not equal")
  
```

```java
public class Main {
    
    public static void main(String[] args)
    {
        int a = 10;
        int b = 20;
        
        if (a ==  b)
        {
            System.out.println("equal");
        }
    }
}
```


<a name="Abschnitt-2-1-2"></a>

## else

```python

if a == b:
  print("equal")
else:
  print("not equal")
  
```

```java
public class Main {
    
    public static void main(String[] args)
    {
        int a = 10;
        int b = 20;
        
        if (a ==  b)
        {
            System.out.println("equal");
        }
        
        else 
        {
            System.out.println("not equal");
        }
    }
}
```

<a name="Abschnitt-2-1-3"></a>

## else if

```python

if a == b:
  print("equal")
else:
  print("not equal")
  
```

```java
public class Main {
    
    public static void main(String[] args)
    {
        int a = 10;
        int b = 20;
        
        if (a ==  b)
        {
            System.out.println("equal");
        }
        
        else if (a == 10) 
        {
            System.out.println("a is 10");
        }  
        
        else 
        {
            System.out.println("not equal");
        }
    }
}
```
 
<a name="Abschnitt-2-2"></a>

## Operatoren


<a name="Abschnitt-2-2-1"></a>

### Arithmetische Operatoren
  - Addition (+)

  - Subtraktion (-)
  
  - Multiplikation(*)

  - Division(/)
  
  - Modulus(%)


<a name="Abschnitt-2-2-2"></a>

### Vergleichsoperatoren
  - Gleich (==)
  
  - Ungleich(!=)
  
  - Grösser (>) / Kleiner (<)
  
  - Grösser oder gleich (>=) / Kleiner oder gleich (<=)


<a name="Abschnitt-2-2-3"></a>

### Logische Operatoren
  - UND (&&)
  
  - ODER (||)
  
  - NICHT (!)


<a name="Abschnitt-2-2-4"></a>

### Zuweisungsoperatoren
  - Zuweisung (=)
  
  - Addition-, Subtraktion-, Multiplikation-, Division- oder Modulus und Zuweisung (+=, -=, *=, /=, %=)
    

---


<a name="Abschnitt-3"></a>

## For- und While-Schlaufen


<a name="Abschnitt-3-1"></a>

### For

```python
a = 10
b = 20

for i in range(10):
  print("i ist kleiner als 10")
  i += 1
```

```java
public class Main {

    public static void main(String[] args) {
        int a = 10;
        int b = 20;
        
        for (int i = 0; i < 10; i++)
        {
            System.out.println("i ist kleiner als 10");
        }
    }
}
```


<a name="Abschnitt-3-2"></a>

### While

```python
a = 10
b = 20

while a != 10:
  print("not equal")
  a += 1
```

```java
public class Main {

    public static void main(String[] args) {
        int a = 10;
        int b = 20;

        while (a != b) 
        {
            System.out.println("not equal");
            a += 1;
        }
        
    }
}
```


---


<a name="Abschnitt-4"></a>

## Arrays (Initialisierung, Werte schreiben und lesen)


<a name="Abschnitt-4-1"></a>

### Array erstellen

```java
public class Main {

    public static void main(String[] args) {
        
        int a = 10;
        
        int[] b = new int[10]; 
//        in Liste b alle stellen haben wert 0
        
        int[] c = {1, 2, 3, 4, 5, 6};
        
        b[2] = 3;
//        index 2 in Liste b hat nun Wert 3
    }
}
```

<a name="Abschnitt-4-2"></a>

### Array durchgehen

```java
public class Main {

    public static void main(String[] args) {
        
        int a = 10;
        
        int[] b = new int[10]; 
//        in Liste b alle stellen haben wert 0
        
        int[] c = {1, 2, 3, 4, 5, 6};
        
        b[2] = 3;
//        index 2 in Liste b hat nun Wert 3
        for (int i = 0; i < b.length; i++)
        {
            System.out.println(b[i]);
        }
        
        int x;
        
        for (int x : b)
        {
            System.out.println(x);
        }
    }
}
```

---


<a name="Abschnitt-5"></a>

## ***Statische*** Klassen und Methoden

**Package A**
```java
public class test {
    public static void testmethod(String[] args) {
        System.out.println("Hallo");
    }
}
```
**Package B**
```java
import a.test;

public class Main {

    public static void main(String[] args) {
        test.testmethod();
    }
}
```

---


<a name="Abschnitt-6"></a>

## Klassen mit Getter, Setter und Konstruktor


<a name="Abschnitt-6-1"></a>

### Setter

```java
package x;
        
public class setter {
    
    private int x;
    
    public void setX(int x)
    {
        this.x = x;
    }
}
```

```java
import x.setter;
public class testset {
    
    public static void main(String[] args)  {
        setter.setX(3);
        System.out.println(x);
    }
    
    
}
```


<a name="Abschnitt-6-2"></a>

### Getter

```java
package x;

public class setter {

  private int x;

  public void setX(int x)
  {
    this.x = x;
  }
  
  public static int getX()
  {
      return x;
  }
}
```

```java
import x.setter;
public class testset {

  public static void main(String[] args)  {
    setter.setX(3);
    System.out.println(x.setter.getX());
  }


}
```

<a name="Abschnitt-6-3"></a>

### Konstruktor

```java
package constructor;

public class Car {
    
    String brand;
    
    String model;
    
    int number;
    
    public Car(String brand, String model, int number)
    {
        this.brand = brand;
        this.model = model;
        this.number = number;
    }
}
```

```java
package constructer;

public class Main{
    public static void main(String[] args)
    {
        Car auto = new Car("Honda", "S3000", 420);
    }
}
```

---


<a name="Abschnitt-7"></a>

## Enums

Enums werden benutzt um den Zustand eines Objektes zu definieren. Es ist ähnlich zu einem Boolean. Als Beispiel hat man in einem Game 
eine Wand und der Wert dieser Wand ist zum Beispiel durchgehbar oder nicht durchgehbar. Ist sie durchgehbar so kann der Spieler durch die Wand
durchlaufen ansonsten nicht. Aber anderst, wie bei einem Boolean kann man mehrere Werte haben nicht bloss true oder false, sondern die Wand könnte auch einen dritten Wert haben.

---


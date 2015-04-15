package week_03_04_2015;

 // The answer must have balanced parenthesis
interface Animal {
  public String getName();
  }

class Cat implements Animal {
  public String getName() {
    return "Cat";
  }
}

class Dog implements Animal {
  public String getName() {
    return "Dog";
  }
}

public class SA_031_Polymorphism_Poly7 {

  public static String get(Animal animal) {
    return animal.getName();
  }

  public static void main(String[] args) {
    Dog dog = new Dog();
    Cat cat = new Cat();
    System.out.println();
    assert get(dog).equals("Dog");
    assert get(cat).equals("Cat");
  }
}

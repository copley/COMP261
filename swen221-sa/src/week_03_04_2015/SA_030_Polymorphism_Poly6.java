package week_03_04_2015;


   // The answer must have balanced parenthesis

class Cat {
  public boolean isEatenBy(Object o) {
    return false;
  }
  public boolean isEatenBy(Dog o) {
	  return (!o.isLittle());
  }
}

class Dog {
  private boolean isLittle; // is a little dog?

  public Dog(boolean isLittle) {
    this.isLittle = isLittle;
  }

  public boolean isLittle() { 
	  return isLittle;
	  }

  public boolean eats(Cat c) {
    return c.isEatenBy(this);
  }
}


public class SA_030_Polymorphism_Poly6 {
  public static void main(String[] args) {
    Cat cat = new Cat();
    Dog lilDog = new Dog(true);
    Dog bigDog = new Dog(false);

    assert !lilDog.eats(cat);
    assert bigDog.eats(cat);
  }
}

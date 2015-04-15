package week_03_04_2015;

// The answer must have balanced parenthesis
// This is an example of "double dispatch"

interface Animal {
	public boolean eats(Animal o);
	public boolean isEatenBy(Animal o);
}

class Cat implements Animal {
	public boolean eats(Animal o) {
		return o.isEatenBy(this);
	}

	public boolean isEatenBy(Animal o) {
		return o instanceof Dog; 
	}
}

class Dog implements Animal {
	public boolean eats(Animal o) {
		return o.isEatenBy(this);
	}

	public boolean isEatenBy(Animal o) {
		return o instanceof Dog; 
	}
}

public class SA_029_Poly5 {
	public static void main(String[] args) {
		Animal a = new Dog();
		Animal b = new Cat();

		System.out.println(a.eats(b));
		System.out.println(b.eats(b));
//		assert a.eats(b);
//		assert !b.eats(b);
	}
}


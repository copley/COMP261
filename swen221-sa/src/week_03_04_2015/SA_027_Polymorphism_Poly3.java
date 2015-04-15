package week_03_04_2015;

// The answer must have balanced parenthesis
interface Parent {
	public String name(boolean b);
	public String name(int i);

}

class Child implements Parent {
	public String name(boolean b) {
		return "Child " + b;
	}

	public String name(int i) {
		return "Child " + i;
	}
}

public class SA_027_Polymorphism_Poly3 {
	public static void main(String[] args) {
		Parent p = new Child();
		assert p.name(1).equals("Child 1");
		assert p.name(true).equals("Child true");
	}
}


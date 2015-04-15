package week_03_04_2015;

// The answer must have balanced parenthesis
interface Parent {
	public String name(Object b);
}

class Child implements Parent {
	public String name(Object b) {
		return "Runs though Object";
	}
	public String name(String b) {
		return "Runs though String";
	}
}

public class SA_028_Poly4 {
	public static void main(String[] args) {
		Parent p = new Child();
		System.out.println(p.name("String"));
		//assert p.name("String").equals("Child String");
	}
}

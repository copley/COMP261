package week_27_03_2015;

class A {
	A(String name) {
		this.name = name;
	}

	String name;
}

class B extends A {
	B() {
		super("");
		super.name = this.toString();
	}
}

public class SA018_Inheritance_Extends2 {
	public static void main(String[] arg) {
		assert (!new B().name.equals(new B().name));
	}
}
package week_03_04_2015;

//The answer must have balanced parentesis
class A {
}

class B extends A {
}

public class SA_032_Polymorphism_ManyM1 {
	public static boolean m(A p1) {
		return false;
	}

	public static boolean m(String p1) {
		return true;
	}

	public static boolean m(B p1) {
		return false;
	}

	public static void main(String [] arg){
		m("Something");
		assert m("Something");
  }
}

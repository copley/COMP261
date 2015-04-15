package week_03_04_2015;

//The answer must have balanced parenthesis
class A {
}

class B extends A {
}

public class SA_035_Polymorphism_ManyMF {
	public static boolean m(A p1, B p2) {
		return false;
	}

	public static boolean m(A p1, A p2) {
		return true;
	}

	public static boolean m(B p1, B p2) {
		return false;
	}

	public static boolean f(A p1, A p2) {
		return false;
	}

	public static boolean f(B p1, A p2) {
		return true;
	}

	public static boolean f(B p1, B p2) {
		return false;
	}

	public static void main(String [] arg){
    assert m(new B(), new A());
    assert f(new B(), new A());
  }
}

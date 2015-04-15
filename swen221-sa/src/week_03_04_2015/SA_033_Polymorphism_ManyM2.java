package week_03_04_2015;

//The answer must have balanced parenthesis

class A {
}

class B extends A {
}

public class SA_033_Polymorphism_ManyM2 {
	public static boolean m(A p1, B p2) {
		return false;
	}

	public static boolean m(A p1, A p2) {
		return true;
	}

	public static boolean m(B p1, B p2) {
		return false;
	}

	public static void main(String [] arg){
    assert m(new A(),new A());
  }
}

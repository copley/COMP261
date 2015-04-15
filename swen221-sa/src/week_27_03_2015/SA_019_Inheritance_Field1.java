package week_27_03_2015;

class A {

	private int f = 0;

	public void setF(int f) {
	  this.f = f;
			  }

	public boolean checkF(int expected) {
		return f == expected;
	}
}

class B extends A {

	public void m() {
		setF(3);
		assert checkF(3);
		setF(8);
		assert checkF(8);
	}
}

public class SA019_Inheritance_Field1 {
	public static void main(String[] arg) {
		new B().m();
	}
}
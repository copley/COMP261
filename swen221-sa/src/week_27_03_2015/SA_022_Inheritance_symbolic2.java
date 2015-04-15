package week_27_03_2015;

abstract class Parent {
	int x = 100;

	Parent(int x) {
		this.x = x;
	}

	abstract int m(int x);

	static Parent k(Parent x) {
		return x;
	}

	int f(int x) {
		return m(x + 1);
	}
}

class Heir extends Parent {

	static int x = 200;

	Heir(int x) {
		super(x + 1);
	}

	Heir() {
		this(3);
	}

	int m(int x) {
		return x + 2;
	}

	int n(int x) {
		return this.x + x;
	}
}

public class SA022_Inheritance_symbolic2 {
	public static void main(String[] arg) {
		Parent p = new Heir();
		Heir h = new Heir();
		new Heir().x = 5;
		System.out.println("########");
		System.out.println(h.n(p.x));
		// assert(h.n(p.x) == h.n (4) );
	}
}
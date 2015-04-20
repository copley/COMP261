public class Test {

	public static void main(String[] args) {
		Vector3D vector = new Vector3D(-10f, 1f, 1f);
		Transform doubler = Transform.newTranslation(10f, 20f, 0f);
//		Transform doubler = Transform.newScale(2f, 2f, 2f);
		Vector3D newVector = doubler.multiply(vector);
		System.out.println(newVector); // output is Vect:(2.0,2.0,2.0)
	}
	
}

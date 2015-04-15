public class Exercise {

	public static int m1() {
		return 3;
		try {
//			if (true) {
//			}
		} catch (Exception e) {
			return 2;
		} 
		finally {
			return 200;
		}
	}

//	public static int m2() {
//		try {
//			try {
//				if (true) {
//					return 3;
//				}
//			} catch (Error e) {
//				return 2;
//			}
//			return 2;
//		} catch (Throwable t) {
//			return 10;
//		}
//	}

	public static void main(String[] arg) {
		// assert (m1() != m2()) : "assertion:" + m1() + "!=" + m2();
		System.out.println(m1());
	}
}
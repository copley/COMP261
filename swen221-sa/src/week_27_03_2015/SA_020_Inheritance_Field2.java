package week_27_03_2015;

class A{
  
	private int f=0;
  
  public void setF(int _f){
	  f=_f;
	  }
  public boolean checkF(int expected) {
	  return f==expected;
	  }
}

class B extends A{
  
	public void setF(int f){
	  super.setF(-f);
  }
  
	public void m(){
		setF(3);
		assert checkF(-3);
		setF(8);
		assert checkF(-8);
  }
}

public class SA020_Inheritance_Field2{
  public static void main(String [] arg){
    new B().m();
  }
}
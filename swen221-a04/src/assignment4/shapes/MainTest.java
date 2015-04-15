package assignment4.shapes;

public class MainTest {

	public static void main(String[] args) {
//		Interpreter interp = new Interpreter("x = [200,0,500,500]\ny = [0,200,400,400]\ndraw x #00ff00\ndraw y #0000ff\ny = y & x\ndraw y #ff0000");
//		Interpreter interp = new Interpreter("x = [200,200,400,400]\nx = ([0,0,300,300] + [200,200,400,400])\ndraw x #00ff00\n");
//		Interpreter interp = new Interpreter("x = [200,0,500,500]\ny = [0,200,400,400]\ndraw x #00ff00\n");
//		Interpreter interp = new Interpreter("x = [2,2,4,4]\ndraw x #0000ff\n");
		Interpreter interp = new Interpreter("x = [20,0,50,50]\ny = [0,20,40,50]\ny = y - x\ndraw y #ff0000\n");
//		Interpreter interp = new Interpreter("x = [20,0,50,50]\ndraw x #00ff00\nx = [0,20,40,40]\ndraw x #0000ff\nx =x&[20,0,50,50]\ndraw x #ff0000");
//		Interpreter interp = new Interpreter("x = [2,0,5,5]\ndraw x #00ff00\nx = [0,2,4,4]\ndraw x #0000ff\nx =x&[2,0,5,5]\ndraw x #ff0000");
		Canvas canvas = interp.run();
           canvas.show();
     } 
}

package expression;


import interfaces.RobotExpNode;
import java.util.Scanner;
import parser.Parser;
import robot.Robot;

public class NumberNode implements RobotExpNode {

	private int value;
	
	public NumberNode(int value){
		this.value = value;
	}
	
	public NumberNode(){
		this.value = -1;
	}

	@Override
	public RobotExpNode parse(Scanner scan) {

		// "NUM"
		if (scan.hasNext(Parser.NUM)) {
			String s = scan.next(Parser.NUM);
			value = Integer.parseInt(s);
		} else {
			Parser.fail("FAIL: Expecting " + Parser.NUM.toString(), scan);
		}
		return this;
	}

	@Override
	public Integer evaluate(Robot robot) {

		return value;

	}

	@Override
	public String toString() {

		return Integer.toString(value);
	}
}

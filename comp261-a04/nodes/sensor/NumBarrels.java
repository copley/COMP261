package sensor;

import java.util.Scanner;
import parser.Parser;
import robot.Robot;
import interfaces.RobotExpNode;
import interfaces.RobotSensNode;

public class NumBarrels implements RobotSensNode {

	@Override
	public RobotExpNode parse(Scanner scan) {
	
		// "numBarrels"
		if (!Parser.gobble(Parser.NUMBARRELS, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.NUMBARRELS.toString(), scan);
		}
	
		return this;
	}

	@Override
	public int evaluate(Robot robot) {

		return robot.numBarrels();
	}

	@Override
	public String toString() {

		return Parser.NUMBARRELS.toString();
	}
}
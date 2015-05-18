package sensor;

import java.util.Scanner;
import parser.Parser;
import robot.Robot;
import interfaces.RobotExpNode;
import interfaces.RobotSensNode;

public class OpponentLR implements RobotSensNode {

	@Override
	public RobotExpNode parse(Scanner scan) {
	
		// "oppFB"
		if (!Parser.gobble(Parser.OPPLR, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.OPPLR.toString(), scan);
		}
	
		return this;
	}

	@Override
	public Integer evaluate(Robot robot) {

		return robot.getOpponentLR();
	}

	@Override
	public String toString() {

		return Parser.OPPLR.toString();
	}
}

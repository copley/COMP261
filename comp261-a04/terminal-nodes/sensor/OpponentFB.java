package sensor;

import java.util.Scanner;
import parser.Parser;
import robot.Robot;
import interfaces.RobotExpNode;
import interfaces.RobotSensNode;

public class OpponentFB implements RobotSensNode {

	private int value = -1;

	@Override
	public int evaluate(Robot robot) {

		return robot.getOpponentFB();
	}

	@Override
	public RobotExpNode parse(Scanner scan) {

		// "oppFB"
		if (!Parser.gobble(Parser.OPPFB, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.OPPFB.toString(), scan);
		}

		return this;
	}

	@Override
	public int getValue() {

		return value;
	}

	@Override
	public String toString() {

		return Parser.OPPFB.toString();
	}
}
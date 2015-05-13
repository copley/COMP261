package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import parser.Parser;
import robot.Robot;

public class BlockNode implements RobotNode {

	List<RobotNode> blockNodes = new ArrayList<RobotNode>();

	@Override
	public void execute(Robot robot) {

		for (RobotNode n : blockNodes) {
			n.execute(robot);
		}
	}

	@Override
	public RobotNode parse(Scanner scan) {

		RobotNode statement = null;
		while (!scan.hasNext(Parser.CLOSEBRACE)) {
			if (scan.hasNext()) { // check that there is at least one instruction
				statement = new StatmentNode();
				statement.parse(scan);
				blockNodes.add(statement);
			} else {
				Parser.fail("Expecting an instruciton", scan);
			}
		}
		return statement;
	}

	@Override
	public String toString() {

		String s = "{";
		for (RobotNode n : blockNodes) {
			s += " " + n.toString();
		}
		return (s + " }\n");
	}
}

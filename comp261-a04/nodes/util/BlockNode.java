package util;

import interfaces.RobotNode;
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
		
		// "{"
		if (!Parser.gobble(Parser.OPENBRACE, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.OPENBRACE.toString(), scan);
		}

		// "STMT+"
		while (!scan.hasNext(Parser.CLOSEBRACE)) {
			if (scan.hasNext()) { // check that there is at least one instruction
				statement = new StatmentNode();
				statement.parse(scan);
				blockNodes.add(statement);
			} else {
				Parser.fail("Expecting 1+ instrucitons", scan);
			}
		}
		
		// "}"
		if (!Parser.gobble(Parser.CLOSEBRACE, scan)) {
			Parser.fail("FAIL: Expecting dsds" + Parser.CLOSEBRACE.toString(), scan);
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

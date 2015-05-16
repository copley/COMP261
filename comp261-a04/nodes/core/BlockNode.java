package core;

import interfaces.RobotProgramNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import parser.Parser;
import robot.Robot;

public class BlockNode implements RobotProgramNode {

	private List<RobotProgramNode> blockNodes = new ArrayList<RobotProgramNode>();

	@Override
	public RobotProgramNode parse(Scanner scan) {
		
		RobotProgramNode statement = null;

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

		return this;
	}

	@Override
	public void execute(Robot robot) {

		for (RobotProgramNode n : blockNodes) {
			n.execute(robot);
		}
	}

	@Override
	public String toString() {

		String s = "{";
		for (RobotProgramNode n : blockNodes) {
			s += " " + n.toString();
		}
		return (s + " }\n");
	}
}

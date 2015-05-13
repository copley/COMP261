package logic;

import java.util.Scanner;
import parser.Parser;
import util.BlockNode;
import util.RobotNode;
import robot.Robot;

public class LoopNode implements RobotNode {

	RobotNode blockNode = null;

	@Override
	public void execute(Robot robot) {

		while (true) {
			blockNode.execute(robot);
		}
	}

	@Override
	public RobotNode parse(Scanner scan) {

		// Check for "loop"
		if (!Parser.gobble(Parser.LOOP, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.LOOP.toString(), scan);
		}
		// Check for open braces
		if (!Parser.gobble(Parser.OPENBRACE, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.OPENBRACE.toString(), scan);
		}
		// Read the block to loop
		blockNode = new BlockNode();
		blockNode.parse(scan);
		// Check for close braces
		if (!Parser.gobble(Parser.CLOSEBRACE, scan)) {
			Parser.fail("FAIL: Expecting dsds" + Parser.CLOSEBRACE.toString(), scan);
		}
		// Parse the node and return
		return blockNode;
	}

	@Override
	public String toString() {
		String s = blockNode.toString();
		return String.format("loop %s",s);
	}
}

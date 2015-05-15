package logic;

import interfaces.RobotNode;
import java.util.Scanner;
import parser.Parser;
import util.BlockNode;
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

		// "loop"
		if (!Parser.gobble(Parser.LOOP, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.LOOP.toString(), scan);
		}
		
		// "BLOCK"
		blockNode = new BlockNode();
		blockNode.parse(scan);
		return blockNode;
	}

	@Override
	public String toString() {
		String s = blockNode.toString();
		return String.format("loop %s",s);
	}
}

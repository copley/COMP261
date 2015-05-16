package logic;

import interfaces.RobotProgramNode;
import java.util.Scanner;
import core.BlockNode;
import parser.Parser;
import robot.Robot;

public class LoopNode implements RobotProgramNode {

	RobotProgramNode blockNode = null;

	@Override
	public void execute(Robot robot) {

		while (true) {
			blockNode.execute(robot);
		}
	}

	@Override
	public RobotProgramNode parse(Scanner scan) {

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

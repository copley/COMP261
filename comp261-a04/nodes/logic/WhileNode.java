package logic;

import interfaces.RobotProgramNode;
import java.util.Scanner;
import condition.Condition;
import core.BlockNode;
import parser.Parser;
import robot.Robot;

public class WhileNode implements RobotProgramNode {

	private Condition conditionNode;
	private BlockNode blockNode;

	@Override
	public RobotProgramNode parse(Scanner scan) {

		// "while"
		if (!Parser.gobble(Parser.WHILE, scan)) {
			Parser.fail("FAIL: Expecting: " + Parser.WHILE.toString(), scan);
		}

		// "("
		if (scan.hasNext(Parser.OPENP)) {
			Parser.gobble(Parser.OPENP, scan);

			// "COND"
			if (scan.hasNext(Parser.CONDITION)) {
				conditionNode = new Condition();
				conditionNode.parse(scan);
			} else {
				Parser.fail("Fail: expecting" + Parser.CONDITION, scan);
			}

			// ")"
			if (scan.hasNext(Parser.CLOSEP)) {
				Parser.gobble(Parser.CLOSEP, scan);
			}

			// "BLOCK"
			blockNode = new BlockNode();
			blockNode.parse(scan);

		}
		return this;
	}

	@Override
	public void execute(Robot robot) {

		while (true) {
			if (conditionNode.evaluate(robot)) {
				blockNode.execute(robot);
			} else {
				return;
			}
		}
	}

	@Override
	public String toString() {

		return "while(" + conditionNode.toString() + ") " + blockNode.toString();
	}
}

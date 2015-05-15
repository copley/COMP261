package util;

import interfaces.RobotProgramNode;
import java.util.Scanner;
import nonTerminalNodes.ActionNode;
import logic.IfNode;
import logic.LoopNode;
import logic.WhileNode;
import parser.Parser;
import robot.Robot;

public class StatmentNode implements RobotProgramNode {

	private RobotProgramNode nextNode = null;

	@Override
	public void execute(Robot robot) {

		nextNode.execute(robot);
	}

	@Override
	public RobotProgramNode parse(Scanner scan) {

		if (scan.hasNext(Parser.ACTION)) {
			nextNode = new ActionNode();
		} else if (scan.hasNext(Parser.LOOP)) {
			nextNode = new LoopNode();
		} else if (scan.hasNext(Parser.IF)) {
			nextNode = new IfNode();
		} else if (scan.hasNext(Parser.WHILE)) {
			nextNode = new WhileNode();
			// } else if (scan.hasNext(Parser.ASSGN)){ // not sure htis is right .. double check if instead should be VAR = EXP
			// nextNode = new AssgnNode();
		} else {
			Parser.fail("Unkown statement", scan);
		}
		// finally, parse the node
		nextNode.parse(scan);
		return this;
	}

	public String toString() {
		return nextNode.toString();
	}
}

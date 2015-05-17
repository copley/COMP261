package core;

import interfaces.RobotProgramNode;
import java.util.Scanner;
import action.Action;
import logic.IfNode;
import logic.LoopNode;
import logic.WhileNode;
import parser.Parser;
import robot.Robot;

public class StatmentNode implements RobotProgramNode {

	private RobotProgramNode nextNode = null;

	@Override
		public RobotProgramNode parse(Scanner scan) {
	
			if (scan.hasNext(Parser.ACTION)) {
				nextNode = new Action();
			} else if (scan.hasNext(Parser.LOOP)) {
				nextNode = new LoopNode();
			} else if (scan.hasNext(Parser.IF)) {
				nextNode = new IfNode();
			} else if (scan.hasNext(Parser.WHILE)) {
				nextNode = new WhileNode();
			} else if (scan.hasNext(Parser.VAR)){ 
				 nextNode = new AssgnNode();
			} else {
				Parser.fail("Unkown statement", scan);
			}
			nextNode.parse(scan);
			return this;
		}

	@Override
	public void execute(Robot robot) {

		nextNode.execute(robot);
	}

	public String toString() {

		return nextNode.toString();
	}
}

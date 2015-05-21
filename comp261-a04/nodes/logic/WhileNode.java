package logic;

import interfaces.RobotExpNode;
import interfaces.RobotProgramNode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import condition.Condition;
import core.BlockNode;
import core.ProgramNode;
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

//		/*
		// CHALLENGE II: Makes a copy of the top layer of the stack to localise any variable inside the block
		Map<String, RobotExpNode> tmpVars = ProgramNode.varStack.peek();
		Map<String, RobotExpNode> tmpVarsCopy = new HashMap<String, RobotExpNode>();
		for (String key : tmpVars.keySet()) {
			tmpVarsCopy.put(key, tmpVars.get(key));
		}
		ProgramNode.varStack.push(tmpVarsCopy);
//		*/
		
		
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
			
//			/*
			// CHALLENGE II: Remove the top layer of the stack, as the block ends here
			ProgramNode.varStack.pop();
//			*/

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

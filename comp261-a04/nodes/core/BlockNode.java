package core;

import interfaces.RobotExpNode;
import interfaces.RobotProgramNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import expression.VarNode;
import parser.Parser;
import robot.Robot;

public class BlockNode implements RobotProgramNode {

	private List<RobotProgramNode> blockNode = new ArrayList<RobotProgramNode>();

	@Override
	public RobotProgramNode parse(Scanner scan) {

		RobotProgramNode statement = null;

		/*
		// CHALLENGE II: Makes a copy of the top layer of the stack to localise any variable inside the block
		Map<VarNode, RobotExpNode> tmpVars = ProgramNode.varStack.peek();
		Map<VarNode, RobotExpNode> tmpVarsCopy = new HashMap<VarNode, RobotExpNode>();
		for (VarNode key : tmpVars.keySet()) {
			tmpVarsCopy.put(key, tmpVars.get(key));
		}
		ProgramNode.varStack.push(tmpVarsCopy);
		*/

		// "{"
		if (!Parser.gobble(Parser.OPENBRACE, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.OPENBRACE.toString(), scan);
		}

		// "STMT+"
		while (!scan.hasNext(Parser.CLOSEBRACE)) {
			if (scan.hasNext()) { // check that there is at least one instruction
				statement = new StatmentNode();
				statement.parse(scan);
				blockNode.add(statement);
			} else {
				Parser.fail("Expecting 1+ instrucitons", scan);
			}
		}

		// "}"
		if (!Parser.gobble(Parser.CLOSEBRACE, scan)) {
			Parser.fail("FAIL: Expecting dsds" + Parser.CLOSEBRACE.toString(), scan);
		}

		/*
		// CHALLENGE II: Remove the top layer of the stack, as the block ends here
		ProgramNode.varStack.pop();
		*/

		return this;
	}

	@Override
	public void execute(Robot robot) {

		for (RobotProgramNode n : blockNode) {
			n.execute(robot);
		}
	}

	@Override
	public String toString() {

		String s = "{";
		for (RobotProgramNode n : blockNode) {
			s += " " + n.toString();
		}
		return (s + " }\n");
	}
}

package core;

import interfaces.RobotExpNode;
import interfaces.RobotProgramNode;
import java.util.Map;
import java.util.Scanner;
import parser.Parser;
import expression.Expression;
import expression.NumberNode;
import expression.VarNode;
import robot.Robot;

public class AssgnNode implements RobotProgramNode {

	private RobotExpNode expNode = null;
	private VarNode variable = null;

	@Override
	public RobotProgramNode parse(Scanner scan) {

		// "VAR"
		variable = new VarNode();
		variable.parse(scan);
//		CHALLENGE: variable.parseInit(scan);

		// "="
		if (!Parser.gobble(Parser.ASSIGN, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.ASSIGN.toString(), scan);
		}

		// "EXP"
		expNode = new Expression();
		expNode.parse(scan);

		// ";"
		if (!Parser.gobble(Parser.SEMICOL, scan)) {
			Parser.fail("Expecting ;", scan);
		}
		
		/*
		// CHALLENGE: Add a variable to the top layer of the stack
		Map<VarNode, RobotExpNode> tmpMap = ProgramNode.varStack.pop();
		if (tmpMap.containsKey(variable)){ // if key is present -> remove and add new
			System.out.println("KEY PRESENT, OVERWIRITNG");
			tmpMap.remove(variable);
		} else {
			System.out.println("NEW KEY");
			tmpMap.put(variable, expNode);
		}
		ProgramNode.varStack.push(tmpMap);
		*/
		return this;
	}

	@Override
	public void execute(Robot robot) {
		
		ProgramNode.variables.put(variable.toString(), new NumberNode(expNode.evaluate(robot)));
		
		
//		if (ProgramNode.variables.containsKey(variable)) {
//			ProgramNode.variables.remove(variable);
//			ProgramNode.variables.put(variable.toString(), expNode);
//		} else {
//			ProgramNode.variables.put(variable.toString(), expNode);
//		}
//		System.out.println(expNode.toString());
		// ?
	}

	@Override
	public String toString() {

		return variable.toString() + " = " + expNode.toString() + ";";
	}
}

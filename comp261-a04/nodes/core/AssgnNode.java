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
		
		// CHALLENGE I:
		variable.parse(scan);

		/*
		// CHALLENGE II : 
		variable.parseInit(scan);
		 */

		// "="
		if (!Parser.gobble(Parser.ASSIGN, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.ASSIGN.toString(), scan);
		}
		

		// "EXP"
		// if expression matches and existing -> re-assign /// maybe this is redundant, since we can overwrite the same key
		if (ProgramNode.varStack.peek().containsKey(variable)) {
			Map<String, RobotExpNode> tmpMap = ProgramNode.varStack.peek();
			expNode = new Expression();
			tmpMap.put(variable.toString(), expNode.parse(scan));
			ProgramNode.varStack.pop();
			ProgramNode.varStack.push(tmpMap);
		} else { // if is a new expression ... 
			System.out.println("NEW EXPRESSION");
			Map<String, RobotExpNode> tmpMap = ProgramNode.varStack.peek();
			expNode = new Expression();
			tmpMap.put(variable.toString(), expNode.parse(scan));
			ProgramNode.varStack.pop();
			ProgramNode.varStack.push(tmpMap);
		}

		// ";"
		if (!Parser.gobble(Parser.SEMICOL, scan)) {
			Parser.fail("Expecting ;", scan);
		}
		
		return this;
	}

	@Override
	public void execute(Robot robot) {
		
		// CHALLENGE I:
		ProgramNode.variables.put(variable.toString(), new NumberNode(expNode.evaluate(robot)));

		/*
		// CHALLENGE II: take variable out of stack, execute it, and put back
		if (ProgramNode.varStack.size() > 0) {
			Map<String, RobotExpNode> tmpMap = ProgramNode.varStack.peek();
			if (tmpMap.containsKey(variable.toString())){ // if key is present
				System.out.println("KEY REASSIGNMENT");
				RobotExpNode tmpExp = expNode;
				//			RobotExpNode tmpExp = tmpMap.get(variable.toString());
				RobotExpNode evalExp = new NumberNode(tmpExp.evaluate(robot));
				tmpMap.put(variable.toString(), evalExp);
				ProgramNode.varStack.pop();
				ProgramNode.varStack.push(tmpMap);
			} else {
				System.out.println("NEW KEY FOUND");
				// new key ... adding new key to stack
				RobotExpNode tmpExp = expNode;
				RobotExpNode evalExp = new NumberNode(tmpExp.evaluate(robot));
				tmpMap.put(tmpExp.toString(), evalExp);
				ProgramNode.varStack.pop();
				ProgramNode.varStack.push(tmpMap);
			}
		} else {
			System.out.println("EMPTY STACK");
		}
		*/
		
	}
		

	@Override
	public String toString() {

		return variable.toString() + " = " + expNode.toString() + ";";
	}
}

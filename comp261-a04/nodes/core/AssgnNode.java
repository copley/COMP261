package core;

import interfaces.RobotExpNode;
import interfaces.RobotProgramNode;
import java.util.Scanner;
import parser.Parser;
import expression.Expression;
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
		
		// store variable in program node
		ProgramNode.variables.put(variable, expNode);
		
		return this;
	}

	@Override
	public void execute(Robot robot) {

		// ?
	}

	@Override
	public String toString() {

		return variable.toString() + " = " + expNode.toString() + ";";
	}
}

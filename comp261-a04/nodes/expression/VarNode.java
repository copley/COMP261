package expression;

import interfaces.RobotExpNode;
import java.util.Scanner;
import core.ProgramNode;
import parser.Parser;
import parser.ParserFailureException;
import robot.Robot;

public class VarNode implements RobotExpNode {

	private String varName = null;

	@Override
	public RobotExpNode parse(Scanner scan) {

		if (scan.hasNext(Parser.VAR)) {
			varName = scan.next();
		} else {
			Parser.fail("Variable name not valid", scan);
		}
		return null;
	}

	@Override
	public int evaluate(Robot robot) {

		if (ProgramNode.variables.get(this) != null) {
			return ProgramNode.variables.get(this).evaluate(robot);
		} else {
			throw new ParserFailureException("Variable must be declared before usage");
		}
	}

	@Override
	public String toString() {

		return varName;
	}

}

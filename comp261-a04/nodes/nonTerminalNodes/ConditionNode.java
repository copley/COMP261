package nonTerminalNodes;

import interfaces.RobotCondNode;
import java.util.Scanner;
import cond.AndNode;
import cond.EqualNode;
import cond.GreaterNode;
import cond.LessNode;
import cond.NotNode;
import cond.OrNode;
import parser.Parser;
import robot.Robot;

public class ConditionNode implements RobotCondNode {

	RobotCondNode conditionNode = null;

	@Override
	public boolean evaluate(Robot robot) {
		return conditionNode.evaluate(robot);
	}

	@Override
	public RobotCondNode parse(Scanner scan) {

		if (scan.hasNext(Parser.GREATERTHEN)) {
			conditionNode = new GreaterNode();
		} else if (scan.hasNext(Parser.LESSTHEN)) {
			conditionNode = new LessNode();
		} else if (scan.hasNext(Parser.EQUAL)) {
			conditionNode = new EqualNode();
		} else if (scan.hasNext(Parser.AND)) {
			conditionNode = new AndNode();
		} else if (scan.hasNext(Parser.OR)) {
			conditionNode = new OrNode();
		} else if (scan.hasNext(Parser.NOT)) {
			conditionNode = new NotNode();
		}
		conditionNode.parse(scan);
		return conditionNode;
	}

	@Override
	public String toString(){
		return conditionNode.toString();
	}
}


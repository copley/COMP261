package logic;

import interfaces.RobotProgramNode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import condition.Condition;
import core.BlockNode;
import parser.Parser;
import robot.Robot;

public class IfNode implements RobotProgramNode {

	Condition ifCondition = null;
	// List<Condition> elifConditions = new ArrayList<Condition>();;
	RobotProgramNode ifBlockNode = null;
	RobotProgramNode elseBlockNode = null;
	Map<Condition, BlockNode> elifConditions = new HashMap<Condition, BlockNode>();

	@Override
	public RobotProgramNode parse(Scanner scan) {

		// "if"
		if (!Parser.gobble(Parser.IF, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.IF.toString(), scan);
		}

		// "("
		if (!Parser.gobble(Parser.OPENP, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.OPENP.toString(), scan);
		}

		// "COND"
		ifCondition = new Condition();
		ifCondition.parse(scan);

		// ")"
		if (!Parser.gobble(Parser.CLOSEP, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.CLOSEP.toString(), scan);
		}

		// "BLOCK"
		ifBlockNode = new BlockNode();
		ifBlockNode.parse(scan);

		// "elif"
		while (true) {
			if (scan.hasNext(Parser.ELIF)) {
				if (!Parser.gobble(Parser.ELIF, scan)) {
					Parser.fail("FAIL: Expecting " + Parser.ELIF.toString(), scan);
				}

				// "("
				if (!Parser.gobble(Parser.OPENP, scan)) {
					Parser.fail("FAIL: Expecting " + Parser.OPENP.toString(), scan);
				}

				// "COND"
				Condition elifCondition = new Condition();
				elifCondition.parse(scan);

				// ")"
				if (!Parser.gobble(Parser.CLOSEP, scan)) {
					Parser.fail("FAIL: Expecting " + Parser.CLOSEP.toString(), scan);
				}

				// "BLOCK"
				BlockNode elifBlockNode = new BlockNode();
				elifBlockNode.parse(scan);

				elifConditions.put(elifCondition, elifBlockNode); // add elif condition and block to list

			} else {
				break;
			}
		}

		// "else"
		if (scan.hasNext(Parser.ELSE)) {
			if (!Parser.gobble(Parser.ELSE, scan)) {
				Parser.fail("FAIL: Expecting " + Parser.ELSE.toString(), scan);
			}

			// "BLOCK"
			elseBlockNode = new BlockNode();
			elseBlockNode.parse(scan);
		}
		return this;
	}

	@Override
	public void execute(Robot robot) {

		if (ifCondition != null) {
			if (ifCondition.evaluate(robot)) {
				ifBlockNode.execute(robot);
			} else if (elifConditions.size() > 0) {
				boolean executed = false;
				for (Condition n : elifConditions.keySet()) {
					n.evaluate(robot);
					if (n.evaluate(robot)) {
						elifConditions.get(n).execute(robot);
						executed = true;
						break;
					}
				}
				if (elifConditions != null && !executed) {
					elseBlockNode.execute(robot);
				}
			} else if (elseBlockNode != null) {
				elseBlockNode.execute(robot);

			}
		}
	}

	@Override
	public String toString() {

		String s = "if(" + ifCondition.toString() + ") " + ifBlockNode.toString();
		for (Condition n : elifConditions.keySet()) {
			s += "elif(" + n.toString() + ") " + elifConditions.get(n).toString();
		}
		if (elseBlockNode != null) {
			s += "else " + elseBlockNode.toString();
		}
		return s;
	}
}

package util;

import interfaces.RobotNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import robot.Robot;

public class ProgramNode implements RobotNode {

	private List<RobotNode> statements = new ArrayList<RobotNode>();

	@Override
	public void execute(Robot robot) {

		for (RobotNode node : statements) {
			node.execute(robot);
		}
	}

	@Override
	public RobotNode parse(Scanner scan) {

		StatmentNode statement;
		while (scan.hasNext()) {
			statement = new StatmentNode();
			statements.add(statement.parse(scan));
		}
		return this;
	}

	public String toString() {

		String s = "";
		for (RobotNode n : statements) {
			s += n.toString() + '\n';
		}
		return s;
	}
}
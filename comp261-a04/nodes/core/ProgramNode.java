package core;

import interfaces.RobotExpNode;
import interfaces.RobotProgramNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import robot.Robot;

public class ProgramNode implements RobotProgramNode {

	private List<RobotProgramNode> statements = new ArrayList<RobotProgramNode>();

	// CHALLENGE I:
	public static Map<String, RobotExpNode> variables = new HashMap<String, RobotExpNode>();
	
	// CHALLENGE II:
	public static Stack<Map<String, RobotExpNode>> varStack = new Stack<Map<String, RobotExpNode>>();

	@Override
	public RobotProgramNode parse(Scanner scan) {
		
		// CHALLENGE II : Add a stack layer for initialization
		varStack.push(new HashMap<String, RobotExpNode>());
		
		StatmentNode statement;
		while (scan.hasNext()) {
			statement = new StatmentNode();
			statements.add(statement.parse(scan));
		}
		return this;
	}

	@Override
	public void execute(Robot robot) {
		for (RobotProgramNode node : statements) {
			node.execute(robot);
		}
	}

	public String toString() {

		String s = "";
		for (RobotProgramNode n : statements) {
			s += n.toString() + '\n';
		}
		return s;
	}
}
package core;

import interfaces.RobotExpNode;
import interfaces.RobotProgramNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import expression.VarNode;
import robot.Robot;

public class ProgramNode implements RobotProgramNode {

	private List<RobotProgramNode> statements = new ArrayList<RobotProgramNode>();
//	public static Map<VarNode, RobotExpNode> variables = new HashMap<VarNode, RobotExpNode>();
	public static Map<String, RobotExpNode> variables = new HashMap<String, RobotExpNode>();

//	public static List<VarNode> curVarList = new ArrayList<VarNode>();
	// CHALLENGE II :
//	public static Stack<Map<VarNode, RobotExpNode>> varStack = new Stack<Map<VarNode, RobotExpNode>>();

	@Override
	public RobotProgramNode parse(Scanner scan) {
		
		// Add a stack layer for initialization
//		varStack.push(new HashMap<VarNode, RobotExpNode>());
		
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
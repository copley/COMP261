package nonTerminalNodes;

import interfaces.RobotProgramNode;
import java.util.Scanner;
import action.MoveNode;
import action.ShieldOffNode;
import action.ShieldOnNode;
import action.TakeFuelNode;
import action.TurnAroundNode;
import action.TurnLNode;
import action.TurnRNode;
import action.WaitNode;
import parser.Parser;
import robot.Robot;

public class ActionNode implements RobotProgramNode {

	RobotProgramNode actionNode = null;

	@Override
	public void execute(Robot robot) {

		actionNode.execute(robot);
	}

	@Override
	public RobotProgramNode parse(Scanner scan) {

		// creates an action node then parse it to the scnner
		if (scan.hasNext(Parser.MOVE)) {
			actionNode = new MoveNode();
		} else if (scan.hasNext(Parser.TAKEFUEL)) {
			actionNode = new TakeFuelNode();
		} else if (scan.hasNext(Parser.TURNL)) {
			actionNode = new TurnLNode();
		} else if (scan.hasNext(Parser.TURNR)) {
			actionNode = new TurnRNode();
		} else if (scan.hasNext(Parser.WAIT)) {
			actionNode = new WaitNode();
		} else if (scan.hasNext(Parser.TURNAROUND)) {
			actionNode = new TurnAroundNode();
		} else if (scan.hasNext(Parser.SHIELDON)) {
			actionNode = new ShieldOnNode();
		} else if (scan.hasNext(Parser.SHIELDOFF)) {
			actionNode = new ShieldOffNode();
		} else {
			Parser.fail("Invalid action node", scan);
		}
		actionNode.parse(scan); // parse then gobble ";"
		if (!Parser.gobble(Parser.SEMICOL, scan)) {
			Parser.fail("Expecting ;", scan);
		}
		return actionNode;
	}
	
	public String toString(){
		String s = actionNode.toString();
		return s;
	}
	
}

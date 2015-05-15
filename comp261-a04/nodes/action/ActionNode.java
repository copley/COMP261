package action;

import interfaces.RobotNode;
import java.util.Scanner;
import parser.Parser;
import robot.Robot;

public class ActionNode implements RobotNode {

	RobotNode nextNode = null;

	@Override
	public void execute(Robot robot) {

		nextNode.execute(robot);
	}

	@Override
	public RobotNode parse(Scanner scan) {

		// creates an action node then parse it to the scnner
		if (scan.hasNext(Parser.MOVE)) {
			nextNode = new MoveNode();
		} else if (scan.hasNext(Parser.TAKEFUEL)) {
			nextNode = new TakeFuelNode();
		} else if (scan.hasNext(Parser.TURNL)) {
			nextNode = new TurnLNode();
		} else if (scan.hasNext(Parser.TURNR)) {
			nextNode = new TurnRNode();
		} else if (scan.hasNext(Parser.WAIT)) {
			nextNode = new WaitNode();
		} else if (scan.hasNext(Parser.TURNAROUND)) {
			nextNode = new TurnAroundNode();
		} else if (scan.hasNext(Parser.SHIELDON)) {
			nextNode = new ShieldOnNode();
		} else if (scan.hasNext(Parser.SHIELDOFF)) {
			nextNode = new ShieldOffNode();
		} else {
			Parser.fail("Invalid action node", scan);
		}
		nextNode.parse(scan); // parse then gobble ";"
		if (!Parser.gobble(Parser.SEMICOL, scan)) {
			Parser.fail("Expecting ;", scan);
		}
		return this;
	}
	
	public String toString(){
		String s = nextNode.toString();
		return s;
	}
	
}

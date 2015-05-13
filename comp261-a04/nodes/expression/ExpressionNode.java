package expression;

import java.util.Scanner;
import parser.Parser;
import robot.Robot;
import sensor.SensorNode;
import util.RobotNode;


public class ExpressionNode implements RobotNode {

	RobotExpNode nextNode = null;
	
	@Override
	public void execute(Robot robot) {
		
	}

	@Override
	public RobotNode parse(Scanner scan) {
		if (scan.hasNext(Parser.NUMPAT)){
			nextNode = new NumberNode();
		} else if (scan.hasNext(Parser.SENSOR)){
			nextNode = new SensorNode();
		} else if (scan.hasNext(Parser.VAR)){
			nextNode = new VarNode();
		} else if (scan.hasNext(Parser.OPERATION)){
			nextNode = new OpNode();
		} else {
			Parser.fail("Invalid action node", scan);
		} 
		nextNode.parse(scan);
		return this;
	}

	public String getValue() {
		return nextNode.getValue();
	}
	
	@Override
	public String toString(){
		return nextNode.toString();
	}
	
}

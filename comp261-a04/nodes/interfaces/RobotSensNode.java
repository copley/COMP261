package interfaces;

/**
 * This interface is redundant but was created to clarify that, when queried, robot sensors return an integer, just like expresison nodes, therefore, the evaluate
 * method is redundant and can be associated to getValue() methods simplifying the overall structure of the parser. The assigment handout asks for robotSensors nodes
 * with an evaluate method, which returns the same type as an expression. In fact sensor-node is a subtype of expression-node, with one only difference in a method
 * naming, but both methods return the same type --> redundant.
 * 
 * @author diego
 *
 */
public interface RobotSensNode extends RobotExpNode {
	// public int evaluate(Robot robot);
	// public RobotSensNode parse(Scanner scan);
}

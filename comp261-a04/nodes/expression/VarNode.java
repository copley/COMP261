package expression;

import interfaces.RobotExpNode;
import java.util.Scanner;
import core.ProgramNode;
import parser.Parser;
import robot.Robot;

public class VarNode implements RobotExpNode {

	private String varName = null;

	@Override
	public RobotExpNode parse(Scanner scan) {

		
		if (scan.hasNext(Parser.VAR)) {
			varName = scan.next();
			
//			if (!ProgramNode.variables.containsKey(this)) {
//				Parser.fail(String.format("Variable not initialised: %s", varName), scan);
//			}
			/*
			// CHALLENGE II: check if top layer of stack contains our variable
			if (!ProgramNode.varStack.peek().containsKey(this)) {
				Parser.fail(String.format("Variable not initialised: %s", varName), scan);
			}
			*/
		}
		return null;
	}
	
	public RobotExpNode parseInit(Scanner scan) {

		if (scan.hasNext(Parser.VAR)) {
			varName = scan.next();
		} else {
			Parser.fail(String.format("Variable name not valid"), scan);
		}
		return null;
	}

	@Override
	public Integer evaluate(Robot robot) {

		// CHALLENGE I
//		System.out.println("This variable: " + ProgramNode.variables.get(this).evaluate(robot));
//		System.out.println(ProgramNode.variables.size());
		
//		for (VarNode key : ProgramNode.variables.keySet()){
//			if (key.toString().equals(varName)){
//				// this value is re defined
//				RobotExpNode tmp = ProgramNode.variables.get(key);
//				ProgramNode.variables.remove(key);
//				ProgramNode.variables.put(key, tmp);
//				return tmp.evaluate(robot);
//			}
//		}
		System.out.println("#####");
		System.out.println(ProgramNode.variables.get(varName));
		return (Integer) ProgramNode.variables.get(varName).evaluate(robot);
		
//		return 0;
		
//		return ProgramNode.variables.get(this).evaluate(robot);

		/*
		// CHALLENGE II: retrve the variables in the top layer of the stack
		System.out.println("dsddssdds");
		System.out.println(ProgramNode.varStack.peek().size());
		System.out.println("dsddssdds");
//		return ProgramNode.varStack.peek().get(this).evaluate(robot);
		return 1;
		*/
	}

	@Override
	public String toString(){
		return varName;
	}

//	@Override
//	public boolean equals(Object o){
//		if(o instanceof VarNode){
//			if(((VarNode) o).toString().equals(varName) ){
//				return true;
//			}
//		}
//		return false;
//	}

//	@Override
//	public int hashCode(){
//		return toString().hashCode();
//	}
}

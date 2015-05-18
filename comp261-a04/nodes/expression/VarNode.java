package expression;

import interfaces.RobotExpNode;
import java.util.Scanner;
import core.ProgramNode;
import parser.Parser;
import parser.ParserFailureException;
import robot.Robot;

public class VarNode implements RobotExpNode {

	private String varName = null;

	@Override
	public RobotExpNode parse(Scanner scan) {

		
		if (scan.hasNext(Parser.VAR)) {
			
			// CHALLENGE I:
			varName = scan.next();
			
			/*
			// CHALLENGE II: check if top layer of stack contains our variable
			if (!ProgramNode.varStack.peek().containsKey(varName)) {
				Parser.fail(String.format("Variable not initialised: %s", varName), scan);
			}
			*/
		}
		return null;
	}

	public RobotExpNode parseInit(Scanner scan) {

		if (scan.hasNext(Parser.VAR)) {

			varName = scan.next();
		}

		return null;
	}

	@Override
	public Integer evaluate(Robot robot) {
		
		// CHALLENGE I:
		if (ProgramNode.variables.containsKey(varName.toString())){ // if key is present
		return (Integer) ProgramNode.variables.get(varName).evaluate(robot);
		} else {
			throw new ParserFailureException("Variable not valid");
		}

		/*
		// CHALLENGE II: retrve the variables in the top layer of the stack
		
		Map<String, RobotExpNode> tmpMap = ProgramNode.varStack.peek();
		
		if (tmpMap.containsKey(varName.toString())){ // if key is present
			RobotExpNode tmpExp = tmpMap.get(varName.toString());
			Integer eval = tmpExp.evaluate(robot);
			tmpMap.put(varName.toString(), new NumberNode(eval));
			ProgramNode.varStack.pop();
			ProgramNode.varStack.push(tmpMap);
			return eval;
		} else {
			// key not found ... should look at upper stack ? 
			System.out.println("KEY NOT FOUND // THIS SHOULD NOT HAPPEN");
			// get it form variables 
			return (Integer) ProgramNode.variables.get(varName).evaluate(robot);
		}
		*/
		
	}

	@Override
	public String toString(){
		return varName;
	}

//	@Override // was used when map had to compare between varNodes rather than strings
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

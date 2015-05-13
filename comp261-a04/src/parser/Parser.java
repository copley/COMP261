package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;
import javax.swing.JFileChooser;
import util.ProgramNode;
import util.RobotNode;

/**
 * The parser and interpreter. The top level parse function, a main method for testing, and several utility methods are provided. You need to implement parseProgram and all the rest of the parser.
 */
public class Parser {
	
	// Statement patterns
	public static Pattern ACTION = Pattern.compile("move|takeFuel|turnL|turnR|wait|turnAround|shieldOn|shieldOff");
	public static Pattern LOOP = Pattern.compile("loop");
	public static Pattern IF = Pattern.compile("if");
	public static Pattern WHILE = Pattern.compile("while");
	public static Pattern VAR  = Pattern.compile("\\$[A-Za-z][A-Za-z0-9]*");

	
	// Action patterns
	public static Pattern MOVE = Pattern.compile("move");
	public static Pattern TAKEFUEL = Pattern.compile("takeFuel");
	public static Pattern TURNL = Pattern.compile("turnL");
	public static Pattern TURNR = Pattern.compile("turnR");
	public static Pattern WAIT = Pattern.compile("wait");
	public static Pattern TURNAROUND = Pattern.compile("turnAround");
	public static Pattern SHIELDON = Pattern.compile("shieldOn");
	public static Pattern SHIELDOFF = Pattern.compile("shieldOff");
	
//	// Generic beheaviour nodes
//	public static Pattern CONDITION = Pattern.compile("and|or|not|lt|gt|eq");

	// Expression nodes
	public static Pattern NUMPAT = Pattern.compile("-?\\d+");  // ("-?(0|[1-9][0-9]*)");
	public static Pattern SENSOR = Pattern.compile("fuelLeft|oppLR|oppFB|numBarrels|barrelLR|barrelFB|wallDist");
	public static Pattern OPERATION = Pattern.compile("add|sub|mul|div");
	
	// Others utilitity patterns
	public static Pattern SEMICOL = Pattern.compile(";");
	public static Pattern OPENP = Pattern.compile("\\(");
	public static Pattern CLOSEP = Pattern.compile("\\)");
	public static Pattern OPENBRACE = Pattern.compile("\\{");
	public static Pattern CLOSEBRACE = Pattern.compile("\\}");
	

	/**
	 * Top level parse method, called by the World
	 */
	public static RobotNode parseFile(File code) {
		Scanner scan = null;
		try {
			scan = new Scanner(code);
			// the only time tokens can be next to each other is
			// when one of them is one of (){},;
			scan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");
			RobotNode n = parseProgram(scan);  // You need to implement this!!!
			scan.close();
			return n;
		} catch (FileNotFoundException e) {
			System.out.println("Robot program source file not found");
		} catch (ParserFailureException e) {
			System.out.println("Parser error:");
			System.out.println(e.getMessage());
			scan.close();
		}
		return null;
	}

	/** For testing the parser without requiring the world */
	public static void main(String[] args) {
		if (args.length > 0) {
			for (String arg : args) {
				File f = new File(arg);
				if (f.exists()) {
					System.out.println("Parsing '" + f + "'");
					RobotNode prog = parseFile(f);
					System.out.println("Parsing completed ");
					if (prog != null) {
						System.out.println("================\nProgram:");
						System.out.println(prog);
					}
					System.out.println("=================");
				} else {
					System.out.println("Can't find file '" + f + "'");
				}
			}
		} else {
			while (true) {
				JFileChooser chooser = new JFileChooser("."); // System.getProperty("user.dir"));
				int res = chooser.showOpenDialog(null);
				if (res != JFileChooser.APPROVE_OPTION) {
					break;
				}
				RobotNode prog = parseFile(chooser.getSelectedFile());
				System.out.println("Parsing completed");
				if (prog != null) {
					System.out.println("Program: \n" + prog);
				}
				System.out.println("=================");
			}
		}
		System.out.println("Done");
	}

	
	/** PROG ::= STMT+ */
	static RobotNode parseProgram(Scanner scan) {
		
		RobotNode programNode = null;
		if (scan != null){
			programNode =  new ProgramNode().parse(scan);
		}
		return programNode;
	}
	
	// utility methods for the parser
	/**
	 * Report a failure in the parser.
	 */
	public static void fail(String message, Scanner s) {
		String msg = message + "\n   @ ...";
		for (int i = 0; i < 5 && s.hasNext(); i++) {
			msg += " " + s.next();
		}
		throw new ParserFailureException(msg + "...");
	}

	/**
	 * If the next token in the scanner matches the specified pattern, consume the token and return true. Otherwise return false without consuming anything. Useful for dealing with the syntactic
	 * elements of the language which do not have semantic content, and are there only to make the language parsable.
	 */
	public static boolean gobble(String p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

	public static boolean gobble(Pattern p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}
}
// You could add the node classes here, as long as they are not declared public (or private)

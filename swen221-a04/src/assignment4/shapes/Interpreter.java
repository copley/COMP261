package assignment4.shapes;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {
	// private String input;
	private int index;
	private Map<String, Shape> shapes;
	private String[] lines;

	public Interpreter(String input) {
		// this.input = input;
		this.index = 0;
		this.shapes = new HashMap<String, Shape>();
		lines = input.split("\n");
//		 for (String line : lines) {
//		 System.out.println(line);
//		 }
//		 System.out.println("-----------------------");
	}

	/**
	 * This method should return a canvas to which the input commands have been applied.
	 * 
	 * @return a canvas that shows the result of the input.
	 */
	public Canvas run() {
		Canvas canvas = new Canvas();
		for (String line : lines) {
			index = 0;
			while (index < line.length()) {
//				 System.out.println(line.charAt(index));
				evaluateNextCommand(canvas, line);
			}
		}
		return canvas;
	}

	private void evaluateNextCommand(Canvas canvas, String line) { // only works on entire line
		// skipWhiteSpace(); // in case there are white spaces
		// skipNonAlphaChar(line); // in case there are non alphanumeric characters
		String cmd = readWord(line); // first word in line. Represents the command sent to the interpreter could be a vriable: x,y,bla or a cmd: fill, draw, poo, or could be a new shape: ([, [

		if (cmd.equals("fill")) { // if is a filled shape
			// System.out.println("Fill cmd");
			skipWhiteSpace(line); // in case there are white spaces
			if (line.charAt(index) == '[') { // fill is assigned to an array
				Shape shape = readShape(line);
				Color color = readColor(line);
				fillShape(shape, color, canvas);
			} else { // fill is assigned to a variable
				String var = readWord(line); // read variable
				if (shapes.containsKey(var)) { // check if variable is valid
					Color color = readColor(line); // get the color the shape
					fillShape(shapes.get(var), color, canvas);// fillShape to variable
				}
			}

		} else if (cmd.equals("draw")) {
			// System.out.println("Draw cmd");
			skipWhiteSpace(line); // in case there are white spaces
			if (line.charAt(index) == '[') { // draw is assigned to an array
				Shape shape = readShape(line);
				Color color = readColor(line);
				drawShape(shape, color, canvas);
			} else { // draw is assigned to a variable
				String var = readWord(line); // read variable
				if (shapes.containsKey(var)) { // check if variable is valid
					Color color = readColor(line); // get the color the shape
					drawShape(shapes.get(var), color, canvas);// fillShape to variable
				}
			}

		} else if (cmd.matches("\\(")) { // cmd has a round bracket --> for this implementation just remove
			line.replace("(", "");
			line.replace(")", "");

		} else if (cmd.matches("^[a-zA-Z]$") && shapes.containsKey(cmd)) { // re-define an existing shape
			// System.out.println("Re-assign a shape operation to a variable");
			skipWhiteSpace(line); // in case there are white spaces
			if (line.matches(".*\\+.*")) { // attempt union operation
				// System.out.println("Do Union");
				this.doBoolean("+", cmd, line); // updates the curent shape
				index = line.length(); // move pointer to end of line
			} else if (line.matches(".*&.*")) { // attempt intersection operation
//				System.out.println("Do Intersection");
				this.doBoolean("&", cmd, line);
				index = line.length(); // move pointer to end of line
			} else if (line.matches(".*-.*")) { // attempt difference operation
//				 System.out.println("Do Difference");
				this.doBoolean("-", cmd, line);
				index = line.length(); // move pointer to end of line
			} else if (line.matches(".*\\[(.*?)\\].*")) { // re-define the shape array
				String shapeName = cmd;
				Shape shape = readShape(line);
				this.shapes.put(shapeName, shape);
				index = line.length(); // move pointer to end of line
			}

		} else if (cmd.matches("^[a-zA-Z]$") && !shapes.containsKey(cmd)) { // cmd matches an alphanumeric char, but is not in the cmd list --> is a new shape
			// System.out.println("New Shape form Array");
			// skipWhiteSpace(line); // in case there are white spaces
			String shapeName = cmd;
			Shape shape = readShape(line);
			this.shapes.put(shapeName, shape);
			index = line.length(); // move pointer to end of line

		}
	}

	/**
	 * Fill shape get boundingbox from shapes then draw on the canvas if contains is true apparently canvas draws in dots
	 * 
	 * @param shape
	 *            the shape to be processed
	 * @param col
	 *            the color of the shape
	 * @param can
	 *            the canvas on which to draw
	 */
	private void fillShape(Shape shape, Color col, Canvas can) {
		for (int i = 0; i < shape.boundingBox().getWidth() + shape.boundingBox().getX(); i++) {
			for (int j = 0; j < shape.boundingBox().getHeight() + shape.boundingBox().getY(); j++) {
				if (shape.contains(i, j)) {
					can.draw(i, j, col);
				}
			}
		}
	}

	/**
	 * Draw shape draws an outline of the current shape. The outline is determined by using an horizontal and vertical scanline algorithm
	 * 
	 * @param shape
	 *            the shape to be processed
	 * @param col
	 *            the color of the shape
	 * @param can
	 *            the canvas on which to draw
	 */
	private void drawShape(Shape shape, Color col, Canvas can) {
		for (int j = 0; j < shape.boundingBox().getHeight() + shape.boundingBox().getY(); j++) {
			for (int i = 0; i < shape.boundingBox().getWidth() + shape.boundingBox().getX(); i++) {
				if (shape.contains(i, j) && !shape.contains(i + 1, j) || !shape.contains(i - 1, j) && shape.contains(i, j) || shape.contains(i, j) && !shape.contains(i, j + 1)
						|| !shape.contains(i, j - 1) && shape.contains(i, j)) {
					can.draw(i, j, col);
				} else {
					if (can.width() < i-1 && can.height() < j-1) {
						can.draw(i, j, can.colorAt(i, j));
					}
				}
			}
		}
	}

	/**
	 * After the cmd draw or fill, this method will read the coordinates between [ ] and return a new Shape It starts from the character following the [ and ends parsing on the char == to ]
	 * 
	 * @return return a new shape with the dimensions specified in the brackets
	 */
	private Shape readShape(String line) {

		// skipWhiteSpace(string); // in case there are white spaces

		Pattern p = Pattern.compile("\\[(.*?)\\]"); // find the first occurance of a new array, if any
		Matcher m = p.matcher(line);
		if (m.find()) { // there is at least one new shape -> adding new shape
			String shapeString = m.group(1);
			String[] shapeArray = shapeString.split(",");
			for (int i = 0; i < shapeArray.length; i++) {
				shapeArray[i] = shapeArray[i].replace(" ", ""); // remove white spaces
			}
			if (shapeArray.length == 4) { // the array has the right length
				// System.out.println("Successfully generated a new shape");
				return new Rectangle(Integer.parseInt(shapeArray[0]), Integer.parseInt(shapeArray[1]), Integer.parseInt(shapeArray[2]), Integer.parseInt(shapeArray[3]));
			}
		}
		return null;

		// if (allMatches.size() > 0) {
		// for (String newArray : allMatches) {
		// System.out.println(newArray);
		// String[] shapeArray = newArray.split(",");
		// for (int i=0; i < shapeArray.length; i++) {
		// shapeArray[i] = shapeArray[i].replace(" ",""); // remove white spaces
		// shapeArray[i] = shapeArray[i].replace("[",""); // remove brackets
		// shapeArray[i] = shapeArray[i].replace("]",""); // remove brackets
		// System.out.println(shapeArray[i]);
		// //
		// }
		// // if (shapeArray.length == 4) { // the array has the right length
		// // return new Rectangle(Integer.parseInt(shapeArray[0]), Integer.parseInt(shapeArray[1]), Integer.parseInt(shapeArray[2]),
		// Integer.parseInt(shapeArray[3]));
		// // }
		// }
		// }
		// }

		// for (String newArray : allMatches) { // iterate though all the new shapes
		// }
		// String shapeString = m.group();
		// System.out.println(shapeString);
		// String[] shapeArray = shapeString.split(",");
		// for (int i=0; i < shapeArray.length; i++) {
		// shapeArray[i] = shapeArray[i].replace(" ",""); // remove white spaces
		// }
		// if (shapeArray.length == 4) { // the array has the right length
		// return new Rectangle(Integer.parseInt(shapeArray[0]), Integer.parseInt(shapeArray[1]), Integer.parseInt(shapeArray[2]),
		// Integer.parseInt(shapeArray[3]));
		// }
		// }

		// FOUND THE OCCURRANCE OF A NEW SHAPE... NEED TO READ THE SHAPE AND THEN TEST IF THERE ARE 2 SHAPES IN THE interpreter Test

		// while (index < line.length() && line.charAt(index) != '[') { // move pointer forward
		// index++;
		// }
		//
		//
		// int indStart = ++index; // set the beginning of the num array and skip this char
		// int indEnd = indStart; // set the end of the num array
		// while (index < line.length() && line.charAt(index) != ']') {
		// indEnd = ++index;
		// }
		// // System.out.printf("READSHAPE ->\t Index start: %d\tIndex end: %d\t Array: %s\n", indStart, indEnd, input.substring(indStart, indEnd));
		// String[] shapeArray = line.substring(indStart, indEnd).split(",");
		// for (int i = 0; i < shapeArray.length; i++) { // remove any white space
		// shapeArray[i] = shapeArray[i].replace(" ", "");
		// }
		// if (shapeArray.length == 4) {
		// return new Rectangle(Integer.parseInt(shapeArray[0]), Integer.parseInt(shapeArray[1]), Integer.parseInt(shapeArray[2]),
		// Integer.parseInt(shapeArray[3]));
		// // }
		// return null;
	}

	/**
	 * This method reads the hexadecimal color starting from the hashtag #
	 * 
	 * @return the Color of the Shape
	 */
	private Color readColor(String line) {

		skipWhiteSpace(line); // in case there are white spaces

		while (index < line.length() && line.charAt(index) != '#') { // move pointer forward
			index++;
		}

		if (line.charAt(index) == '#') {
			String colorStr = line.substring(index, index + 7);
			index = index + 7;
			return new Color(colorStr);
		}

		return null;
	}

	/**
	 * Read the next word in the provided line (stops when encounters a white space)
	 * 
	 * @param line
	 * @return
	 */
	private String readWord(String line) {
		skipWhiteSpace(line); // in case there are white spaces
		int start = index;
		while (index < line.length() && Character.isLetter(line.charAt(index))) {
			index++;
		}
		return line.substring(start, index);
	}

	/**
	 * Advanced the Index cursor though the non alphanumeric characters in the provided line
	 * 
	 * @param line
	 */
	private void skipNonAlphaChar(String line) {
		while (index < line.length() && !Character.isLetter(line.charAt(index))) {
			index++;
		}
	}

	/**
	 * Advances the index cursor though all the white spaces in the provided line
	 * 
	 * @param line
	 */
	private void skipWhiteSpace(String line) {
		while (index < line.length() && (line.charAt(index) == ' ')) {
			index++;
		}
	}

	/**
	 * Executes a union operation between the two shapes
	 * 
	 * @param var
	 *            the name of the shape-variable to whom assign the resulting shape
	 * @param line
	 *            the line containg the command
	 */
	private void doBoolean(String op, String var, String line) {

		Shape shapeA = null;
		Shape shapeB = null;

		String operation = line.split("=")[1]; // fetch the portion of line with the operation
		String operandA = operation.split("\\" + op + "")[0]; // split again to find the two operands
		String operandB = operation.split("\\" + op + "")[1];

		if (readShape(operandA) != null) { // check if there is an array for a shape to be created
			shapeA = readShape(operandA);
		} else if (shapes.get(operandA.replaceAll("[^a-zA-Z0-9]", "")) != null) { // is is not a new shape check if the variable is in the map
																					// (removing special char)
			shapeA = shapes.get(operandA.replaceAll("[^a-zA-Z0-9]", ""));
		} else {
			System.out.println("Shape A is not valid");
		}

		if (readShape(operandB) != null) { // check if there is an array for a shape to be created
			shapeB = readShape(operandB);
		} else if (shapes.get(operandB.replaceAll("[^a-zA-Z0-9]", "")) != null) { // is is not a new shape check if the variable is in the map
																					// (removing special char)
			shapeB = shapes.get(operandB.replaceAll("[^a-zA-Z0-9]", ""));
		} else {
			System.out.println("Shape B is not valid");
		}

		if (op.equals("+")) {
			shapes.put(var, new ShapeUnion(shapeA, shapeB)); // generates a new shape form the union of the two input and store in shpaes
		} else if (op.equals("&")) {
			shapes.put(var, new ShapeIntersection(shapeA, shapeB)); // generates a new shape form the union of the two input and store in shpaes
		} else if (op.equals("-")) {
			shapes.put(var, new ShapeDifference(shapeA, shapeB)); // generates a new shape form the union of the two input and store in shpaes
		} else {
			System.out.println("Operator not valid");
		}
	}
}

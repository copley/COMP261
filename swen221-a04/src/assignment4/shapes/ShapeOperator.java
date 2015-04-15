package assignment4.shapes;

/**
 * This abstract class is intended to eliminate any duplicate code found in the classes for the different shape operators.
 * @author diego
 *
 */

public abstract class ShapeOperator implements Shape{
	Shape shapeA;
	Shape shapeB;

	public ShapeOperator(Shape shapeA, Shape shapeB){
		this.shapeA = shapeA;
		this.shapeB = shapeB;
	}
	
}

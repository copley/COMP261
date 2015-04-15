package assignment4.shapes;

public class ShapeDifference extends ShapeOperator {

	public ShapeDifference(Shape shapeA, Shape shapeB) {
		super(shapeA, shapeB);
	}

	@Override
	public boolean contains(int x, int y) {
		return (shapeA.contains(x, y) && !shapeB.contains(x, y));
	}

	@Override
	public Rectangle boundingBox() {
		
		return new Rectangle(shapeA.boundingBox().getX(), shapeA.boundingBox().getY(), shapeA.boundingBox().getWidth(), shapeA.boundingBox().getHeight());
	}
}

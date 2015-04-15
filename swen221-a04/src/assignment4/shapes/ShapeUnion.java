package assignment4.shapes;

public class ShapeUnion extends ShapeOperator {

	public ShapeUnion(Shape shapeA, Shape shapeB) {
		super(shapeA, shapeB);
	}

	@Override
	public boolean contains(int x, int y) {
		boolean isOutside = !shapeA.contains(x, y) && !shapeB.contains(x, y);
		return !isOutside;
	}

	@Override
	public Rectangle boundingBox() {

		int newXMin = Math.min(shapeA.boundingBox().getX(), shapeB.boundingBox().getX());
		int newYMin = Math.min(shapeA.boundingBox().getY(), shapeB.boundingBox().getY());

		int newXMax = Math.max(shapeA.boundingBox().getX() + (shapeA.boundingBox().getWidth() ), shapeB.boundingBox().getX() + (shapeB.boundingBox().getWidth() ));
		int newYMax = Math.max(shapeA.boundingBox().getY() + (shapeA.boundingBox().getHeight() ), shapeB.boundingBox().getY() + (shapeB.boundingBox().getHeight() ));

		return new Rectangle(newXMin, newYMin, newXMax - newXMin, newYMax - newYMin);
	}
}

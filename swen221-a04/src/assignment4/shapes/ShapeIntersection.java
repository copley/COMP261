package assignment4.shapes;

public class ShapeIntersection extends ShapeOperator {

	public ShapeIntersection(Shape shapeA, Shape shapeB) {
		super(shapeA, shapeB);
	}

	@Override
	public boolean contains(int x, int y) {
		return shapeA.contains(x, y) && shapeB.contains(x, y);
	}

	@Override
	public Rectangle boundingBox() {
		
		int newMinX = -1;
		int newMinY = -1;
		int newMaxX = 0;
		int newMaxY = 0;
		int totalWidth = shapeA.boundingBox().getWidth() + shapeB.boundingBox().getWidth();
		int totalHeight = shapeA.boundingBox().getHeight() + shapeB.boundingBox().getHeight();
		int oldMinX = Math.min(shapeA.boundingBox().getX(), shapeB.boundingBox().getX());
		int oldYMin = Math.min(shapeA.boundingBox().getY(), shapeB.boundingBox().getY());
		
		for (int i = oldMinX ; i < totalWidth; i++) {
			for (int j = oldYMin ; j < totalHeight; j++) {
				if (shapeA.contains(i, j) && shapeB.contains(i, j)) { // pixel is contained in new bounding box --> set new minX
					if (newMinX == -1 && newMinY == -1) {
						newMinX = i;
						newMinY = j;
					} else {
						newMaxX = i;
						newMaxY = j;
					}
				}
			}
		}
		return new Rectangle(newMinX, newMinY, newMaxX - newMinX+1, newMaxY - newMinY+1);
	}

}

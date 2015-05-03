public class BBox {

	private float minX;
	private float minY;
	private float minZ;
	private float maxX;
	private float maxY;
	private float maxZ;

	public BBox(float minX, float maxX, float minY, float maxY, float minZ, float maxZ) {

		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	public float getMinX() {

		return minX;
	}

	public void setMinX(float minX) {

		this.minX = minX;
	}

	public float getMinY() {

		return minY;
	}

	public void setMinY(float minY) {

		this.minY = minY;
	}

	public float getMinZ() {

		return minZ;
	}

	public void setMinZ(float minZ) {

		this.minZ = minZ;
	}

	public float getMaxX() {

		return maxX;
	}

	public void setMaxX(float maxX) {

		this.maxX = maxX;
	}

	public float getMaxY() {

		return maxY;
	}

	public void setMaxY(float maxY) {

		this.maxY = maxY;
	}

	public float getMaxZ() {

		return maxZ;
	}

	public void setMaxZ(float maxZ) {

		this.maxZ = maxZ;
	}
}

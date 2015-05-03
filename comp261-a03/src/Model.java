import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Model {

	// public static final float ZOOOM_RATE = 100;
	private Set<Polygon> polygons = new HashSet<Polygon>();
	public List<Light> lightSources = new ArrayList<Light>();
	private Vector3D origin;
	private Vector3D scale;
	private Vector3D screenCentre;
	private BBox modelBBox;

	// public Model(Light light) {
	// this.lightSources.add(light); // creates model with light provided
	// }
	public Model(Vector3D lightDirection) {

		this.lightSources.add(new Light(lightDirection)); // creates model with new default light
		// setBounds(); // these gbounds will be inf when the model is furst created ... as there are no poly yet
		// origin = new Vector3D((maxX - minX) / 2, (maxY - minY) / 2, 0);
		// scale = new Vector3D(((GUI.CANVAS_WIDTH - (maxX - minX)) / 2), ((GUI.CANVAS_HEIGHT - (maxY - minY)) / 2), 0);
	}

	public BBox setBounds() {

		float maxX = Integer.MIN_VALUE;
		float maxY = Integer.MIN_VALUE;
		float maxZ = Integer.MIN_VALUE;
		float minX = Integer.MAX_VALUE;
		float minY = Integer.MAX_VALUE;
		float minZ = Integer.MAX_VALUE;
		if (polygons != null) {
			for (Polygon poly : polygons) {
				for (Vector3D vec : poly.getVertices()) {
					if (vec.x > maxX) maxX = vec.x;
					// System.out.println(maxX);
					if (vec.x < minX) minX = vec.x;
					// System.out.println(minX);
					if (vec.y > maxY) maxY = vec.y;
					// System.out.println(maxY);
					if (vec.y < minY) minY = vec.y;
					// System.out.println(minY);
					if (vec.z > maxZ) maxZ = vec.z;
					// System.out.println(maxZ);
					if (vec.z < minZ) minZ = vec.z;
					// System.out.println(minZ);
				}
			}
		}
		System.out.printf("BBOX: %f %f %f %f %f %f\n", minX, maxX, minY, maxY, minZ, maxZ);
		modelBBox = new BBox(minX, maxX, minY, maxY, minZ, maxZ);
		return modelBBox;
	}

	public Vector3D getOrigin() {

		return origin;
	}

	/**
	 * Add polygon to this model
	 * 
	 * @param p
	 *            polygon to add
	 */
	public void addPolygon(Polygon p) {

		polygons.add(p);
	}

	/**
	 * @return the lights
	 */
	public List<Light> getLights() {

		return lightSources;
	}

	/**
	 * @return the polygons
	 */
	public Set<Polygon> getPolygons() {

		return polygons;
	}

	/**
	 * This methods sets the bounds of all poly, centre and scale
	 */
	public void focus() {

		// Compute the bounds
		BBox bounds = setBounds();
		
		float scaleX = (GUI.CANVAS_WIDTH-10) / (bounds.getMaxX()-bounds.getMinX());
		System.out.println("scaleX " + scaleX);
		float scaleY = (GUI.CANVAS_HEIGHT-10) / (bounds.getMaxY()-bounds.getMinY());
		System.out.println("scaleY " + scaleY);
		float scaleScreen = Math.min(scaleX, scaleY);
				
		Transform transform = Transform.identity();
	    transform = Transform.newTranslation(-bounds.getMinX(), -bounds.getMinY(), 0).compose(transform); // move center to (0, 0)
	    transform = Transform.newScale(scaleScreen, scaleScreen, scaleScreen).compose(transform); // scale
		
		for (Polygon poly : polygons) {
			poly.applyTransformation(transform);
		}
		setBounds();
	}
}

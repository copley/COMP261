import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Model {
	
	public static final float ZOOOM_RATE = 100; 

	private Set<Polygon> polygons = new HashSet<Polygon>();
	public List<Light> lightSources = new ArrayList<Light>();
	
	public Model(Light light) {
		this.lightSources.add(light); // creates model with light provided
	}
	
	public Model(Vector3D lightDirection) {
		this.lightSources.add(new Light(lightDirection)); // creates model with new default light
	}
	
	/**
	 * Add polygon to this model
	 * @param p polygon to add
	 */
	public void addPolygon(Polygon p) {
		polygons.add(p);
	}

	/**
	 * @return the polygons
	 */
	public Set<Polygon> getPolygons() {
		return polygons;
	}

	public void zoomIn() {
		for (Polygon p : polygons) {
			p.zoom(ZOOOM_RATE);
		}
	}

	public void zoomOut() {
		for (Polygon p : polygons) {
			p.zoom(1/ZOOOM_RATE);
		}
	}	
	
}

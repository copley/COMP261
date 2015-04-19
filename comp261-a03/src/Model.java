import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Model {
	
	public static final float ZOOOM_RATE = 2; 

	private Set<Polygon> polygons = new HashSet<Polygon>();
	public Light lightSource;
	
	public Model(Light light) {
		this.lightSource = light; // creates model with light provided
	}
	
	public Model(Vector3D lightDirection) {
		lightSource = new Light(lightDirection); // creates model with new default light
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
	
	public Light getLightSource(){
		return lightSource;
	}
	
	
	public void zoomIn() {
		for (Polygon p : polygons) {
//			p.zoom(ZOOOM_RATE);
		}
	}

	public void zoomOut() {
		for (Polygon p : polygons) {
//			p.zoom(1/ZOOOM_RATE);
		}
	}	
	
}

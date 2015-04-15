package mapper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Polygon {

	private int type;
	private int endLevel;
	private String label;
	private int cityIdx;
	private PolyColor color;
	private List<Location> polyData = new ArrayList<Location>();
	
	public Polygon(Integer type, Integer endLevel, String label, Integer cityIdx, List<Location> data, PolyColor color) {
		this.type = type;
		this.endLevel = endLevel;
		this.label = label;
		this.cityIdx = cityIdx;
		this.color = color;
		for (Location loc : data){
			polyData.add(loc);
		}
		//System.out.printf("Type: %d \t %s \t %d \t %d \t %s \n", this.type, this.label, this.endLevel, this.cityIdx, getDataAsString(this.data));
	}

	public int getType() {
		return type;
	}

	public int getEndLevel() {
		return endLevel;
	}	
	
	public String getLabel() {
		return label;
	}

	public int getCityIdx() {
		return cityIdx;
	}

	public PolyColor getColor() {
		return color;
	}

	public List<Location> getData() {
		return polyData;
	}
	
	public void draw(Graphics g, Location origin, double scale, int filterType) {
		if (this.type == filterType ) {
			int[] xPoints = new int[polyData.size()];
			int[] yPoints = new int[polyData.size()];
			for (int i=0; i<this.polyData.size(); i++) {
				Point point = polyData.get(i).asPoint(origin, scale);
				Integer pX = (int) point.getX();
				Integer pY = (int) point.getY();
				xPoints[i] = pX;
				yPoints[i] = pY;
			}
			int nPoints = xPoints.length;
			g.setColor(new Color(color.red, color.green, color.blue));
			g.fillPolygon(xPoints, yPoints, nPoints); 
		}
	}
	
	@SuppressWarnings("unused")
	private String getDataAsString( List<Location> data) {
		String stringLoc = "";
		for (Location loc : data){
			stringLoc = stringLoc.concat(Double.toString(loc.x) + "\t" + Double.toString(loc.y)+" ");
		}
		return stringLoc;
	}
}

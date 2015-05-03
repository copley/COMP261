import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class Vertex {

	public Vector3D vector = null;
	public List<Polygon> faces = null;
	private Vector3D vertexNorm = null;
	public Color colorOut = new Color(0,0,0);
	
	public Vertex(float x, float y, float z){
		vector = new Vector3D(x,y,z);
		this.faces = new ArrayList<Polygon>();
	}
	
	public void computeNormal() {
		for(Polygon poly : faces) {
			if(vertexNorm == null) {
				vertexNorm = poly.surfaceNorm;
			} else {
				vertexNorm = vertexNorm.plus(poly.surfaceNorm);
			}
		}
		
		computeColor();
	}
	
	private void computeColor() {

		/* 
		// Initialise all the incoming and outgoing vars
		int[] ambLight = faces.get(0).rend.getAmbientLight();
		float ambient = faces.get(0).rend.ambentIntensity;
		Vector3D normal = vertexNorm;
		float specular = 0;
		float redDiff = faces.get(0).color.getRed() / 255f;
		float greenDiff = faces.get(0).color.getGreen() / 255f;
		float blueDiff = faces.get(0).color.getBlue() / 255f;
		float ambRed = ambLight[0] / 255f * ambient;
		float ambGreen = ambLight[1] / 255f * ambient;
		float ambBlue = ambLight[2] / 255f * ambient;
		// Loop though all lights
		for (Vector3D light : faces.get(0).rend.ligths) {
			Vector3D lightDir = light.unitVector();
			// COMPUTE DIFFUSE COMPONENT
			float costh = normal.dotProduct(lightDir);
			redDiff =+ costh * redDiff;
			greenDiff =+ costh * greenDiff;
			blueDiff =+ costh * blueDiff;
			// COMPUTE SPECULAR COMPONENT -- FOR PHONG IMPLEMENTATION
			Vector3D beta = normal.mult(2*costh);
			Vector3D mirroredLight = lightDir.minus(beta).unitVector();
			if (mirroredLight.z < 0) mirroredLight.z = 0;
			specular = specular + (float) Math.pow(mirroredLight.z, faces.get(0).rend.getSpecPow());
		}*/
		
		// This part is not fully funcitonal but I tried do implement the Gouraud shading method and I think I am quite close to it, but need some help form tutoris on how to acheive this correctly.
		
		
		// Initialise all the incoming and outgoing vars
		int[] ambLight = faces.get(0).rend.getAmbientLight();
		float ambient = faces.get(0).rend.ambentIntensity;
		Vector3D normal = vertexNorm;
		float specular = 0;
		float redDiff = faces.get(0).color.getRed() / 255f;
		float greenDiff = faces.get(0).color.getGreen() / 255f;
		float blueDiff = faces.get(0).color.getBlue() / 255f;
		float ambRed = ambLight[0] / 255f * ambient;
		float ambGreen = ambLight[1] / 255f * ambient;
		float ambBlue = ambLight[2] / 255f * ambient;
		// Loop though all lights
		for (Vector3D light : faces.get(0).rend.ligths) {
			Vector3D lightDir = light.unitVector();
			// COMPUTE DIFFUSE COMPONENT
			float costh = normal.dotProduct(lightDir);
			redDiff = costh * redDiff;
			greenDiff = costh * greenDiff;
			blueDiff = costh * blueDiff;
			// COMPUTE SPECULAR COMPONENT -- FOR PHONG IMPLEMENTATION
			Vector3D beta = normal.mult(2*costh);
			Vector3D mirroredLight = lightDir.minus(beta).unitVector();
			if (mirroredLight.z < 0) mirroredLight.z = 0;
			specular = specular + (float) Math.pow(mirroredLight.z, faces.get(0).rend.getSpecPow());
		}
		// CLAMP THE OUTPUT BETWEEN 0-1
		float redOut   = Math.max(0, Math.min(ambRed + redDiff, 1));
		float greenOut = Math.max(0, Math.min(ambGreen + greenDiff, 1));
		float blueOut  = Math.max(0, Math.min(ambBlue + blueDiff, 1));
//		float redOut   = Math.max(0, Math.min(ambRed + redDiff   + redDiff*specular, 1));
//		float greenOut = Math.max(0, Math.min(ambGreen + greenDiff + greenDiff*specular, 1));
//		float blueOut  = Math.max(0, Math.min(ambBlue + blueDiff  + blueDiff*specular, 1));
//		System.out.println("VERTEX COLOR OUT :"+redOut+greenOut+blueOut);
		this.colorOut = new Color(redOut, greenOut, blueOut);
		
	}

	public void addPoly(Polygon poly){
		this.faces.add(poly);
	}

	
}

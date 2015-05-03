import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Converter {
	
//	public List<Vector3D> ligth;
	
	public Converter() {

		onLoad(new File("bunnyVeryHi.obj"));
	}
	
	
	
	protected void onLoad(File file) {

		try {
			BufferedReader fileScan = new BufferedReader(new FileReader(file));
			loadModel(fileScan);
			fileScan.close();
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}
	
	private void loadModel(BufferedReader scan) {

		try {
			String line = "header";
				
//			float lightX = 0.4f;
//			float lightY =  -0.1f;
//			float lightZ =  -1f;
			List<Vector3D> vertices = new ArrayList<Vector3D>();
			List<Face> faces = new ArrayList<Face>();
			// first read all the vertices
			while ((line = scan.readLine()) != null) {
				String[] values = line.split("\\s+");
				if (values[0].equals("v")) {
					// add all vertices to a list array
					for (int i=0; i<values.length;i=i+4){
						vertices.add(new Vector3D(Float.parseFloat(values[i+1]),Float.parseFloat(values[i+2]),Float.parseFloat(values[i+3])));
					}
				} else if (values[0].equals("f")) {
					for (int i=0; i<values.length;i=i+4){
						faces.add(new Face(Integer.parseInt(values[i+1]), Integer.parseInt(values[i+2]), Integer.parseInt(values[i+3])));
					}
				}
			}
			// now for each face compose a line of vertices
			for (int i=0; i<faces.size(); i++){
//				System.out.printf("%d %d %d\n",faces.get(i).x , faces.get(i).y , faces.get(i).z);
				Vector3D v1 = vertices.get(faces.get(i).x-1); 
				Vector3D v2 = vertices.get(faces.get(i).y-1); 
				Vector3D v3 = vertices.get(faces.get(i).z-1);
				System.out.printf("%f %f %f %f %f %f %f %f %f 8 200 107\n",v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v3.x, v3.y, v3.z);
			} // could prettify this class by adding some write out method, but for testing purposes this is working well enough
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) {

		new Converter();
	}
}
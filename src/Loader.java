import java.io.File;
import java.util.Scanner;

import org.ejml.simple.SimpleMatrix;

public class Loader {
	public static IndexedFace readObj(String name) {
		IndexedFace faces = new IndexedFace();
		try {
			File file = new File(name);
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				String[] tokens = data.split(" ");
				
				if(tokens[0].compareTo("v") == 0) {
					faces.addVec(new SimpleMatrix(new double [][] {
						new double[] {Double.parseDouble(tokens[1])},
						new double[] {Double.parseDouble(tokens[2])},
						new double[] {Double.parseDouble(tokens[3])},
						new double[] {1}
					}));
				}
				else if(tokens[0].compareTo("f") == 0) {
					faces.addFace(Integer.parseInt(tokens[1]) - 1, Integer.parseInt(tokens[2]) - 1, Integer.parseInt(tokens[3]) - 1);
				}
				else if(tokens[0].compareTo("o") == 0) {
					faces.setName(tokens[1]);
				}
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		return faces;
	}
	
}


import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.ejml.simple.SimpleMatrix;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sun.swing.FilePane;

/**
 * @author Milos
 */
public class Main extends Application {
	private Button load = new Button("Load");
	private Button reset = new Button("Reset");
	private final String FILE = "src/obj_files/bunny.obj";
	@Override
	public void start(Stage mainStage) {
		mainStage.setTitle("Visualiser");
		FlowPane pane = new FlowPane();
		Canvas canvas = new Canvas(800,800);
		NumberSpinner numSpinner = new NumberSpinner();
		IndexedFace faces;
		

		pane.getChildren().add(canvas);
		pane.getChildren().add(load);
		pane.getChildren().add(reset);
		pane.getChildren().add(numSpinner);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		 
		gc.setFill(Color.BLACK);
		gc.setLineWidth(1.0);
		Path filePath = Paths.get(FILE);
		faces = Loader.readObj(filePath.toString());
		faces = Transformator.rotate(faces, 180);
		faces = Transformator.scale(faces, canvas.getHeight()/4, canvas.getWidth()/4);
		SimpleMatrix[] vecs = new SimpleMatrix[faces.getVecs().size()];
		vecs = faces.getVecs().toArray(vecs);

		faces = Transformator.translate(faces, canvas.getHeight()/2, canvas.getWidth()/2);
		vecs = new SimpleMatrix[faces.getVecs().size()];
		vecs = faces.getVecs().toArray(vecs);

		
		for(int i=0;i<faces.getIndices().size();i++) {
			int[] face = faces.getIndices().get(i);
			gc.moveTo(vecs[face[0]].get(0), vecs[face[0]].get(1));
			//vykreslovanie
			gc.lineTo(vecs[face[1]].get(0), vecs[face[1]].get(1));
			gc.lineTo(vecs[face[2]].get(0), vecs[face[2]].get(1));
			gc.lineTo(vecs[face[0]].get(0), vecs[face[0]].get(1));
			gc.stroke();
		}
		
		mainStage.setScene(new Scene(pane, 900, 900));
		mainStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

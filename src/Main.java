
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
import javafx.scene.control.TextField;
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
	private TextField file = new TextField("bunny.obj");
	private final String PATH = "src/obj_files/";
	private IndexedFace loadedFaces;
	
	@Override
	public void start(Stage mainStage) {
		mainStage.setTitle("Visualiser");
		FlowPane pane = new FlowPane();
		Canvas canvas = new Canvas(800,800);
		NumberSpinner numSpinner = new NumberSpinner();
		

		pane.getChildren().add(canvas);
		pane.getChildren().add(file);
		pane.getChildren().add(load);
		pane.getChildren().add(reset);
		pane.getChildren().add(numSpinner);
		load.setOnAction(e -> load(canvas, canvas.getGraphicsContext2D()));
		reset.setOnAction(e -> reset(canvas, canvas.getGraphicsContext2D()));
		
		
		mainStage.setScene(new Scene(pane, 900, 900));
		mainStage.show();
	}
	
	private void reset(Canvas canvas, GraphicsContext gc) {
		MyVec[] vecs = new MyVec[loadedFaces.getVecs().size()];
		vecs = loadedFaces.getVecs().toArray(vecs);
		for(int i=0;i<loadedFaces.getIndices().size();i++) {
			int[] face = loadedFaces.getIndices().get(i);
			gc.moveTo(vecs[face[0]].getX(), vecs[face[0]].getY());
			//vykreslovanie
			gc.lineTo(vecs[face[1]].getX(), vecs[face[1]].getY());
			gc.lineTo(vecs[face[2]].getX(), vecs[face[2]].getY());
			gc.lineTo(vecs[face[0]].getX(), vecs[face[0]].getY());
			gc.stroke();
		}
	}
	
	private void load(Canvas canvas, GraphicsContext gc) {
		IndexedFace faces;
		

		gc.beginPath();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.BLACK);
		gc.setLineWidth(1.0);
		Path filePath = Paths.get(PATH + file.getText());
		faces = Loader.readObj(filePath.toString());
		
		
		faces = Transformator.rotate(faces, 'z', Math.PI);
		faces = Transformator.scale(faces, canvas.getHeight()/4, canvas.getWidth()/4);;
		faces = Transformator.translate(faces, canvas.getHeight()/2, canvas.getWidth()/2);
		MyVec[] vecs = new MyVec[faces.getVecs().size()];
		vecs = faces.getVecs().toArray(vecs);

		
		for(int i=0;i<faces.getIndices().size();i++) {
			int[] face = faces.getIndices().get(i);
			gc.moveTo(vecs[face[0]].getX(), vecs[face[0]].getY());
			//vykreslovanie
			gc.lineTo(vecs[face[1]].getX(), vecs[face[1]].getY());
			gc.lineTo(vecs[face[2]].getX(), vecs[face[2]].getY());
			gc.lineTo(vecs[face[0]].getX(), vecs[face[0]].getY());
			gc.stroke();
		}
		this.loadedFaces = faces.clone();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

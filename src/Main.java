
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
	private IndexedFace actualFaces;
	private Canvas canvas;
	private GraphicsContext gc;
	
	@Override
	public void start(Stage mainStage) {
		mainStage.setTitle("Visualiser");
		FlowPane pane = new FlowPane();
		canvas = new Canvas(800,800);
		gc = canvas.getGraphicsContext2D();
		NumberSpinner numSpinner = new NumberSpinner();
		gc.setFill(Color.BLACK);
		gc.setLineWidth(1.0);

		pane.getChildren().add(canvas);
		pane.getChildren().add(file);
		pane.getChildren().add(load);
		pane.getChildren().add(reset);
		pane.getChildren().add(numSpinner);
		load.setOnAction(e -> load());
		reset.setOnAction(e -> reset());
		
		
		mainStage.setScene(new Scene(pane, 900, 900));
		mainStage.show();
	}
	
	private void reset() {
		actualFaces = loadedFaces;
		this.draw();
		
	}
	
	private void load() {

		
		Path filePath = Paths.get(PATH + file.getText());
		actualFaces = Loader.readObj(filePath.toString());
		
		
		actualFaces = Transformator.rotate(actualFaces, 'z', Math.PI);
		actualFaces = Transformator.scale(actualFaces, canvas.getHeight()/4, canvas.getWidth()/4);;
		actualFaces = Transformator.translate(actualFaces, canvas.getHeight()/2, canvas.getWidth()/2);
		MyVec[] vecs = new MyVec[actualFaces.getVecs().size()];
		vecs = actualFaces.getVecs().toArray(vecs);

		
		this.draw();
		
		this.loadedFaces = actualFaces.clone();
	}
	
	private void draw() {
		MyVec[] vecs = new MyVec[actualFaces.getVecs().size()];
		vecs = actualFaces.getVecs().toArray(vecs);

		gc.beginPath();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		for(int i=0;i<actualFaces.getIndices().size();i++) {
			int[] face = actualFaces.getIndices().get(i);
			gc.moveTo(vecs[face[0]].getX(), vecs[face[0]].getY());
			//vykreslovanie
			gc.lineTo(vecs[face[1]].getX(), vecs[face[1]].getY());
			gc.lineTo(vecs[face[2]].getX(), vecs[face[2]].getY());
			gc.lineTo(vecs[face[0]].getX(), vecs[face[0]].getY());
			gc.stroke();
		}
	}
	
	private void translate() {
		//treba spravit funkciu na vykreslovanie
		//toto nech len zoberie tie fieldy kde su ulozene hodnoty ako ma posunut a zavola Transformator
	}
	
	private void rotate() {
		//zoberie hodnoty a zavola ich na Transformator
	}
	
	private void scale() {
		//scale
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

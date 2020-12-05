
import java.nio.file.Path;
import java.nio.file.Paths;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

	private Button translateButton = new Button("Translate");
	private Button rotateButton = new Button("Rotate");
	private Button scaleButton = new Button("Scale");
	private TextField[] translationVal = new TextField[3];
	private TextField[] rotationVal = new TextField[3];
	private TextField scalingVal = new TextField();
	
	@Override
	public void start(Stage mainStage) {
		mainStage.setTitle("Visualiser");
		FlowPane pane = new FlowPane();
		canvas = new Canvas(800,800);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.setLineWidth(1.0);

		pane.getChildren().add(canvas);
		pane.getChildren().add(file);
		pane.getChildren().add(load);
		pane.getChildren().add(reset);
		
		for(int i=0; i < 3;i++) {
			translationVal[i] = new TextField();
			rotationVal[i] = new TextField();
			translationVal[i].setMaxWidth(50);
			rotationVal[i].setMaxWidth(50);
			pane.getChildren().add(translationVal[i]);
		}
		pane.getChildren().add(translateButton);
		for(int i=0; i < 3;i++) {
			pane.getChildren().add(rotationVal[i]);
		}
		pane.getChildren().add(rotateButton);
		pane.getChildren().add(scalingVal);
		pane.getChildren().add(scaleButton);
		
		translateButton.setOnAction(e -> translate());
		rotateButton.setOnAction(e -> rotate());
		scaleButton.setOnAction(e -> scale());
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
		//translate by Z?
		double toX = Double.parseDouble(this.translationVal[0].getText());
		double toY = Double.parseDouble(this.translationVal[1].getText());
		double toZ = Double.parseDouble(this.translationVal[2].getText());
		actualFaces = Transformator.translate(actualFaces, toX, toY);
		this.draw();
	}
	
	private void rotate() {
		//rotate arround local or global?
		double inRespectX = Double.parseDouble(this.rotationVal[0].getText());
		double inRespectY = Double.parseDouble(this.rotationVal[1].getText());
		double inRespectZ = Double.parseDouble(this.rotationVal[2].getText());
		if (inRespectX != 0) {
			actualFaces = Transformator.rotate(actualFaces, 'x', inRespectX);
		}
		else if (inRespectY != 0) {
			actualFaces = Transformator.rotate(actualFaces, 'y', inRespectY);
		}
		else if (inRespectZ != 0) {
			actualFaces = Transformator.rotate(actualFaces, 'z', inRespectZ);
		}
		this.draw();
	}
	
	private void scale() {
		double scalingFactor = Double.parseDouble(this.scalingVal.getText());
		actualFaces = Transformator.scale(actualFaces, scalingFactor, scalingFactor);
		this.draw();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}


import java.io.IOException;
import java.util.List;

import com.sun.javafx.geom.Vec4d;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Milos
 * Fundamental class of GUI, contains main. It launch sessions 
 * of employees or windows for scopes.
 */
public class Main extends Application {
	private Button load = new Button("Load");
	private Button reset = new Button("Reset");
	
	/**
	 * Method which run all GUI. Contains handlers for buttons.
	 */
	@Override
	public void start(Stage mainStage) {
		mainStage.setTitle("Warehouse manager");
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
		//gc.fillOval(x, y, w, h);
		
		faces = Loader.readObj("D:\\Milos\\eclipse-workspace\\Visualiser\\src\\obj_files\\bunny.obj");
		
		faces = Transformator.rotate(faces, 180);
		faces = Transformator.scale(faces, canvas.getHeight()/4, canvas.getWidth()/4);
		Vec4d[] vecs = new Vec4d[faces.getVecs().size()];
		vecs = faces.getVecs().toArray(vecs);
		
		faces = Transformator.translate(faces, canvas.getHeight()/2, canvas.getWidth()/2);
		vecs = new Vec4d[faces.getVecs().size()];
		vecs = faces.getVecs().toArray(vecs);

		System.out.println("Translated:");
		
		for(int i=0;i<faces.getIndices().size();i++) {
			int[] face = faces.getIndices().get(i);
			gc.moveTo(vecs[face[0]].x, vecs[face[0]].y);
			gc.lineTo(vecs[face[1]].x, vecs[face[1]].y);
			gc.lineTo(vecs[face[2]].x, vecs[face[2]].y);
			gc.lineTo(vecs[face[0]].x, vecs[face[0]].y);
			gc.stroke();
			for(int j=0;j<2;j++) {
				
			}
		}
		
		mainStage.setScene(new Scene(pane, 900, 900));
		mainStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

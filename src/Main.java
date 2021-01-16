import com.sun.glass.ui.View;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

/**
 * @author Milos
 */
public class Main extends Application {
	private final int CANVAS_WIDTH = 800;
	private final int CANVAS_HEIGHT = 800;
	private final int SCENE_WIDTH = 900;
	private final int SCENE_HEIGHT = 900;
	private final int TEXTFIELD_WIDTH = 50;
	private static final int AXIS_X = 0;
	private static final int AXIS_Y = 1;
	private static final int AXIS_Z = 2;
	private static final int FIRST = 0;
	private static final int SECOND = 1;
	private static final int THIRD = 2;
	private final Button load = new Button("Load");
	private final Button reset = new Button("Reset");
	private final TextField file = new TextField("icosphere.obj");
	private final String PATH = "src/obj_files/";
	private final Button translateButton = new Button("Translate");
	private final Button rotateButton = new Button("Rotate");
	private final Button scaleButton = new Button("Scale");
	private final TextField[] translationVal = new TextField[3];
	private final TextField[] rotationVal = new TextField[3];
	private final TextField scalingVal = new TextField();
	private final TextField[] lightDirectionVal = new TextField[3];
	private final Button setLightDirection = new Button("Set Light Direction");
	private final double LINE_WIDTH = 1.0;
	private final MyVec VIEW = new MyVec(0,0,-1,0);
	private final double RED = 0.1;
	private final double GREEN = 0.1;
	private final double BLUE = 0.7;
	
	private IndexedFace loadedFaces;
	private Canvas canvas;
	private GraphicsContext graphicContext;
	private MyMatrix transformatioMatrix;
	private MyVec light = new MyVec(0,0,-1,0);
	
	@Override
	public void start(Stage mainStage) {
		mainStage.setTitle("Visualiser");
		intitializeCanvasAndContext();
		initializeTransformationMatrix();
		FlowPane pane = intitializePane();
		initializeTextFields();
		addComponents(pane);
		setButtons();
		mainStage.setScene(new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT));
		mainStage.show();
	}
	
	private void initializeTransformationMatrix() {
		transformatioMatrix = new MyMatrix(
				new double[][] {
					new double[] {1,0,0,0},
					new double[] {0,1,0,0},
					new double[] {0,0,1,0},
					new double[] {0,0,0,1}
				});
		

		transformatioMatrix = Transformator.rotate(transformatioMatrix, 'z', Math.PI);
		transformatioMatrix = Transformator.rotate(transformatioMatrix, 'y', Math.PI);
		transformatioMatrix = Transformator.scale(transformatioMatrix, canvas.getHeight()/4, canvas.getWidth()/4, canvas.getWidth()/4);;
		transformatioMatrix = Transformator.translate(transformatioMatrix, canvas.getHeight()/2, canvas.getWidth()/2);
	}
	
	private void intitializeCanvasAndContext() {
		canvas = new Canvas(CANVAS_WIDTH,CANVAS_HEIGHT);
		graphicContext = canvas.getGraphicsContext2D();
		graphicContext.setFill(Color.BLACK);
		graphicContext.setLineWidth(LINE_WIDTH);
		
	}
	
	private void initializeTextFields() {
		for(int i=0; i < 3;i++) {
			translationVal[i] = new TextField();
			rotationVal[i] = new TextField();
			lightDirectionVal[i] = new TextField();
			translationVal[i].setMaxWidth(TEXTFIELD_WIDTH);
			rotationVal[i].setMaxWidth(TEXTFIELD_WIDTH);
			lightDirectionVal[i].setMaxWidth(TEXTFIELD_WIDTH);
		}
	}
	
	private FlowPane intitializePane() {
		FlowPane pane = new FlowPane();
		return pane;
	}
	
	private void addComponents(FlowPane pane) {
		ObservableList<Node> paneChildren = pane.getChildren();
		addBasicFunctionality(paneChildren);
		addTranslationCompononents(paneChildren);
		addRotationComponents(paneChildren);
		addScalingComponents(paneChildren);
		addLightingComponents(paneChildren);
	}
	
	private void addBasicFunctionality(ObservableList<Node> paneChildren) {
		paneChildren.add(canvas);
		paneChildren.add(file);
		paneChildren.add(load);
		paneChildren.add(reset);
	}
	
	private void addTranslationCompononents(ObservableList<Node> paneChildren) {
		for(int i=0; i < 3;i++) {
			paneChildren.add(translationVal[i]);
		}
		paneChildren.add(translateButton);
	}
	
	private void addRotationComponents(ObservableList<Node> paneChildren) {
		for(int i=0; i < 3;i++) {
			paneChildren.add(rotationVal[i]);
		}
		paneChildren.add(rotateButton);
	}
	
	private void addScalingComponents(ObservableList<Node> paneChildren) {
		paneChildren.add(scalingVal);
		paneChildren.add(scaleButton);
	}
	
	private void addLightingComponents(ObservableList<Node> paneChildren) {
		for(int i=0; i < 3;i++) {
			paneChildren.add(lightDirectionVal[i]);
		}
		paneChildren.add(setLightDirection);
	}

	private void setButtons() {
		translateButton.setOnAction(e -> translate());
		rotateButton.setOnAction(e -> rotate());
		scaleButton.setOnAction(e -> scale());
		load.setOnAction(e -> load());
		reset.setOnAction(e -> reset());
		setLightDirection.setOnAction(e -> changeLight());
	}
	
	private void reset() {
		initializeTransformationMatrix();
		draw();
		
	}
	
	private void load() {
		loadOBJ();
		initializeTransformationMatrix();
		draw();
	}
	
	private void loadOBJ() {
		String filePath = PATH + file.getText();
		loadedFaces = Loader.readObj(filePath);
	}
	
	private void changeLight() {
		double toX = getDouble(this.lightDirectionVal[AXIS_X]);
		double toY = getDouble(this.lightDirectionVal[AXIS_Y]);
		double toZ = getDouble(this.lightDirectionVal[AXIS_Z]); 
		light = new MyVec(toX, toY, toZ, 0);
		this.draw();
	}
	
	private void draw() {
		System.out.println(transformatioMatrix);
		IndexedFace actualFaces = Transformator.transform(loadedFaces, transformatioMatrix);
		
		MyVec[] vecs = getVecsArray(actualFaces);
		int actualFaceIndicesAmount = actualFaces.getIndices().size();
		
		graphicContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(int i=0;i<actualFaceIndicesAmount;i++) {
			int[] face = actualFaces.getIndices().get(i);
			if(isVisible(face, vecs)) {
				drawTriangel(graphicContext, face, vecs);
			}
		}
	}
	
	private MyVec[] getVecsArray(IndexedFace actualFaces) {
		MyVec[] vecs = new MyVec[actualFaces.getVecs().size()];
		return vecs = actualFaces.getVecs().toArray(vecs);
	}
	
	private boolean isVisible(int[] face, MyVec[] vecs) {
		MyVec normal = getNormal(face, vecs);
		if(VIEW.dot(normal) > 0)
				return true;
		return false;
	}
	
	private MyVec getNormal(int[] face, MyVec[] vecs) {
		MyVec v0 = vecs[face[SECOND]].minus(vecs[face[FIRST]]).divide(200);
		MyVec v1 = vecs[face[THIRD]].minus(vecs[face[SECOND]]).divide(200);
		return v0.cross(v1);
	}
	
	private void drawTriangel(GraphicsContext context, int[] face, MyVec[] vecs) {
		double[] x_coordinates = new double[3];
		double[] y_coordinates = new double[3];
		x_coordinates[0] = vecs[face[FIRST]].getX();
		x_coordinates[1] = vecs[face[SECOND]].getX();
		x_coordinates[2] = vecs[face[THIRD]].getX();
		y_coordinates[0] = vecs[face[FIRST]].getY();
		y_coordinates[1] = vecs[face[SECOND]].getY();
		y_coordinates[2] = vecs[face[THIRD]].getY();
		double ambient_reflection = 0.8;
		double ambient_I = 0.5;
		double I = ambient_reflection * ambient_I + getLight(face, vecs);
		if(I > 1) I = 1;
		Color color = new Color(0.0,0.0,0.75*I,1);
		context.setFill(color);
        context.fillPolygon(x_coordinates, y_coordinates, 3);
	}
	
	private double getLight(int[] face, MyVec[] vecs) {
		double diffus_reflection = 2.7;
		double specular_reflection = 1.5;
		double shininess = 1;
		MyVec normal = getNormal(face, vecs);
		double I_diffuse = normal.dot(light);
		MyVec half = VIEW.plus(light).divide(VIEW.plus(light).length());
		double I_specular = Math.pow(half.dot(normal), shininess);
		if(I_diffuse < 0) I_diffuse = 0;
		if(I_specular < 0) I_specular = 0;
		return diffus_reflection * I_diffuse + specular_reflection * I_specular;
	}
	
	private void translate() {
		double toX = getDouble(this.translationVal[AXIS_X]);
		double toY = getDouble(this.translationVal[AXIS_Y]);
		double toZ = getDouble(this.translationVal[AXIS_Z]); //this will be used in the future
		transformatioMatrix = Transformator.translate(transformatioMatrix, toX, toY);
		this.draw();
	}
	
	private void rotate() {
		//rotate arround local coordinates
		double inRespectX = getDouble(this.rotationVal[AXIS_X]);
		double inRespectY = getDouble(this.rotationVal[AXIS_Y]);
		double inRespectZ = getDouble(this.rotationVal[AXIS_Z]);
		if (inRespectX != 0) {
			transformatioMatrix = Transformator.rotate(transformatioMatrix, 'x', inRespectX * Math.PI);
		}
		if (inRespectY != 0) {
			transformatioMatrix = Transformator.rotate(transformatioMatrix, 'y', inRespectY * Math.PI);
		}
		if (inRespectZ != 0) {
			transformatioMatrix = Transformator.rotate(transformatioMatrix, 'z', inRespectZ * Math.PI);
		}
		this.draw();
	}
	
	private void scale() {
		double scalingFactor = getDouble(this.scalingVal);
		transformatioMatrix = Transformator.scale(transformatioMatrix, scalingFactor, scalingFactor, scalingFactor);
		this.draw();
	}
	
	private double getDouble(TextField field) {
		double value;
		try {
			value = Double.parseDouble(field.getText());
		} catch (Exception e) {
			value = 0;
		}
		return value;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

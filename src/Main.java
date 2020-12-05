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
	private final double LINE_WIDTH = 1.0;
	
	private IndexedFace loadedFaces;
	private IndexedFace actualFaces;
	private Canvas canvas;
	private GraphicsContext graphicContext;

	
	@Override
	public void start(Stage mainStage) {
		mainStage.setTitle("Visualiser");
		intitializeCanvasAndContext();
		FlowPane pane = intitializePane();
		initializeTextFields();
		addComponents(pane);
		setButtons();
		
		mainStage.setScene(new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT));
		mainStage.show();
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
			translationVal[i].setMaxWidth(TEXTFIELD_WIDTH);
			rotationVal[i].setMaxWidth(TEXTFIELD_WIDTH);
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

	private void setButtons() {
		translateButton.setOnAction(e -> translate());
		rotateButton.setOnAction(e -> rotate());
		scaleButton.setOnAction(e -> scale());
		load.setOnAction(e -> load());
		reset.setOnAction(e -> reset());
	}
	
	private void reset() {
		actualFaces = loadedFaces;
		draw();
		
	}
	
	private void load() {
		loadOBJ();
		trasnformFacesToCanas();
		draw();
		storeFaces();
	}
	
	private void loadOBJ() {
		String filePath = PATH + file.getText();
		actualFaces = Loader.readObj(filePath);
	}
		
	private void trasnformFacesToCanas() {
		actualFaces = Transformator.rotate(actualFaces, 'z', Math.PI);
		actualFaces = Transformator.scale(actualFaces, canvas.getHeight()/4, canvas.getWidth()/4);;
		actualFaces = Transformator.translate(actualFaces, canvas.getHeight()/2, canvas.getWidth()/2);
	}
	
	private void storeFaces() {
		loadedFaces = actualFaces.clone();
	}
	
	private void draw() {
		MyVec[] vecs = getVecsArray();

		graphicContext.beginPath();
		graphicContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		int actualFaceIndicesAmount = actualFaces.getIndices().size();
		for(int i=0;i<actualFaceIndicesAmount;i++) {
			int[] face = actualFaces.getIndices().get(i);
			drawTriangel(face, vecs);
			graphicContext.stroke();
		}
	}
	
	private MyVec[] getVecsArray() {
		MyVec[] vecs = new MyVec[actualFaces.getVecs().size()];
		return vecs = actualFaces.getVecs().toArray(vecs);
	}
	
	private void drawTriangel(int[] face, MyVec[] vecs) {
		MyVec firstNode = vecs[face[FIRST]];
		MyVec secondNode = vecs[face[SECOND]];
		MyVec thirdNode = vecs[face[THIRD]];
		graphicContext.moveTo(firstNode.getX(), firstNode.getY());
		graphicContext.lineTo(secondNode.getX(), secondNode.getY());
		graphicContext.lineTo(thirdNode.getX(), thirdNode.getY());
		graphicContext.lineTo(firstNode.getX(), firstNode.getY());
	}
	
	private void translate() {
		//translate by Z?
		double toX = Double.parseDouble(this.translationVal[AXIS_X].getText());
		double toY = Double.parseDouble(this.translationVal[AXIS_Y].getText());
		double toZ = Double.parseDouble(this.translationVal[AXIS_Z].getText()); //this will be used in the future
		actualFaces = Transformator.translate(actualFaces, toX, toY);
		this.draw();
	}
	
	private void rotate() {
		//rotate arround local or global?
		double inRespectX = Double.parseDouble(this.rotationVal[AXIS_X].getText());
		double inRespectY = Double.parseDouble(this.rotationVal[AXIS_Y].getText());
		double inRespectZ = Double.parseDouble(this.rotationVal[AXIS_Z].getText());
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

package solver;

import java.awt.Robot;
import java.rmi.UnexpectedException;

import com.sun.javafx.scene.text.TextLine;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SolverMain extends Application{
	
	Group root;
	GridPane grid;
	ScrollBar sc;
	EvaluatingVBox[] evalBoxes;
	Robot r;
	
	public static void main(String[] args){
		launch();
	}//main
	
	public SolverMain(){
		root = new Group();
		grid = new GridPane();
		sc = new ScrollBar();
		evalBoxes = new EvaluatingVBox[100];
	}//Constorter
	
	public void start(Stage stage) throws Exception {
		stage.setTitle("Expression Evaluator");
		Scene scene = new Scene(root, 550, 800);
		scene.setFill(Color.ALICEBLUE);
		stage.setScene(scene);
		root.getChildren().addAll(grid, sc);
		
		sc.setOrientation(Orientation.VERTICAL);
		sc.setMin(0);
		sc.setMax(1200);
		sc.setPrefSize(30, scene.getHeight());
		sc.setLayoutX(scene.getWidth() - 30);
		sc.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    grid.setLayoutY(-new_val.doubleValue());
            }
        });
		
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setGridLinesVisible(true);
		
		grid.add(addTopButtons(), 0, 0);
		evalBoxes[0] = evalBox(0);
		for(int i = 0; i < 100; i ++){
			evalBoxes[i] = evalBox(i);
			if(i > 0) evalBoxes[i].setVisible(false);
			grid.add(evalBoxes[i], 0, i + 1);
		}
		
		stage.show();
	}//start
	
	private HBox addTopButtons(){
		HBox topButtons = new HBox();
		topButtons.setSpacing(10);
		topButtons.setPadding(new Insets(25, 25, 25, 25));
		
		Button addEvalBox = new Button();
		addEvalBox.setPrefSize(400, 50);
		addEvalBox.setFont(new Font(18));
		addEvalBox.setText("Add Evaluation Box");
		addEvalBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent arg0){
				for(int i = 0; i < 100; i ++){
					if(!evalBoxes[i].isVisible()){
						evalBoxes[i].textField1.setText("");
						evalBoxes[i].textField2.setText("");
						evalBoxes[i].setVisible(true);
						break;
					}//if
				}//for
			}//handle
	    });//onMouseClicked
		
		topButtons.getChildren().add(addEvalBox);
		return topButtons;
	}//addTopButtons
	
	private EvaluatingVBox evalBox(int x){
		
		EvaluatingVBox base = new EvaluatingVBox();
		
		base.button1.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@SuppressWarnings("static-access")
			public void handle(MouseEvent arg0){
				try{ 
					base.textField2.setText("" + base.eval.eval(base.textField1.getText()));
				}catch(Exception ex){ 
					base.textField2.setText("Error");
				}
			}//handle
		});//onMouseClicked
		base.textField1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
            	if(event.getCode() == KeyCode.ESCAPE){
            		try{
	            		for(int i = x; i < 99; i ++){
	            			if(evalBoxes[i+1].isVisible()){
	            				evalBoxes[i].setVisible(true);
	            				evalBoxes[i].textField1.setText(evalBoxes[i+1].textField1.getText());
	            				evalBoxes[i].textField2.setText(evalBoxes[i+1].textField2.getText());
	            			}else{
	            				evalBoxes[i].setVisible(false);
	            			}
	            		}
            		}catch(Exception ex){
            		}
            	}//if
            	if(event.getCode() == KeyCode.RIGHT){ //this needs to be 'tab'
            		try{
            			if(!evalBoxes[x-1].textField2.getText().equals("Error")){
	            			base.textField1.setText(base.textField1.getText() + evalBoxes[x-1].textField2.getText());
	            			base.textField1.selectEnd();
            			}
            		}catch(Exception ex){
            		}
            	}//if    
            	if(event.getCode() == KeyCode.ENTER){
            		try{ 
    					base.textField2.setText("" + base.eval.eval(base.textField1.getText()));
    				}catch(Exception ex){ 
    					base.textField2.setText("Error");
    				}
            	}
            	if(event.getCode() == KeyCode.DOWN){
            		try{
            			if(evalBoxes[x+1].isVisible()){
            				evalBoxes[x+1].textField1.requestFocus();
            			}
            		}catch(Exception ex){
            		}
            	}
            	if(event.getCode() == KeyCode.UP){
            		try{
            			if(evalBoxes[x-1].isVisible()){
            				evalBoxes[x-1].textField1.requestFocus();
            			}
            		}catch(Exception ex){
            		}
            	}
            	if(event.getCode() == KeyCode.ALT){ //this should be 'n', this should be in a key listener for the whole scene
            		for(int i = 0; i < 100; i ++){
    					if(!evalBoxes[i].isVisible()){
    						evalBoxes[i].textField1.setText("");
    						evalBoxes[i].textField2.setText("");
    						evalBoxes[i].setVisible(true);
    						break;
    					}//if
    				}//for
            	}
            }//handle
        });
		
		return base;
	}//EvaluatingVBox
	
}//class

package solver;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class EvaluatingVBox extends VBox{
	
	Evaluator eval = new Evaluator();
	HBox row1;
	HBox row2;
	TextField textField1;
	TextField textField2;
	Button button1;
	
	public EvaluatingVBox(){
		row1 = new HBox();
		row2 = new HBox();
		textField1 = new TextField();
		textField2 = new TextField();
		button1 = new Button();
		
		setPadding(new Insets(25, 25, 25, 25));
		setSpacing(10);
		
		addRow1();
		addRow2();
		
		getChildren().addAll(row1, row2);
	}//Constorter

	private void addRow1(){
		row1.setSpacing(10);
		textField1.setFont(new Font(18));
		textField1.setPrefSize(300, 50);
		button1.setPrefSize(100, 50);
		button1.setFont(new Font(18));
		button1.setFocusTraversable(false);
		button1.setText("Evaluate");
		row1.getChildren().addAll(textField1, button1);
		textField2.setEditable(false);
	}//addRow1
	
	private void addRow2(){
		textField2.setFont(new Font(18));
		textField2.setPrefSize(410, 50);;
		row2.getChildren().addAll(textField2);
	}//addRow2
	
}//class

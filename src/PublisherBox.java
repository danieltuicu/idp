import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PublisherBox {

	public static void display(Publisher publisher) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Publisher details");
		
		Label name = new Label("Company: " + publisher.name);
		Label founded = new Label("Founded:  " + publisher.founded);
		Label employees = new Label("Employees: " + publisher.employees);
		
		VBox layout = new VBox();
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.setMinWidth(250);
		
		HBox buttonLayout = new HBox();
		buttonLayout.setMinWidth(250);
		buttonLayout.setAlignment(Pos.CENTER);
	
		Button button = new Button("Close");
		button.setMinWidth(50);
		button.setOnAction(e -> window.close());
		buttonLayout.getChildren().add(button);
		
		layout.getChildren().addAll(name, founded, employees, buttonLayout);
		
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	
}

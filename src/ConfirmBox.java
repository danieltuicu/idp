import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

	private static boolean answer;
	
	public static boolean display(String title, String message) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		window.setMinHeight(150);
		
		// Create two buttons
		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		
		// Setup buttons
		yesButton.setOnAction(e -> {
			answer = true;
			window.close();
		});	
		noButton.setOnAction(e -> {
			answer = false;
			window.close();
		});
		
		yesButton.setMinWidth(70);
		noButton.setMinWidth(70);
		
		// Setup vertical layout
		Label label = new Label(message);
		VBox vLayout = new VBox(10);
		vLayout.getChildren().addAll(label);
		vLayout.setAlignment(Pos.CENTER);
		
		// Setup buttons horizontal layout
		HBox hLayout = new HBox(30);
		hLayout.getChildren().addAll(yesButton, noButton);
		hLayout.setAlignment(Pos.CENTER);
		
		// Add buttons to the layout
		vLayout.getChildren().addAll(hLayout);
		
		// Show the dialog box
		Scene scene = new Scene(vLayout);
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}
	
}

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TransactionCompleteBox {

	public static void display(double total) {
		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Transaction complete");

		Label text = new Label("Thank you for your purchase!");
		Label subtotal = new Label("Total: " + String.format("%.2f", total) + " $");

		VBox layout = new VBox();
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.setMinWidth(250);
		layout.setAlignment(Pos.CENTER);

		HBox buttonLayout = new HBox();
		buttonLayout.setMinWidth(250);
		buttonLayout.setAlignment(Pos.CENTER);
		buttonLayout.setPadding(new Insets(10, 0, 0, 0));

		Button button = new Button("Close");
		button.setMinWidth(50);
		button.setOnAction(e -> window.close());
		buttonLayout.getChildren().add(button);

		layout.getChildren().addAll(text, subtotal, buttonLayout);

		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}

}

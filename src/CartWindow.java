
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class CartWindow {

	private TableView<CartItem> cartTable;
	private VBox cartLayout;
	private Controller controller;
	public Label title;

	public CartWindow(TableView<CartItem> cartTable, VBox cartLayout, Controller controler) {
		this.cartTable = cartTable;
		this.cartLayout = cartLayout;
		this.controller = controler;
	}

	public void setupWindow() {
		createCartTable();

		title = new Label("Shopping cart total: " + controller.get_total());
		title.setFont(new Font(20));
		title.setPadding(new Insets(10, 0, 0, 0));
		cartLayout.setPadding(new Insets(10, 30, 30, 30));

		Button remove = new Button("Remove");
		remove.setOnAction(e -> removeButtonClicked());

		Button complete = new Button("Complete transaction");
		complete.setOnAction(e -> completeTransactionClicked());

		HBox buttonsLayout = new HBox(355);
		buttonsLayout.getChildren().addAll(complete, remove);
		buttonsLayout.setPadding(new Insets(10, 0, 0, 0));
		cartLayout.getChildren().addAll(title, cartTable, buttonsLayout);
	}

	private void completeTransactionClicked() {
		if (cartTable.getItems().isEmpty())
			return;

		cartTable.getItems().clear();
		TransactionCompleteBox.display(controller.get_total());
		controller.emptyCart();
		updateTotal();
	}

	private void removeButtonClicked() {
		ObservableList<CartItem> items = cartTable.getItems();
		ObservableList<CartItem> selectedItems = cartTable.getSelectionModel().getSelectedItems();
		CartItem i = selectedItems.get(0);

		if (i == null)
			return;
		System.out.println("Selected: " + i.getTitle() + " " + i.getDescription() + " " + i.getPrice());
		selectedItems.forEach(items::remove);
		controller.deleteCartItem(i.getTitle(), i.getDescription(), i.getPrice());
		updateTotal();
	}

	private void createCartTable() {
		// Title column
		TableColumn<CartItem, String> titleColumn = new TableColumn<>("Title");
		titleColumn.setMinWidth(230);
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

		// Price column
		TableColumn<CartItem, String> priceColumn = new TableColumn<>("Price($)");
		priceColumn.setMinWidth(80);
		priceColumn.setCellValueFactory(new Callback<CellDataFeatures<CartItem, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<CartItem, String> data) {

				SimpleStringProperty result = new SimpleStringProperty();
				if (data.getValue().getPrice() == 0) {
					result.set("0.0 $");
				} else {
					result.set(data.getValue().getPrice() + " $");
				}
				return result;
			}

		});

		// Title column
		TableColumn<CartItem, String> descriptionColumn = new TableColumn<>("Description");
		descriptionColumn.setMinWidth(230);
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		cartTable.getColumns().addAll(titleColumn, descriptionColumn, priceColumn);
	}

	public void updateTotal() {
		title.setText("Shopping cart total: " + String.format("%.2f", controller.get_total()) + " $");
	}
}

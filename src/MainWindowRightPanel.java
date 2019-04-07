import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;

public class MainWindowRightPanel {

	private final VBox previewPanel;
	private final VBox optionsPanel;

	private Label title;
	private Label price;
	private Label releaseDate;
	private Hyperlink publisher;
	private Label developer;

	public TableView<DlcItem> dlcTable;
	public TableView<EditionItem> editionsTable;
	private TableView<CartItem> cartTable;

	private Controller controller;

	public MainWindowRightPanel(VBox previewPanel, VBox shoppingCart, Controller controller,
			TableView<CartItem> cartTable) {
		this.previewPanel = previewPanel;
		this.optionsPanel = shoppingCart;
		this.controller = controller;
		this.cartTable = cartTable;
	}

	public void setupPreviewPanel() {
		previewPanel.setMinWidth(300);
		previewPanel.setPadding(new Insets(30, 10, 10, 10));

		// Top layout where it says Preview
		HBox topLayout = new HBox();
		Label topLabel = new Label();
		topLabel.setText("Preview:");
		topLabel.setFont(new Font(20));
		topLayout.setAlignment(Pos.CENTER);
		topLayout.getChildren().add(topLabel);

		// Layout with details
		VBox detailsLayout = new VBox();
		title = new Label("Title:");
		price = new Label("Price:");
		releaseDate = new Label("Release date:");
		publisher = new Hyperlink();
		developer = new Label("Developer:");

		publisher.setOnAction(e -> {
			if(!publisher.getText().equals("")) {
				Publisher p = controller.getPublisher(publisher.getText());
				PublisherBox.display(p);
			}
		});
		
		detailsLayout.setPadding(new Insets(10, 10, 0, 10));
		detailsLayout.getChildren().addAll(title, price, releaseDate, new TextFlow(new Label("Publisher: "), publisher), developer);

		previewPanel.getChildren().addAll(topLayout, detailsLayout);
	}

	public void setupShoppingCart() {
		optionsPanel.setMinWidth(300);
		optionsPanel.setPadding(new Insets(00, 20, 20, 20));

		createDlcTable();
		createEditionsTable();
	}

	private void createDlcTable() {
		// DLC table
		dlcTable = new TableView<>();

		// Title column
		TableColumn<DlcItem, String> dlcTitleColumn = new TableColumn<>("Title");
		dlcTitleColumn.setMinWidth(190);
		dlcTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

		// Price column
		TableColumn<DlcItem, String> dlcPriceColumn = new TableColumn<>("Price($)");
		dlcPriceColumn.setMinWidth(80);
		dlcPriceColumn.setCellValueFactory(new Callback<CellDataFeatures<DlcItem, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<DlcItem, String> data) {

				SimpleStringProperty result = new SimpleStringProperty();
				if (data.getValue().getPrice() == 0) {
					result.set("Free");
				} else {
					result.set(data.getValue().getPrice() + " $");
				}
				return result;
			}

		});

		TableColumn<DlcItem, String> dlcActionCol = new TableColumn<>("A");
		dlcActionCol.setMaxWidth(45);
		dlcActionCol.setStyle("-fx-alignment: CENTER;");
		Callback<TableColumn<DlcItem, String>, TableCell<DlcItem, String>> dlcCellFactory = new Callback<TableColumn<DlcItem, String>, TableCell<DlcItem, String>>() {
			@Override
			public TableCell<DlcItem, String> call(final TableColumn<DlcItem, String> param) {
				final TableCell<DlcItem, String> cell = new TableCell<DlcItem, String>() {

					final Button btn = new Button("Buy");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setMinWidth(45);
							btn.setOnAction(event -> {
								DlcItem dlc = getTableView().getItems().get(getIndex());
								System.out.println(
										"Added to cart DLC: " + dlc.getTitle() + ". Price: " + dlc.getPrice() + " $");
								cartTable.getItems().add(new CartItem(dlc.getGame(), dlc.getPrice(), "DLC: " + dlc.getTitle()));
								controller.insertCartItem(dlc.getGame(), "DLC: " + dlc.getTitle(), dlc.getPrice());
							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};

		dlcActionCol.setCellFactory(dlcCellFactory);

		dlcTable.setMaxWidth(338);
		dlcTable.setMaxHeight(150);
		dlcTable.setPlaceholder(new Label("No DLC available for the selected game."));
		dlcTable.getColumns().addAll(dlcTitleColumn, dlcPriceColumn, dlcActionCol);

		VBox dlcLayout = new VBox();
		Label dlcLabel = new Label("DLC:");
		dlcLayout.getChildren().addAll(dlcLabel, dlcTable);

		optionsPanel.getChildren().addAll(dlcLayout);
	}

	public void updatePreviewDetails(Game game) {
		title.setText("Title: " + game.getTitle());
		price.setText("Price: " + ((game.getPrice() == 0.0) ? "Free to play" : game.getPrice() + " $"));
		releaseDate.setText("Release date: " + game.getReleased());
		publisher.setText(game.getPublisher());
		developer.setText("Developer: " + game.getDeveloper());
	}

	private void createEditionsTable() {
		// DLC table
		editionsTable = new TableView<>();

		// Title column
		TableColumn<EditionItem, String> editionsTitleColumn = new TableColumn<>("Title");
		editionsTitleColumn.setMinWidth(190);
		editionsTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

		// Price column
		TableColumn<EditionItem, String> editionsPriceColumn = new TableColumn<>("Price($)");
		editionsPriceColumn.setMinWidth(80);
		editionsPriceColumn
				.setCellValueFactory(new Callback<CellDataFeatures<EditionItem, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<EditionItem, String> data) {

						SimpleStringProperty result = new SimpleStringProperty();
						if (data.getValue().getPrice() == 0) {
							result.set("Free");
						} else {
							result.set(data.getValue().getPrice() + " $");
						}
						return result;
					}

				});

		TableColumn<EditionItem, String> editionsActionCol = new TableColumn("A");
		editionsActionCol.setMaxWidth(45);
		editionsActionCol.setStyle("-fx-alignment: CENTER;");
		Callback<TableColumn<EditionItem, String>, TableCell<EditionItem, String>> editionsCellFactory = new Callback<TableColumn<EditionItem, String>, TableCell<EditionItem, String>>() {

			@Override
			public TableCell<EditionItem, String> call(TableColumn<EditionItem, String> arg0) {
				final TableCell<EditionItem, String> cell = new TableCell<EditionItem, String>() {

					final Button btn = new Button("Buy");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setMinWidth(45);
							btn.setOnAction(event -> {
								EditionItem editions = getTableView().getItems().get(getIndex());
								System.out.println("Added to cart " + editions.getTitle() + " for " + editions.getGame()
										+ ". Price: " + editions.getPrice() + " $");
								cartTable.getItems().add(new CartItem(editions.getGame(), editions.getPrice(), editions.getTitle()));
								controller.insertCartItem(editions.getGame(), editions.getTitle(), editions.getPrice());
							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}

		};

		editionsActionCol.setCellFactory(editionsCellFactory);

		editionsTable.setMaxWidth(338);
		editionsTable.setMaxHeight(150);
		editionsTable.setPlaceholder(new Label("No editions available for the selected game."));
		editionsTable.getColumns().addAll(editionsTitleColumn, editionsPriceColumn, editionsActionCol);

		VBox editionLayout = new VBox();
		editionLayout.setPadding(new Insets(15, 0, 0, 0));
		Label editionLabel = new Label("Other editions:");
		editionLayout.getChildren().addAll(editionLabel, editionsTable);

		optionsPanel.getChildren().addAll(editionLayout);
	}
}

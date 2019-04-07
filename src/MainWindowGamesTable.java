

import java.util.StringTokenizer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class MainWindowGamesTable {

	private TableView<Game> gamesTable;
	private TableView<CartItem> cartTable;
	private TextField searchInput;
	private VBox mainLayout;
	private Controller controller;
	public MainWindowRightPanel rightPanel;
	private Scene cartScene;

	public MainWindowGamesTable(TableView<Game> gamesTable, TextField searchInput, VBox mainLayout,
			Controller controller, MainWindowRightPanel rightPanel, Scene cartScene, TableView<CartItem> cartTable) {
		this.gamesTable = gamesTable;
		this.searchInput = searchInput;
		this.mainLayout = mainLayout;
		this.controller = controller;
		this.rightPanel = rightPanel;
		this.cartScene = cartScene;
		this.cartTable = cartTable;
	}

	public void setupGamesDisplayList() {

		// Title column
		TableColumn<Game, String> titleColumn = new TableColumn<>("Title");
		titleColumn.setMinWidth(230);
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

		// Price column
		TableColumn<Game, String> priceColumn = new TableColumn<>("Price($)");
		priceColumn.setMinWidth(80);
		priceColumn.setCellValueFactory(new Callback<CellDataFeatures<Game, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Game, String> data) {

				SimpleStringProperty result = new SimpleStringProperty();
				if (data.getValue().getPrice() == 0) {
					result.set("Free to play");
				} else {
					result.set(data.getValue().getPrice() + " $");
				}
				return result;
			}

		});

		// Buy button column
		TableColumn<Game, String> actionCol = new TableColumn<>("A");
		actionCol.setMaxWidth(45);
		actionCol.setStyle("-fx-alignment: CENTER;");
		Callback<TableColumn<Game, String>, TableCell<Game, String>> cellFactory = new Callback<TableColumn<Game, String>, TableCell<Game, String>>() {
			@Override
			public TableCell<Game, String> call(final TableColumn<Game, String> param) {
				final TableCell<Game, String> cell = new TableCell<Game, String>() {

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
								Game game = getTableView().getItems().get(getIndex());
								System.out.println("Added to cart: " + game.getTitle() + ". Price: " + game.getPrice() + " $");
								cartTable.getItems().add(new CartItem(game.getTitle(), game.getPrice(), "Standard Edition"));
								controller.insertCartItem(game.getTitle(), "Standard Edition", game.getPrice());
							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};

		actionCol.setCellFactory(cellFactory);

		gamesTable.setItems(controller.getGames());
		gamesTable.setMaxWidth(368);
		gamesTable.setMinHeight(450);
		gamesTable.getColumns().addAll(titleColumn, priceColumn, actionCol);

		gamesTable.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			if (newValue != null) {
				rightPanel.updatePreviewDetails(newValue);
				rightPanel.dlcTable.setItems(controller.getDlcs(newValue.getTitle()));
				rightPanel.editionsTable.setItems(controller.getEditions(newValue.getTitle()));
			}

		});
		// Search input
		searchInput.setPromptText("Search");
		searchInput.setMinWidth(220);

		Button searchButton = new Button("Search");
		searchButton.setMinWidth(60);
		searchButton.setOnAction(e -> searchButtonClicked());

		// Search layout
		HBox searchLayout = new HBox();
		searchLayout.setPadding(new Insets(10, 10, 10, 10));
		searchLayout.setSpacing(10);
		searchLayout.setAlignment(Pos.CENTER);
		searchLayout.getChildren().addAll(searchInput, searchButton);

		// Top layout
		HBox topLayout = new HBox();
		topLayout.setPadding(new Insets(10, 10, 10, 10));
		topLayout.setSpacing(60);
		topLayout.setAlignment(Pos.CENTER);

		Label topLabel = new Label("Welcome to the shop!");
		topLabel.setFont(new Font(15));

		Button cartButton = new Button("View cart");
		cartButton.setOnAction(e -> {
			controller.setScene(cartScene);
			controller.updateTotal();
		});

		topLayout.getChildren().addAll(topLabel, cartButton);

		mainLayout.getChildren().addAll(topLayout, gamesTable, searchLayout);
	}

	private void searchButtonClicked() {
		String input = searchInput.getText();
		if (input.equals("")) {
			gamesTable.setItems(controller.getGames());
			return;
		}

		ObservableList<Game> gamesList = gamesTable.getItems();
		ObservableList<Game> matchingGames = FXCollections.observableArrayList();

		StringTokenizer t = new StringTokenizer(input);
		String word = "";
		while (t.hasMoreTokens()) {
			word = t.nextToken();
			for (Game game : gamesList) {
				if (game.getTitle().toLowerCase().contains(word.toLowerCase()) && !matchingGames.contains(game))
					matchingGames.add(game);
			}
		}
		gamesList.retainAll(matchingGames);
	}
}

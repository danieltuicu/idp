import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	private OracleConnection oc;
	private Stage window;
	private VBox gamesTableLayout;
	private VBox rightLayout;
	private VBox previewPanel;
	private VBox shoppingCart;
	
	private HBox mainLayout;
	private VBox cartLayout;

	private TableView<Game> gamesTable;
	private TableView<CartItem> cartTable;
	private TextField searchInput;
	private CartWindow cartView;
	private Scene mainScene;
	private Scene cartScene;

	private Controller controller = new Controller() {

		@Override
		public ObservableList<Game> getGames() {
			return oc.select_games();
		}

		@Override
		public ObservableList<DlcItem> getDlcs(String game) {
			return oc.select_dlcs_for_game(game);
		}

		@Override
		public ObservableList<EditionItem> getEditions(String game) {
			return oc.select_editions_for_game(game);
		}

		@Override
		public void setScene(Scene scene) {
			window.setScene(scene);
		}

		@Override
		public Publisher getPublisher(String name) {
			return oc.select_publisher(name);
		}

		@Override
		public void insertCartItem(String title, String description, double price) {
			oc.insert_cart_item(title, description, price);
		}

		@Override
		public ObservableList<CartItem> getCart() {
			return oc.select_cart();
		}

		@Override
		public void deleteCartItem(String title, String description, double price) {
			oc.delete_cart_item(title, description, price);
		}

		@Override
		public double get_total() {
			return oc.get_total();
		}

		@Override
		public void updateTotal() {
			cartView.updateTotal();
		}

		@Override
		public void emptyCart() {
			oc.delete_all_cart();
		}
	};

	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		setupConnection();

		this.window = primaryStage;
		gamesTableLayout = new VBox();
		gamesTable = new TableView<>();
		searchInput = new TextField();

		setupMainWindow();

		MainWindowRightPanel rightPanelView = new MainWindowRightPanel(previewPanel, shoppingCart, controller, cartTable);
		rightPanelView.setupPreviewPanel();
		rightPanelView.setupShoppingCart();

		MainWindowGamesTable gameTableView = new MainWindowGamesTable(gamesTable, searchInput, gamesTableLayout, controller, rightPanelView, cartScene, cartTable);
		gameTableView.setupGamesDisplayList();
		
		cartView = new CartWindow(cartTable, cartLayout, controller);
		cartView.setupWindow();
		cartTable.setItems(oc.select_cart());

		mainScene = new Scene(mainLayout);
		window.setScene(mainScene);
		window.setResizable(false);
		window.show();
	}

	private void setupMainWindow() {
		mainLayout = new HBox();
		rightLayout = new VBox();
		shoppingCart = new VBox();
		previewPanel = new VBox();
		
		cartLayout = new VBox();
		cartTable = new TableView<>();
		cartScene = new Scene(cartLayout);
		
		Button cartBackButton = new Button("<");
		cartBackButton.setOnAction(e -> window.setScene(mainScene));
		cartBackButton.setMinWidth(50);
		cartLayout.getChildren().addAll(cartBackButton);

		rightLayout.getChildren().addAll(previewPanel, shoppingCart);
		mainLayout.getChildren().addAll(gamesTableLayout, rightLayout);

		window.setTitle("Video Games Shop");
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
	}

	private void setupConnection() {
		//oc = new OracleConnection("localhost", 1521, "orcl", "dummy", "dummy");
		oc = new OracleConnection("localhost", 1521, "ORCLCDB", "dummy", "dummy");
		oc.openConnection();
	}

	private void closeProgram() {
		boolean answer = ConfirmBox.display("Confirm exit", "Are you sure you want to exit?");

		if (answer == true) {
			if (oc != null) {
				oc.closeConnection();
			}
			System.out.println("Program closed.");
			Platform.exit();
		}
	}
}

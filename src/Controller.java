import javafx.collections.ObservableList;
import javafx.scene.Scene;

public interface Controller {
	ObservableList<Game> getGames();
	ObservableList<DlcItem> getDlcs(String game);
	ObservableList<EditionItem> getEditions(String game);
	ObservableList<CartItem> getCart();
	Publisher getPublisher(String name);
	void setScene(Scene scene);
	void insertCartItem(String title, String description, double price);
	void deleteCartItem(String title, String description, double price);
	double get_total();
	void updateTotal();
	void emptyCart();
}

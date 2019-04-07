public class EditionItem {

	private final String title;
	private final double price;
	private final String game;

	public EditionItem(String title, double price, String game) {
		this.title = title;
		this.price = price;
		this.game = game;
	}

	public String getTitle() {
		return title;
	}

	public double getPrice() {
		return price;
	}

	public String getGame() {
		return game;
	}
	
	
}

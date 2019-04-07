public class DlcItem {

	private final String title;
	private final String released;
	private final double price;
	private final String game;

	public DlcItem(String title, double price, String released, String game) {
		this.title = title;
		this.price = price;
		this.released = released;
		this.game = game;
	}

	public String getTitle() {
		return title;
	}

	public double getPrice() {
		return price;
	}

	public String getReleased() {
		return released;
	}
	
	public String getGame() {
		return game;
	}

}

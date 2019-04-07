public class Game {

	private final String title;
	private final String publisher;
	private final String developer;
	private final String released;
	private final double price;

	public Game(String title, double price, String publisher, String developer, String released) {
		this.title = title;
		this.price = price;
		this.publisher = publisher;
		this.developer = developer;
		this.released = released;
	}

	public String getTitle() {
		return title;
	}

	public double getPrice() {
		return price;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getReleased() {
		return released;
	}

	public String getDeveloper() {
		return developer;
	}

}

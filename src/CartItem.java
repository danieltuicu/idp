public class CartItem {

	private final String title;
	private final double price;
	private final String description;
	
	public CartItem(String title, double price, String description) {
		this.title = title;
		this.price = price;
		this.description = description;
	}
	
	public String getTitle() {
		return title;
	}
	
	public double getPrice() {
		return price;
	}
	
	public String getDescription() {
		return description;
	}
}

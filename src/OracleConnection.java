import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class OracleConnection {

	private Connection connection = null;
	private String username = null;
	private String password = null;
	private String host = null;
	private int port;
	private String schema = null;

	public OracleConnection(String host, int port, String schema, String username, String password) {
		this.host = host;
		this.port = port;
		this.schema = schema;
		this.username = username;
		this.password = password;
		System.out.println(host + " " + port + " " + schema + " " + username + " " + password);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("JDBC check: PASSED!");
		} catch (ClassNotFoundException CNFEx) {
			System.out.println("JDBC check: NOT PASSED!");
			CNFEx.printStackTrace();
		}
	}

	public void openConnection() {
		try {
			String connString = "jdbc:oracle:thin:@" + this.host + ":" + this.port + ":" + this.schema;
			System.out.println(connString);
			this.connection = DriverManager.getConnection(connString, this.username, this.password);
			if (this.connection != null) {
				System.out.println("We are connected!");
			} else {
				System.out.println("Failed to make a connection!");
			}
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			this.connection.close();
			System.out.println("Connection closed!");
		} catch (Exception e) {
			System.out.println("Connection NOT closed!");
			e.printStackTrace();
		}
	}
	
	public double get_total() {
		try {
			String call = "{? = call tables.get_total}";
			CallableStatement cstmt = this.connection.prepareCall(call);
			// register parameters
			cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.executeQuery();
			// get the department's name
			return cstmt.getFloat(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public ObservableList<Game> select_games() {
		try {
			String call = "{call tables.select_games(?)}";
			CallableStatement cstmt = this.connection.prepareCall(call);
			// register the out parameters
			cstmt.registerOutParameter("games", oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			// get the cursor
			ResultSet rs = (ResultSet) cstmt.getObject("games");
			ObservableList<Game> games = FXCollections.observableArrayList();
			SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
			while (rs.next()) {
				games.add(new Game(rs.getString("title"), rs.getDouble("price"), rs.getString("publisher"), rs.getString("developer"), df.format(rs.getDate("released"))));
			}
			
			return games;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ObservableList<DlcItem> select_dlcs_for_game(String game) {
		try {
			String call = "{call tables.select_dlcs_for_game(?, ?)}";
			CallableStatement cstmt = this.connection.prepareCall(call);
			// register the out parameters
			cstmt.registerOutParameter("dlcs", oracle.jdbc.OracleTypes.CURSOR);
			cstmt.setString(2, game);
			cstmt.executeQuery();
			// get the cursor
			ResultSet rs = (ResultSet) cstmt.getObject("dlcs");
			ObservableList<DlcItem> dlcs = FXCollections.observableArrayList();
			SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
			while (rs.next()) {
				dlcs.add(new DlcItem(rs.getString("title"), rs.getDouble("price"), df.format(rs.getDate("released")), rs.getString("game")));
			}
			
			return dlcs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ObservableList<EditionItem> select_editions_for_game(String game) {
		try {
			String call = "{call tables.select_editions_for_game(?, ?)}";
			CallableStatement cstmt = this.connection.prepareCall(call);
			// register the out parameters
			cstmt.registerOutParameter("editions", oracle.jdbc.OracleTypes.CURSOR);
			cstmt.setString(2, game);
			cstmt.executeQuery();
			// get the cursor
			ResultSet rs = (ResultSet) cstmt.getObject("editions");
			ObservableList<EditionItem> editions = FXCollections.observableArrayList();
			while (rs.next()) {
				editions.add(new EditionItem(rs.getString("name"), rs.getDouble("price"), rs.getString("game")));
			}
			
			return editions;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Publisher select_publisher(String name) {
		try {
			String call = "{call tables.select_publisher(?, ?)}";
			CallableStatement cstmt = this.connection.prepareCall(call);
			// register the out parameters
			cstmt.registerOutParameter("publishers", oracle.jdbc.OracleTypes.CURSOR);
			cstmt.setString(2, name);
			cstmt.executeQuery();
			// get the cursor
			ResultSet rs = (ResultSet) cstmt.getObject("publishers");
			SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
			while (rs.next()) {
				return new Publisher(rs.getString("name"), df.format(rs.getDate("founded")), rs.getInt("employees"));
			}
			
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ObservableList<CartItem> select_cart() {
		try {
			String call = "{call tables.select_cart(?)}";
			CallableStatement cstmt = this.connection.prepareCall(call);
			// register the out parameters
			cstmt.registerOutParameter("cart", oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			// get the cursor
			ResultSet rs = (ResultSet) cstmt.getObject("cart");
			ObservableList<CartItem> cart = FXCollections.observableArrayList();
			while (rs.next()) {
				cart.add(new CartItem(rs.getString("title"), rs.getDouble("price"), rs.getString("description")));
			}
			
			return cart;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void insert_cart_item(String title, String description, double price) {
		try {
			String call = "{call tables.insert_cart_item(?, ?, ?)}";
			CallableStatement cstmt = this.connection.prepareCall(call);

			cstmt.setString(1, title);
			cstmt.setString(2, description);
			cstmt.setDouble(3, price);
			cstmt.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete_cart_item(String title, String description, double price) {
		try {
			String call = "{call tables.delete_cart_item(?, ?, ?)}";
			CallableStatement cstmt = this.connection.prepareCall(call);

			cstmt.setString(1, title);
			cstmt.setString(2, description);
			cstmt.setDouble(3, price);
			cstmt.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete_all_cart() {
		try {
			String call = "{call tables.delete_all_cart}";
			CallableStatement cstmt = this.connection.prepareCall(call);
			cstmt.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

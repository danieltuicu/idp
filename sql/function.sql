set serveroutput on;


--drop sequence inc_sequence

create or replace package tables as
      procedure select_games(games out sys_refcursor);
	  procedure select_dlcs_for_game(dlcs out sys_refcursor, game_title in dlc.game%type);
	  procedure select_editions_for_game(editions out sys_refcursor, game_title in editions.game%type);
	  procedure select_publisher(publishers out sys_refcursor, publisher_name in publishers.name%type);
	  procedure select_cart(cart out sys_refcursor);
	  procedure insert_cart_item(title in cart.title%type, description in cart.description%type, price in cart.price%type);
	  procedure delete_cart_item(t in cart.title%type, d in cart.description%type, p in cart.price%type);
	  function get_total return number;
	  procedure delete_all_cart;
end tables;
/

create or replace package body tables as
	
    procedure select_games(games out sys_refcursor)
	is
	begin
		open games for 
			select title, publisher, developer, released, price from games;
	end select_games;
	
	procedure select_dlcs_for_game(dlcs out sys_refcursor, game_title in dlc.game%type)
	is
	begin
		open dlcs for 
			select title, released, price, game from dlc where game = game_title;
	end select_dlcs_for_game;
	
	procedure select_editions_for_game(editions out sys_refcursor, game_title in editions.game%type)
	is
	begin
		open editions for 
			select name, price, game from editions where game = game_title;
	end select_editions_for_game;
	
	procedure select_publisher(publishers out sys_refcursor, publisher_name in publishers.name%type)
	is
	begin
		open publishers for
			select name, founded, employees from publishers where name = publisher_name;
	end select_publisher;
	
	procedure select_cart(cart out sys_refcursor)
	is
	begin
		open cart for
			select title, description, price from cart;
	end select_cart;
	
	procedure insert_cart_item(title in cart.title%type, description in cart.description%type, price in cart.price%type)
	is
	begin
		insert into Cart (Title, Description, Price) values (title, description, price);
	end insert_cart_item;
	
	procedure delete_cart_item(t in cart.title%type, d in cart.description%type, p in cart.price%type)
	is
	begin
		delete from cart c where c.title = t and c.description = d and c.price = p and c.id = (select min(t.id) from cart t where t.title = t and t.description = d and t.price = p);
	end delete_cart_item;
	
	function get_total return number
	is
		total number;
	begin
		select sum(price) into total from cart;
		return total;
	end get_total;
	
	procedure delete_all_cart
	is
	begin
		delete from cart;
	end delete_all_cart;
	
end tables;
/

CREATE SEQUENCE inc_sequence
START WITH 1
INCREMENT BY 1;

CREATE OR REPLACE TRIGGER inc_trigger
BEFORE INSERT
ON cart
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT inc_sequence.nextval INTO :NEW.ID FROM dual;
END;
/

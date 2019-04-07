/*drop table Games;
drop table Editions;
drop table Publishers;
drop table DLC;
drop table Cart;
*/
-- Games - Title, Publisher, Developer, Released, Price(eur)
create table Games (
	Title varchar2(50) primary key,
	Publisher varchar2(30) not null,
	Developer varchar2(30) not null,
	Released date not null,
	Price number(6,2) not null
);

insert into Games (Title, Publisher, Developer, Released, Price)
values ('Counter-Strike', 'Valve', 'Valve', to_date('01-NOV-2000','DD-MON-YYYY'), 4.99);

insert into Games (Title, Publisher, Developer, Released, Price)
values ('Counter-Strike: Source', 'Valve', 'Valve', to_date('01-NOV-2004','DD-MON-YYYY'), 15.99);

insert into Games (Title, Publisher, Developer, Released, Price)
values ('Counter-Strike: Global Offensive', 'Valve', 'Valve', to_date('21-Aug-2012','DD-MON-YYYY'), 0);

insert into Games (Title, Publisher, Developer, Released, Price)
values ('PAYDAY: The Heist', 'Daybreak Game Company', 'OVERKILL Software', to_date('20-Oct-2011','DD-MON-YYYY'), 14.99);

insert into Games (Title, Publisher, Developer, Released, Price)
values ('PAYDAY 2', 'Starbreeze Publishing AB', 'OVERKILL: a Starbreeze Studio', to_date('13-Aug-2013','DD-MON-YYYY'), 9.99);

insert into Games (Title, Publisher, Developer, Released, Price)
values ('Metro 2033', 'Deep Silver', '4A Games', to_date('26-Mar-2010','DD-MON-YYYY'), 19.99);

insert into Games (Title, Publisher, Developer, Released, Price)
values ('Metro: Last Light', 'Deep Silver', '4A Games', to_date('13-May-2014','DD-MON-YYYY'), 19.99);

insert into Games (Title, Publisher, Developer, Released, Price)
values ('FOR HONOR', 'Ubisoft', 'Ubisoft Quebec', to_date('14-Feb-2017','DD-MON-YYYY'), 29.99);

insert into Games (Title, Publisher, Developer, Released, Price)
values ('Tom Clancy''s Rainbow Six Siege', 'Ubisoft', 'Ubisoft Montreal', to_date('01-Dec-2015','DD-MON-YYYY'), 39.99);

insert into Games (Title, Publisher, Developer, Released, Price)
values ('Warframe', 'Digital Extremes', 'Digital Extremes', to_date('25-Mar-2013','DD-MON-YYYY'), 0);

select * from games;

-- DLC - Title, Released, Price, Game
create table DLC (
	Title varchar2(50) not null,
	Released date not null,
	price number(6,2),
	Game varchar2(50) not null
);

insert into DLC (Title, Released, Price, Game)
values ('Soundtrack', to_date('18-Oct-2012','DD-MON-YYYY'), 3.99, 'PAYDAY: The Heist');

insert into DLC (Title, Released, Price, Game)
values ('Wolfpack DLC', to_date('07-Aug-2012','DD-MON-YYYY'), 9.99, 'PAYDAY: The Heist');

insert into DLC (Title, Released, Price, Game)
values ('VR', to_date('15-Mar-2018','DD-MON-YYYY'), 0, 'PAYDAY 2');

insert into DLC (Title, Released, Price, Game)
values ('h3h3 Character Pack', to_date('22-Nov-2017','DD-MON-YYYY'), 4.99, 'PAYDAY 2');

insert into DLC (Title, Released, Price, Game)
values ('John Wich Heists', to_date('09-Feb-2017','DD-MON-YYYY'), 7.79, 'PAYDAY 2');

insert into DLC (Title, Released, Price, Game)
values ('Scarface Heist', to_date('16-Dec-2016','DD-MON-YYYY'), 13.89, 'PAYDAY 2');

insert into DLC (Title, Released, Price, Game)
values ('Sokol Character Pack', to_date('12-Jun-2015','DD-MON-YYYY'), 4.99, 'PAYDAY 2');

insert into DLC (Title, Released, Price, Game)
values ('Year 3 Pass', to_date('18-Dec-2018','DD-MON-YYYY'), 29.99, 'FOR HONOR');

insert into DLC (Title, Released, Price, Game)
values ('Year 1 Heroes Bundle', to_date('14-Feb-2017','DD-MON-YYYY'), 29.99, 'FOR HONOR');

insert into DLC (Title, Released, Price, Game)
values ('Marching Fire Expansion', to_date('05-OCT-2017','DD-MON-YYYY'), 29.99, 'FOR HONOR');

insert into DLC (Title, Released, Price, Game)
values ('Utra HD Texture Pack', to_date('05-OCT-2017','DD-MON-YYYY'), 0, 'Tom Clancy''s Rainbow Six Siege');

insert into DLC (Title, Released, Price, Game)
values ('Year 4 Pass', to_date('12-Jun-2015','DD-MON-YYYY'), 29.99, 'Tom Clancy''s Rainbow Six Siege');

insert into DLC (Title, Released, Price, Game)
values ('The Safari Bundle', to_date('14-Oct-2018','DD-MON-YYYY'), 4.99, 'Tom Clancy''s Rainbow Six Siege');

insert into DLC (Title, Released, Price, Game)
values ('Smoke Bushido Set', to_date('27-Nov-2016','DD-MON-YYYY'), 7.99, 'Tom Clancy''s Rainbow Six Siege');

insert into DLC (Title, Released, Price, Game)
values ('Blitz Bushido Set', to_date('03-Jan-2015','DD-MON-YYYY'), 7.99, 'Tom Clancy''s Rainbow Six Siege');

insert into DLC (Title, Released, Price, Game)
values ('Starter Pack', to_date('03-Jan-2015','DD-MON-YYYY'), 19.99, 'Warframe');

insert into DLC (Title, Released, Price, Game)
values ('Heavy IMpact Pinnacle Pack', to_date('09-Jun-2018','DD-MON-YYYY'), 16.79, 'Warframe');

insert into DLC (Title, Released, Price, Game)
values ('Reflex Guard Pinnacle Pack', to_date('23-Jan-2017','DD-MON-YYYY'), 33.99, 'Warframe');

insert into DLC (Title, Released, Price, Game)
values ('Retribution Pinnacle Pack', to_date('14-Feb-2016','DD-MON-YYYY'), 49.99, 'Warframe');

insert into DLC (Title, Released, Price, Game)
values ('Shock Absorbers Pinnacle Pack', to_date('10-Oct-2017','DD-MON-YYYY'), 84.99, 'Warframe');

insert into DLC (Title, Released, Price, Game)
values ('Mesa Prime Access', to_date('03-Jan-2015','DD-MON-YYYY'), 44.99, 'Warframe');

-- Publishers - Name, Founded, Employees
create table Publishers (
	Name varchar2(30) primary key,
	Founded date not null,
	Employees number(6) not null
);

insert into Publishers (Name, Founded, Employees)
values ('Valve', to_date('24-Aug-1996','DD-MON-YYYY'), 360);

insert into Publishers (Name, Founded, Employees)
values ('Daybreak Game Company', to_date('17-Dec-1997','DD-MON-YYYY'), 190);

insert into Publishers (Name, Founded, Employees)
values ('Starbreeze Publishing AB', to_date('21-Jun-1998','DD-MON-YYYY'), 650);

insert into Publishers (Name, Founded, Employees)
values ('Deep Silver', to_date('08-Apr-2002','DD-MON-YYYY'), 310);

insert into Publishers (Name, Founded, Employees)
values ('Ubisoft', to_date('12-Mar-1986','DD-MON-YYYY'), 13742);

insert into Publishers (Name, Founded, Employees)
values ('Digital Extremes', to_date('17-Nov-1993','DD-MON-YYYY'), 170);

select * from Publishers;

-- Editions - Name, Game, Price
create table Editions (
	Name varchar2(30) not null,
	Game varchar2(50) not null,
	Price number(6,2)
);

insert into Editions (Name, Game, Price)
values ('Prime Status Upgrade', 'Counter-Strike: Global Offensive', 13.25);

insert into Editions (Name, Game, Price)
values ('Ultimate Edition', 'PAYDAY 2', 50.99);

insert into Editions (Name, Game, Price)
values ('Redux', 'Metro 2033', 19.99);

insert into Editions (Name, Game, Price)
values ('Redux', 'Metro: Last Light', 19.99);

insert into Editions (Name, Game, Price)
values ('Starter Edition', 'FOR HONOR', 14.99);

insert into Editions (Name, Game, Price)
values ('Marching Fire Edition', 'FOR HONOR', 49.99);

insert into Editions (Name, Game, Price)
values ('Complete Edition', 'FOR HONOR', 99.99);

insert into Editions (Name, Game, Price)
values ('Starter Edition', 'Tom Clancy''s Rainbow Six Siege', 14.99);

insert into Editions (Name, Game, Price)
values ('Advanced Edition', 'Tom Clancy''s Rainbow Six Siege', 49.99);

insert into Editions (Name, Game, Price)
values ('Gold Edition', 'Tom Clancy''s Rainbow Six Siege', 79.99);

insert into Editions (Name, Game, Price)
values ('Complete Edition', 'Tom Clancy''s Rainbow Six Siege', 119.99);

select * from Editions;

-- Cart - Title, Description, Price
create table Cart (
	id number(4) primary key,
	Title varchar2(60) not null,
	Description varchar2(60) not null,
	Price number(6,2)
);

--View all:
select * from Games;
select * from DLC;
select * from Publishers;
Select * from Editions;
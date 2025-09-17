INSERT INTO destination(city, country) VALUES ("Rome", "Italy");
INSERT INTO destination(city, country) VALUES ("London", "England");
INSERT INTO destination(city, country) VALUES ("Berlin", "Germany");
INSERT INTO destination(city, country) VALUES ("New York", "United States");
INSERT INTO destination(city, country) VALUES ("Tokyo", "Japan");

insert into travel(price, hotel, destination_id) values (1000, "The Waldorf Hilton", 2);
insert into travel(price, hotel, destination_id) values (1200, "The Plaza", 4);
insert into travel(price, hotel, destination_id) values (1500, "Dai-ichi Hotel", 5);

INSERT INTO booking(customer, departure, weeks, hotel, destination_city, destination_country, total_price, status, travel_id)
VALUES("John Doe", "2025-10-05", 2, "The Plaza", "New York", "United States",
       1200 * 2, "UPCOMING", 2);
INSERT INTO user (create_ts,name,status,update_ts,email,first_name, last_name, password,user_name,user_status) VALUES
	 ('2022-02-10 09:08:32.581','admin','INSERT','2022-02-10 09:08:32.581','admin@localhost','admin', 'admin','$2a$10$5vw2bNRLaWP6lurWpqjb8uEmMw8wnf5mxI0B3nkGQarJ9Oizl52XO','admin','ENABLED'),
	 ('2022-02-10 09:08:32.581','manager01','INSERT','2022-02-10 09:08:32.581','manager01@localhost', 'manager01', 'manager01', '$2a$10$5vw2bNRLaWP6lurWpqjb8uEmMw8wnf5mxI0B3nkGQarJ9Oizl52XO','manager01','ENABLED'),
	 ('2022-02-10 09:08:32.581','manager02','INSERT','2022-02-10 09:08:32.581','manager02@localhost', 'manager02', 'manager02', '$2a$10$5vw2bNRLaWP6lurWpqjb8uEmMw8wnf5mxI0B3nkGQarJ9Oizl52XO','manager02','ENABLED'),
	 ('2022-02-10 09:08:32.581','user01','INSERT','2022-02-10 09:08:32.581','user01@localhost', 'user01', 'user01', '$2a$10$5vw2bNRLaWP6lurWpqjb8uEmMw8wnf5mxI0B3nkGQarJ9Oizl52XO','user01','ENABLED'),
	 ('2022-02-10 09:08:32.581','user02','INSERT','2022-02-10 09:08:32.581','user02@localhost', 'user02', 'user02', '$2a$10$5vw2bNRLaWP6lurWpqjb8uEmMw8wnf5mxI0B3nkGQarJ9Oizl52XO','user02','ENABLED'),
	 ('2022-02-10 09:08:32.581','user03','INSERT','2022-02-10 09:08:32.581','user03@localhost', 'user03', 'user03', '$2a$10$5vw2bNRLaWP6lurWpqjb8uEmMw8wnf5mxI0B3nkGQarJ9Oizl52XO','user03','ENABLED'),
	 ('2022-02-10 09:08:32.581','user04','INSERT','2022-02-10 09:08:32.581','user04@localhost', 'user04', 'user04', '$2a$10$5vw2bNRLaWP6lurWpqjb8uEmMw8wnf5mxI0B3nkGQarJ9Oizl52XO','user04','ENABLED');

INSERT INTO role (create_ts,user_name,user_role,user_id) VALUES
	 ('2022-02-10 09:08:32.581','admin','ADMIN', (SELECT id from user u WHERE u.user_name='admin')),
	 ('2022-02-10 09:08:32.581','manager01','MANAGER',(SELECT id from user u WHERE u.user_name='manager01')),
	 ('2022-02-10 09:08:32.581','manager02','MANAGER',(SELECT id from user u WHERE u.user_name='manager02')),
	 ('2022-02-10 09:08:32.581','user01','USER',(SELECT id from user u WHERE u.user_name='user01')),
	 ('2022-02-10 09:08:32.581','user02','USER',(SELECT id from user u WHERE u.user_name='user02')),
	 ('2022-02-10 09:08:32.581','user03','USER',(SELECT id from user u WHERE u.user_name='user03')),
	 ('2022-02-10 09:08:32.581','user04','USER',(SELECT id from user u WHERE u.user_name='user04'));

INSERT INTO author (create_ts,name, first_name, last_name, status,update_ts) VALUES
	 ('2022-02-17 18:00:00.0','Italo Calvino','Italo','Calvino','INSERT','2022-02-17 18:00:00.0'),
	 ('2022-02-17 18:00:00.0','Alessandro Baricco','Alessandro','Baricco','INSERT','2022-02-17 18:00:00.0');

INSERT INTO book (create_ts,description,name,status,update_ts,isbn_code,pub_year) VALUES
	 ('2022-02-17 18:00:00.0','Seta','Seta','INSERT','2022-02-17 18:00:00.0','9788433976598',1996),
	 ('2022-02-17 18:00:00.0','Il barone rampante','Il barone rampante','INSERT','2022-02-17 18:00:00.0','9788478444212',1957);

INSERT INTO book_authors (artist_id,item_id) VALUES
	 (2,1),
	 (1,2);

INSERT INTO musician (biography, first_name, last_name, name, status, create_ts, update_ts) VALUES
	('BIO', 'Jimi', 'Hendrix', 'Jimi Hendrix', 'Insert', '2022-02-17 18:00:00.0', '2022-02-17 18:00:00.0'),
	('BIO', 'Jimi', 'Page', 'Jimi Page', 'Insert', '2022-02-17 18:00:00.0', '2022-02-17 18:00:00.0');

INSERT INTO musician (biography, first_name, last_name, name, status, create_ts, update_ts) VALUES
	('BIO', 'Jimi', 'Hendrix', 'Jimi Hendrix', 'Insert', '2022-02-17 18:00:00.0', '2022-02-17 18:00:00.0'),
	('BIO', 'Jimi', 'Page', 'Jimi Page', 'Insert', '2022-02-17 18:00:00.0', '2022-02-17 18:00:00.0');

INSERT INTO band (create_ts, description, name, status, update_ts) VALUES
	('2022-02-17 18:00:00.0', 'Led Zeppelin', 'Led Zeppelin', 'INSERT', '2022-02-17 18:00:00.0');

INSERT INTO band_musician (musician_id, band_id) VALUES
	(2, 1);

INSERT INTO musician_genres (id, genre) VALUES
	(1, 'Rock'),
	(2, 'Rock');


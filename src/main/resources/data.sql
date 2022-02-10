INSERT INTO user (create_ts,name,status,update_ts,email,password,user_name,user_status) VALUES
	 ('2022-02-10 09:08:32.581','admin','INSERT','2022-02-10 09:08:32.581','admin@localhost','$2a$10$5vw2bNRLaWP6lurWpqjb8uEmMw8wnf5mxI0B3nkGQarJ9Oizl52XO','admin',0);
INSERT INTO user (create_ts,name,status,update_ts,email,password,user_name,user_status) VALUES
	 ('2022-02-10 09:08:32.581','manager01','INSERT','2022-02-10 09:08:32.581','manager01@localhost','$2a$10$5vw2bNRLaWP6lurWpqjb8uEmMw8wnf5mxI0B3nkGQarJ9Oizl52XO','manager01',0);
INSERT INTO user (create_ts,name,status,update_ts,email,password,user_name,user_status) VALUES
	 ('2022-02-10 09:08:32.581','manager02','INSERT','2022-02-10 09:08:32.581','manager02@localhost','$2a$10$5vw2bNRLaWP6lurWpqjb8uEmMw8wnf5mxI0B3nkGQarJ9Oizl52XO','manager02',0);
INSERT INTO user (create_ts,name,status,update_ts,email,password,user_name,user_status) VALUES
	 ('2022-02-10 09:08:32.581','user01','INSERT','2022-02-10 09:08:32.581','user01@localhost','$2a$10$5vw2bNRLaWP6lurWpqjb8uEmMw8wnf5mxI0B3nkGQarJ9Oizl52XO','user01',0);
INSERT INTO user (create_ts,name,status,update_ts,email,password,user_name,user_status) VALUES
	 ('2022-02-10 09:08:32.581','user02','INSERT','2022-02-10 09:08:32.581','user02@localhost','$2a$10$5vw2bNRLaWP6lurWpqjb8uEmMw8wnf5mxI0B3nkGQarJ9Oizl52XO','user02',0);
INSERT INTO user (create_ts,name,status,update_ts,email,password,user_name,user_status) VALUES
	 ('2022-02-10 09:08:32.581','user03','INSERT','2022-02-10 09:08:32.581','user03@localhost','$2a$10$5vw2bNRLaWP6lurWpqjb8uEmMw8wnf5mxI0B3nkGQarJ9Oizl52XO','user03',0);
INSERT INTO user (create_ts,name,status,update_ts,email,password,user_name,user_status) VALUES
	 ('2022-02-10 09:08:32.581','user04','INSERT','2022-02-10 09:08:32.581','user04@localhost','$2a$10$5vw2bNRLaWP6lurWpqjb8uEmMw8wnf5mxI0B3nkGQarJ9Oizl52XO','user04',0);

INSERT INTO role (create_ts,user_name,user_role,user_id) VALUES
	 ('2022-02-10 09:08:32.581','admin','ADMIN',SELECT id from USER u WHERE u.user_name='admin');
INSERT INTO role (create_ts,user_name,user_role,user_id) VALUES
	 ('2022-02-10 09:08:32.581','manager01','MANAGER',SELECT id from USER u WHERE u.user_name='manager01');
INSERT INTO role (create_ts,user_name,user_role,user_id) VALUES
	 ('2022-02-10 09:08:32.581','manager02','MANAGER',SELECT id from USER u WHERE u.user_name='manager02');
INSERT INTO role (create_ts,user_name,user_role,user_id) VALUES
	 ('2022-02-10 09:08:32.581','user01','USER',SELECT id from USER u WHERE u.user_name='user01');
INSERT INTO role (create_ts,user_name,user_role,user_id) VALUES
	 ('2022-02-10 09:08:32.581','user02','USER',SELECT id from USER u WHERE u.user_name='user02');
INSERT INTO role (create_ts,user_name,user_role,user_id) VALUES
	 ('2022-02-10 09:08:32.581','user03','USER',SELECT id from USER u WHERE u.user_name='user03');
INSERT INTO role (create_ts,user_name,user_role,user_id) VALUES
	 ('2022-02-10 09:08:32.581','user04','USER',SELECT id from USER u WHERE u.user_name='user04');
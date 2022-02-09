-- User user@email.pass/pass
INSERT INTO user (name, user_name, email, password, status, user_status, create_ts, update_ts, country)
  values ('Administrator', 'admin',
    'root@localhost',
    '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a',
    'INSERT',
    1, NOW(), NOW(), 'IT');

INSERT INTO authorities (user_name, authority, create_ts)
  values ('admin', 'ADMIN', NOW());
INSERT INTO cities.roles (role_id, name) VALUES ('d616255e-ef97-4dc8-90e5-367daa84b924', 'ALLOW_READ');
INSERT INTO cities.roles (role_id, name) VALUES ('da0246e4-466b-421d-8e1a-51c3c4b23d97', 'ALLOW_EDIT');

INSERT INTO cities.users (user_id, username, email, password, enabled) VALUES ('ce562bf0-f87c-4621-a322-59ec59500032', 'admin', 'admin@mail.com', '$2a$12$FyZE8cVW9ybgJPU5yjHz1.7EBrFTPo6.UghblFSjFSxYVDRpBbBGq', true);
INSERT INTO cities.users (user_id, username, email, password, enabled) VALUES ('2212211b-4c80-4e09-912e-e68e91afbbee', 'user', 'user@mail.com', '$2a$12$SlGW85N9PRQUwanQ6jnvDu8JtjYmNCJdh4jBvu1.ucsmC2j5EeEZG', true);

INSERT INTO users_roles (user_id, role_id) VALUES ('ce562bf0-f87c-4621-a322-59ec59500032', 'd616255e-ef97-4dc8-90e5-367daa84b924');
INSERT INTO users_roles (user_id, role_id) VALUES ('ce562bf0-f87c-4621-a322-59ec59500032', 'da0246e4-466b-421d-8e1a-51c3c4b23d97');
INSERT INTO users_roles (user_id, role_id) VALUES ('2212211b-4c80-4e09-912e-e68e91afbbee', 'd616255e-ef97-4dc8-90e5-367daa84b924');
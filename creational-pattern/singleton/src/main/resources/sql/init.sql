DROP TABLE IF EXISTS users;
CREATE TABLE users(
                      usr_id serial,
                      usr_username varchar(255),
                      usr_password varchar(255),
                      PRIMARY KEY (usr_id)
);
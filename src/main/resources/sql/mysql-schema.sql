# CREATE TABLE appuser (
#   id varchar(36) NOT NULL UNIQUE PRIMARY KEY ,
#   created_at bigint(20) NOT NULL,
#   updated_at bigint(20) NOT NULL,
#   email varchar(50) NOT NULL UNIQUE,
#   password varchar(500) NOT NULL
# );
#
# CREATE TABLE authority (
#   name VARCHAR(50) NOT NULL PRIMARY KEY
# );
#
# CREATE TABLE user_authority (
#     user_id VARCHAR(50) NOT NULL,
#     authority VARCHAR(50) NOT NULL,
#     FOREIGN KEY (user_id) REFERENCES appuser (user_id),
#     FOREIGN KEY (authority) REFERENCES authority (name),
#     UNIQUE INDEX user_authority_idx_1 (user_id, authority)
# );
#
# CREATE TABLE oauth_access_token (
#   token_id VARCHAR(256) DEFAULT NULL,
#   token BLOB,
#   authentication_id VARCHAR(256) DEFAULT NULL,
#   user_name VARCHAR(256) DEFAULT NULL,
#   client_id VARCHAR(256) DEFAULT NULL,
#   authentication BLOB,
#   refresh_token VARCHAR(256) DEFAULT NULL
# );
#
# CREATE TABLE oauth_refresh_token (
#   token_id VARCHAR(256) DEFAULT NULL,
#   token BLOB,
#   authentication BLOB
# );

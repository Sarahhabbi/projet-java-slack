CREATE TABLE IF NOT EXISTS users (
  user_id bigint unsigned auto_increment PRIMARY KEY,
  pseudo varchar(60) NOT NULL UNIQUE,
  password varchar(60) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS channels (
  channel_id bigint unsigned auto_increment PRIMARY KEY,
  admin_id bigint unsigned,
  name varchar(60) NOT NULL UNIQUE,
  CONSTRAINT admin_id_fk1 FOREIGN KEY (admin_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS channel_users (
  channel_id bigint unsigned,
  user_id bigint unsigned,
  PRIMARY KEY (channel_id, user_id),
  CONSTRAINT channel_users_fk1 FOREIGN KEY (channel_id) REFERENCES channels (channel_id),
  CONSTRAINT channel_users_fk2 FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS messages (
  message_id bigint unsigned auto_increment PRIMARY KEY,
  user_id bigint unsigned NOT NULL,
  channel_id bigint unsigned NOT NULL,
  message TEXT,
  date DATE NOT NULL,
  CONSTRAINT messages_fk1 FOREIGN KEY (user_id) REFERENCES users (user_id),
  CONSTRAINT messages_fk2 FOREIGN KEY (channel_id) REFERENCES channels (channel_id)    
);  

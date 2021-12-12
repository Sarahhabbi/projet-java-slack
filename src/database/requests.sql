 -- users
INSERT INTO users (pseudo, password) VALUES ("Siyani","password");
INSERT INTO users (pseudo, password) VALUES ("Doha","helloworld");
INSERT INTO users (pseudo, password) VALUES ("Shalom","flemme");
INSERT INTO users (pseudo, password) VALUES ("Sarah","coucou");


-- channels
INSERT INTO channels (name, admin_id) VALUES ("channel-1", 1);
INSERT INTO channels (name, admin_id) VALUES ("channel-2", 4);

-- channels_users 1
INSERT INTO channel_users (channel_id, user_id) VALUES (1,1);
INSERT INTO channel_users (channel_id, user_id) VALUES (1,4);
INSERT INTO channel_users (channel_id, user_id) VALUES (1,3);
-- channels_users 2
INSERT INTO channel_users (channel_id, user_id) VALUES (2,1);
INSERT INTO channel_users (channel_id, user_id) VALUES (2,4);

-- messages in channel 1
INSERT INTO messages (user_id, channel_id, message, date) VALUES (4,1,"channel 1 sarah : hello world flemme de faire un gros messages", CURRENT_TIMESTAMP);
INSERT INTO messages (user_id, channel_id, message, date) VALUES (4,1,"channel 1 sarah : la date devrait PAS fail", CURRENT_TIMESTAMP);
INSERT INTO messages (user_id, channel_id, message, date) VALUES (4,1,"channel 1 sarah : on test un truc lo avec la date", CURRENT_TIMESTAMP);
-- messages in channel 2
INSERT INTO messages (user_id, channel_id, message, date) VALUES (4,2,"channel 2 sarah : hello world  ", CURRENT_TIMESTAMP);
INSERT INTO messages (user_id, channel_id, message, date) VALUES (1,2,"channel 2 siyani : je suis pas dans ce channel mais je peux quand meme ecrire", CURRENT_TIMESTAMP);
INSERT INTO messages (user_id, channel_id, message, date) VALUES (2,2,"channel 2 doha : coucou je suis dans ce channel", CURRENT_TIMESTAMP);


SELECT * FROM users;
SELECT * FROM channels;
SELECT * FROM channel_users;
SELECT * FROM messages;


-- Select all last messages from a channel (ordered by DESC on DATE)
SELECT * FROM messages
WHERE channel_id=2
ORDER BY date DESC;






CREATE SCHEMA IF NOT EXISTS users;
CREATE SCHEMA IF NOT EXISTS messaging;

CREATE TABLE IF NOT EXISTS users.users(
    id SERIAL PRIMARY KEY,
    username VARCHAR,
    password VARCHAR
);

CREATE TABLE IF NOT EXISTS messaging.dialogs
(
    id SERIAL PRIMARY KEY,
    title VARCHAR
);

CREATE TABLE messaging.conversations(
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users.users(id),
    dialog_id INTEGER REFERENCES messaging.dialogs(id)
);

CREATE TABLE IF NOT EXISTS messaging.messages
(
    id SERIAL PRIMARY KEY,
    text VARCHAR,
    sender_id INTEGER REFERENCES users.users(id),
    dialog_id INTEGER REFERENCES messaging.dialogs(id)
);
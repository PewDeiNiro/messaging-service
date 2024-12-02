CREATE TABLE IF NOT EXISTS users.blocklist(
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users.users(id),
    block_id INTEGER REFERENCES users.users(id)
);
CREATE TABLE IF NOT EXISTS users.tokens(
    id SERIAL PRIMARY KEY,
    token VARCHAR,
    released_at TIMESTAMP,
    expiry_at TIMESTAMP,
    user_id INTEGER REFERENCES users.users(id)
);
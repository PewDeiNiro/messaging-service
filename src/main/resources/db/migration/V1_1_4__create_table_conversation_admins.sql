CREATE TABLE IF NOT EXISTS messaging.conversation_admins(
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users.users(id),
    dialog_id INTEGER REFERENCES messaging.dialogs(id)
);
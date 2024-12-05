ALTER TABLE messaging.messages ADD COLUMN type VARCHAR;
ALTER TABLE messaging.messages ADD COLUMN parent_message_id INTEGER REFERENCES messaging.messages(id);
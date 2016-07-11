DROP TABLE IF EXISTS todo;

CREATE TABLE todo (
  todo_id    BIGINT IDENTITY PRIMARY KEY,
  title      VARCHAR(255),
  is_done    BOOLEAN,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);
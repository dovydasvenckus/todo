DROP TABLE IF EXISTS list;

CREATE TABLE list (
  list_id     BIGINT IDENTITY PRIMARY KEY,
  title       VARCHAR(255),
  created_at  TIMESTAMP,
  updated_at  TIMESTAMP
);

DROP TABLE IF EXISTS todo;

CREATE TABLE todo (
  todo_id    BIGINT IDENTITY PRIMARY KEY,
  title      VARCHAR(255),
  is_done    BOOLEAN,
  list_id    BIGINT,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  FOREIGN KEY(list_id) REFERENCES list(list_id)
);

INSERT INTO list VALUES (1, 'Inbox', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
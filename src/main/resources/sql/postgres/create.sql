DROP TABLE IF EXISTS list;

CREATE TABLE list (
  list_id     SERIAL PRIMARY KEY,
  title       VARCHAR(255),
  created_at  TIMESTAMP,
  updated_at  TIMESTAMP
);

DROP TABLE IF EXISTS todo;

CREATE TABLE todo (
  todo_id    SERIAL PRIMARY KEY,
  title      VARCHAR(255),
  is_done    BOOLEAN,
  list_id    BIGINT REFERENCES list (list_id),
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);

INSERT INTO list VALUES (1, 'Inbox', localtimestamp, localtimestamp);
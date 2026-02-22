CREATE KEYSPACE IF NOT EXISTS tododb
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE IF NOT EXISTS tododb.todos (
    id text PRIMARY KEY,
    title text,
    completed boolean
);

DROP  TABLE IF EXISTS actors;

CREATE TABLE actors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    born_date DATE,
    movies_ids INTEGER ARRAY
);

INSERT INTO actors (id, first_name, last_name, born_date, movies_ids) VALUES
    (1, 'Tom', 'Hanks', '1956-07-09', ARRAY [1]),
    (2, 'Meryl', 'Streep', '1949-06-22', ARRAY [2, 1]),
    (3, 'Leonardo', 'DiCaprio', '1974-11-11', ARRAY [3, 1]),
    (4, 'Scarlett', 'Johansson', '1984-11-22', ARRAY [4, 1]),
    (5, 'Morgan', 'Freeman', '1937-06-01', ARRAY [5, 1]);

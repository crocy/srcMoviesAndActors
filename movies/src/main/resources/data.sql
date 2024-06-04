DROP  TABLE IF EXISTS movies;

-- Create movies table
CREATE TABLE movies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title TEXT NOT NULL,
    year_released INTEGER NOT NULL,
    description TEXT,
    actors_ids INTEGER ARRAY
);

INSERT INTO movies (title, year_released, description, actors_ids) VALUES
    ('The Shawshank Redemption', 1994, 'A former banker convicted of murder befriends a fellow prisoner as he tries to prove his innocence.', ARRAY [1, 2, 3]),
    ('The Godfather', 1972, 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', ARRAY [1, 2, 3]),
    ('The Dark Knight', 2008, 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', ARRAY [1, 2, 3]),
    ('Pulp Fiction', 1994, 'The lives of two mob hitmen, a boxer, a gangster`s wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', ARRAY [1, 2, 3]),
    ('Forrest Gump', 1994, 'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood love.', ARRAY [1, 2, 3]);

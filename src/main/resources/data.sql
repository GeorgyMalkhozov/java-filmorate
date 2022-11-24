-- заполняем список жанров по-умолчанию

MERGE INTO genres (genre_id, name) VALUES (1, 'Комедия');
MERGE INTO genres (genre_id, name) VALUES (2, 'Драма');
MERGE INTO genres (genre_id, name) VALUES (3, 'Мультфильм');
MERGE INTO genres (genre_id, name) VALUES (4, 'Триллер');
MERGE INTO genres (genre_id, name) VALUES (5, 'Документальный');
MERGE INTO genres (genre_id, name) VALUES (6, 'Боевик');

-- заполняем список MPA по-умолчанию

MERGE INTO mpa (mpa_id, name) VALUES (1, 'G');
MERGE INTO mpa (mpa_id, name) VALUES (2, 'PG');
MERGE INTO mpa (mpa_id, name) VALUES (3, 'PG-13');
MERGE INTO mpa (mpa_id, name) VALUES (4, 'R');
MERGE INTO mpa (mpa_id, name) VALUES (5, 'NC-17');

DELETE FROM FRIENDS;
DELETE FROM FILM_GENRE;
DELETE FROM LIKES;
DELETE FROM FILMS;
ALTER TABLE FILMS ALTER COLUMN FILM_ID RESTART WITH 1;
DELETE FROM USERS;
ALTER TABLE USERS ALTER COLUMN USER_ID RESTART WITH 1;

# java-filmorate

![ER диаграмма проекта](src/main/resources/ER_filmorate.jpg)

ER-диаграмма: https://lucid.app/lucidchart/12a2707a-df83-474f-b6f9-64bd1497e816/edit?viewport_loc=-108%2C215%2C1586%2C871%2CWAw2OXBzFWXX&invitationId=inv_8156e66a-4135-49da-b0fa-1267c9a8b022

## _Примеры запросов:_

## Фильмы (таблица 'films'):

- список всех фильмов:
```
SELECT * 
FROM FILMS;
```
- получить фильм:
```
SELECT * 
FROM FILMS 
WHERE FILM_ID = ?;
```
- изменить фильм:
```
UPDATE FILMS
SET NAME = ?,
    DESCRIPTION = ?,
    RELEASE_DATE = ?,
    RATE = ?,
    DURATION = ?,
    MPA_ID = ?
WHERE FILM_ID = ?;
```
- получить список популярных фильмов:
```
SELECT *
    FROM FILMS F
    LEFT JOIN (
               SELECT DISTINCT FILM_ID,
               COUNT(USER_ID) AS LIKECOUNT
               FROM LIKES
               GROUP BY FILM_ID) AS LIKETEMP on F.FILM_ID = LIKETEMP.FILM_ID
    ORDER BY LIKETEMP.LIKECOUNT DESC
    LIMIT ?;
```

## Пользователи (таблица 'users'):

- список всех пользователей:
```
SELECT * 
FROM USERS;
```
- получить пользователя:
```
SELECT * 
FROM USERS 
WHERE USERS_ID = ?;
```
- изменить пользователя:
```
UPDATE USERS
SET NAME = ?,
    LOGIN = ?,
    EMAIL = ?,
    BIRTHDAY = ?
WHERE USER_ID = ?;
```

## Друзья (таблица 'friends'):

- создать дружбу:
```
INSERT INTO FRIENDS 
SET USER_ID = ?, 
    FRIEND_ID = ?;
```
- удалить дружбу:
```
DELETE 
FROM FRIENDS 
WHERE USER_ID = ? AND FRIEND_ID = ?;
```
- получить список друзей пользователя:
```
SELECT FRIEND_ID 
FROM FRIENDS 
WHERE USER_ID = ?;
```

## Жанры (таблицы 'genres' и 'film_genre''):

- список всех жанров:
```
SELECT * 
FROM GENRES;
```
- получить жанр:
```
SELECT * 
FROM GENRES 
WHERE GENRE_ID = ?;
```
- задать жанры для фильма:
```
INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) 
VALUES (?, ?);
```
- удалить жанры фильма:
```
DELETE 
FROM FILM_GENRE 
WHERE FILM_ID = ?;
```


## MPA (таблица 'mpa'):

- список всех жанров:
```
SELECT * 
FROM MPA;
```
- получить жанр:
```
SELECT * 
FROM MPA 
WHERE MPA_ID = ?;
```

## Лайки (таблица 'likes'):

- создать лайк:
```
INSERT INTO LIKES 
SET USER_ID = ?, 
    FILM_ID = ?;
```
- удалить лайк:
```
DELETE 
FROM LIKES 
WHERE USER_ID = ? AND FILM_ID = ?;
```
- получить список лайков фильма:
```
SELECT USER_ID 
FROM LIKES 
WHERE FILM_ID = ?;
```
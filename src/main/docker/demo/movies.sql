-- Create Table ACTOR with Primary Key as ACT_ID

CREATE TABLE ACTOR
(
	ACT_ID   INTEGER PRIMARY KEY,
	ACT_NAME VARCHAR(20)
);

-- Create Table DIRECTOR with Primary Key as DIR_ID

CREATE TABLE DIRECTOR
(
	DIR_ID    INTEGER PRIMARY KEY,
	DIR_NAME  VARCHAR(20),
	DIR_PHONE VARCHAR(20)
);

-- Create Table MOVIES with Primary Key as MOV_ID and Foreign Key DIR_ID referring DIRECTOR table

CREATE TABLE MOVIES
(
	MOV_ID    INTEGER PRIMARY KEY,
	MOV_TITLE VARCHAR(25),
	MOV_YEAR  INTEGER,
	MOV_LANG  VARCHAR(15),
	DIR_ID    INTEGER,
	FOREIGN KEY (DIR_ID) REFERENCES DIRECTOR (DIR_ID)
);


-- Create Table MOVIE_CAST with Primary Key as MOV_ID and ACT_ID and Foreign Key ACT_ID and MOV_ID referring ACTOR and MOVIES tables respectively

CREATE TABLE MOVIE_CAST
(
	ACT_ID INTEGER,
	MOV_ID INTEGER,
	ROLE   VARCHAR(10),
	PRIMARY KEY (ACT_ID, MOV_ID),
	FOREIGN KEY (ACT_ID) REFERENCES ACTOR (ACT_ID),
	FOREIGN KEY (MOV_ID) REFERENCES MOVIES (MOV_ID)
);


INSERT INTO DIRECTOR (Dir_id, Dir_Name, Dir_Phone)
VALUES (1, 'Alfred Hitchcock', '555-0101'),
       (2, 'Stanley Kubrick', '555-0102'),
       (3, 'Christopher Nolan', '555-0103'),
       (4, 'Paul Verhoeven', '555-0104'),
       (5, 'George Sluizer', '555-0105');

-- Movies
INSERT INTO MOVIES (Mov_id, Mov_Title, Mov_Year, Mov_Lang, Dir_id)
VALUES (1, 'Psycho', 1960, 'English', 1),
       (2, 'The Shining', 1980, 'English', 2),
       (3, 'Inception', 2010, 'English', 3),
       (4, 'Turks Fruit', 1973, 'Dutch', 4),
       (5, 'Soldaat van Oranje', 1977, 'Dutch', 4),
       (6, 'Spetters', 1980, 'Dutch', 4),
       (7, 'RoboCop', 1987, 'English', 4),
       (8, 'Total Recall', 1990, 'English', 4),
       (9, 'The Fourth Man', 1983, 'Dutch', 4),
       (10, 'Basic Instinct', 1992, 'English', 4),
       (11, 'Starship Troopers', 1997, 'English', 4),
       (12, 'The Hitchcock', 1963, 'English', 1),
       (13, 'North by Northwest', 1959, 'English', 1),
       (14, 'Vertigo', 1958, 'English', 1),
       (15, '2001: A Space Odyssey', 1968, 'English', 2),
       (16, 'A Clockwork Orange', 1971, 'English', 2),
       (17, 'Eyes Wide Shut', 1999, 'English', 2),
       (18, 'Dunkirk', 2017, 'English', 3),
       (19, 'Interstellar', 2014, 'English', 3),
       (20, 'The Dark Knight', 2008, 'English', 3),
       (21, 'Spoorloos', 1988, 'Dutch', 5),
       (22, 'The Vanishing', 1993, 'English', 5),
       (23, 'De Aanslag', 1986, 'Dutch', 5),
       (24, 'Zwartboek', 2006, 'Dutch', 4),
       (25, 'De Lift', 1983, 'Dutch', 5);

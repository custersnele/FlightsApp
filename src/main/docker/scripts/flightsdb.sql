CREATE TABLE CARRIERS (cid varchar(7) PRIMARY KEY,
		       name varchar(83));

CREATE TABLE FLIGHTS (fid int PRIMARY KEY,
                      month_id int,        -- 1-12
                      day_of_month int,    -- 1-31
                      day_of_week_id int,  -- 1-7, 1 = Monday, 2 = Tuesday, etc
                      carrier_id varchar(7),
                      flight_num int,
                      origin_city varchar(34),
                      origin_state varchar(47),
                      dest_city varchar(34),
                      dest_state varchar(46),
                      departure_delay int, -- in mins
                      taxi_out int,        -- in mins
                      arrival_delay int,   -- in mins
                      canceled int,        -- 1 means canceled
                      actual_time int,     -- in mins
                      distance int,        -- in miles
                      capacity int,
                      price int            -- in $
                      );

LOAD DATA LOCAL INFILE '/var/lib/mysql-files/flights-small2.csv' into TABLE FLIGHTS FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 0 ROWS;
LOAD DATA LOCAL INFILE '/var/lib/mysql-files/carriers.csv' into TABLE CARRIERS FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 0 ROWS;

ALTER TABLE FLIGHTS
	ADD YEAR int default(year(now()));

-- Created a Customer table with the following attributes:
-- A User ID
-- The full name
-- A unique login handle
-- A password that allows the user to login.

CREATE TABLE CUSTOMER (
	    uid INT PRIMARY KEY,
	    name varchar(50) NOT NULL,
	    handle varchar(50) UNIQUE NOT NULL,
	    password varchar(50) NOT NULL
);

INSERT INTO CUSTOMER VALUES(2, 'James Bond', 'jb007', 'b0nd7');
INSERT INTO CUSTOMER VALUES(3, 'Tony Stark', 'tstark', 'iamironman');
INSERT INTO CUSTOMER VALUES(4, 'Bruce Banner', 'banner', 'iamhulk');

CREATE TABLE RESERVATION(
    uid int NOT NULL,
    fid int NOT NULL
);

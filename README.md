## Flight booking

For this exercise you develop a command-line application to search and book flights.
The template consists of the following classes and files:
-	**FlightsApp.java**: the main class responsible for user input and creating database-statements. An object of the class FlightsDB is used for executing the database statements. This main class is already complete
-	**FlightsDB.java**: this class offers methods to search and update the flights database. You need to implement this class.
-	**User.java, Flight.java and Itinerary.java are domain classes**. These classes are complete.
-	**docker-compose.yml and related sql-files** to create the flights database. For this exercise we use a docker container with a MariaDB database. From the directory src/main/docker run the command 'docker compose up' to create the container.
-	**dbconn.properties**: this property file contains all information for connecting to the flights database. You may need to update this file.

Start with updating the file dbconn.properties to connect to the flights database. Next, it's possible to run the application. The application provides a CLI (command-line interface). When you execute the command help you can see a list of supported commands:
Supported commands:
```
* login <handle> <password>
* search <origin-city> <dest-city> <day-of-month> (Day of month is 1-31 of July 2024)
* book <itinerary-num>
* reservations
* cancel <itinerary-num>
* quit
```
At the moment only 2 commands function correctly: search and quit. You need to implement JDBC statements in the class FlightsDB to get the application working.

Here is a short description of the different commands. Further implementation details are provided later in this document.
- **login** given a user's handle (short username) and password the system checks if the user is registered in the system. When the login details are correct, the user is logged in in the flight reservation system.
- **search** provides a list of itineraries (direct (one-hop) or with a stopover (two-hop)) to travel from origin-city to destination-city. The database only contains flights for July 2022. Therefore, you only provide the day-of-month. All search results have a unique identifier which can be used to book a flight.
- **book** uses a flights unique identifier to book the flight for the logged-in user. When you book a flight, it's called a reservation.
- **reservations** all reservations for the logged-in user.
- **cancel** cancel a reservation

###  Implementation details

#### Task 1: Login

Implement the method logIn in class FlightsDB.java. Write and execute a query to check if the given handle and password exist in table Customer.  If the user exists, you return a User-object with the user's information. If the user doesn't exist, you return null, and the login will fail.
Now it should be possible to execute the login-command. The users that can login in the flight reservation system can be found in the file flightsdb.sql.

#### Task 2: Stop SQL Injection

The search-command is already implemented. Run the following instruction:

```
  search "Seattle WA" "Boston MA" 10
```

Next, try the following command:

```
  search "Seattle WA" "Boston MA' AND F1.carrier_id = 'AA" 10
```

The executed query uses the following value for destination "Boston MA' AND F1.carrier_id = 'AA"". Although this destination doesn't exist in the database, the query can be executed without an error. The resulting flights are limited to flights carried out by American Airlines (AA). This is an example of SQL injection. Rewrite the queries so the application is no longer vulnerable to SQL injection.

#### Task 3: Book a flight

Implement the method addReservations in class FlightsDB. A logged-in user can enter the unique identification for an itinerary. When a user performs a search, the results are saved in a data structure in the class FlightsApp). Multiple Flight-objects can belong to one itinerary. You must save one record for every Flight in the Reservation table. There are some constraints you must keep in mind:
1.	A user can book only one itinerary per day. So, when there is already a reservation for the user on the given day, you may not register another booking.
2.	For every flight, only 3 reservations are allowed.

In the class FlightsDB.java you find some possible error codes. Return the appropriate error code when the constraints are not fulfilled.
When all the constraints are fulfilled, it's possible to create the reservations and to save them in the database. All these insert-statements should be executed in one transaction. You can test transaction management when you ask for user input between the INSERT-queries and the actual commit of the transaction.
Show the message "Press any key to continue..." and wait for user input. You open another terminal and start the program a second time. The same user logs in and books the same itinerary while the first program is still waiting to commit. If everything is implemented correctly, the second program cannot book the flights.

#### Task 4: Display reservations
Implement the method getReservations in FlightsDB.java. This method returns an overview of all the reservations created by the logged-in user. The method returns a list with information on booked flights. For every booked flight, a new Flight object with the necessary information is created. Test the reservations command thoroughly.

#### Task 5: Cancel a reservation
Implement the method removeReservations in FlightsDB.java. This method removes all the reservations for a given itinerary.
You should use transactions. This way you avoid that only part of the itinerary is deleted.

### Sample output

```
Enter a command (type 'help' for usage information):
> login jb007 b0nd7
Hello, James Bond!

> search "Miami FL" "Atlanta GA" 23
1 23-jul.-2022 Miami        Baltimore    American Airlines Inc. 313
Baltimore    Atlanta      Delta Air Lines Inc. 1100
2 23-jul.-2022 Miami        Tampa        American Airlines Inc. 1461
Tampa        Atlanta      Southwest Airlines Co. 319
3 23-jul.-2022 Miami        Tampa        American Airlines Inc. 1461
Tampa        Atlanta      Delta Air Lines Inc. 1709
4 23-jul.-2022 Miami        Tampa        American Airlines Inc. 1461
Tampa        Atlanta      Delta Air Lines Inc. 1903
5 23-jul.-2022 Miami        Tampa        American Airlines Inc. 1461
Tampa        Atlanta      AirTran Airways Corporation 122
…
91 23-jul.-2022 Miami        Houston      American Airlines Inc. 1579
Houston      Atlanta      ExpressJet Airlines Inc. 5220
92 23-jul.-2022 Miami        Atlanta      AirTran Airways Corporation 506
93 23-jul.-2022 Miami        Atlanta      American Airlines Inc. 349
94 23-jul.-2022 Miami        Atlanta      AirTran Airways Corporation 62
95 23-jul.-2022 Miami        Atlanta      AirTran Airways Corporation 338
96 23-jul.-2022 Miami        Atlanta      Delta Air Lines Inc. 74
97 23-jul.-2022 Miami        Atlanta      American Airlines Inc. 1028
98 23-jul.-2022 Miami        Atlanta      American Airlines Inc. 2076
99 23-jul.-2022 Miami        Tampa        American Airlines Inc. 1461
Tampa        Atlanta      Delta Air Lines Inc. 2372

> book 91
Successfully booked flights

> reservations
1 23-jul.-2022 Miami        Houston      American Airlines Inc. 1579
Houston      Atlanta      ExpressJet Airlines Inc. 5220

> search "Atlanta GA" "Houston TX" 28
1 28-jul.-2022 Atlanta      Houston      Delta Air Lines Inc. 439
2 28-jul.-2022 Atlanta      Houston      Delta Air Lines Inc. 1194
…
96 28-jul.-2022 Atlanta      New Orleans  Delta Air Lines Inc. 1277
New Orleans  Houston      Southwest Airlines Co. 2171
97 28-jul.-2022 Atlanta      Tulsa        Delta Air Lines Inc. 1609
Tulsa        Houston      Southwest Airlines Co. 3828
98 28-jul.-2022 Atlanta      New Orleans  Delta Air Lines Inc. 1419
New Orleans  Houston      Southwest Airlines Co. 2171
99 28-jul.-2022 Atlanta      New Orleans  Delta Air Lines Inc. 1970
New Orleans  Houston      Southwest Airlines Co. 2171

> book 97
Successfully booked flights

> reservations
1 28-jul.-2022 Atlanta      Tulsa        Delta Air Lines Inc. 1609
Tulsa        Houston      Southwest Airlines Co. 3828
2 23-jul.-2022 Miami        Houston      American Airlines Inc. 1579
Houston      Atlanta      ExpressJet Airlines Inc. 5220

> cancel 2
Successfully canceled flights

> reservations
1 28-jul.-2022 Atlanta      Tulsa        Delta Air Lines Inc. 1609
Tulsa        Houston      Southwest Airlines Co. 3828
```

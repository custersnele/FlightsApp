## JDBC

### Database for demo code
MySQL database
```
cd src/main/docker/demo
docker compose up
```

### Exercise 1

Create a standalone Java program that accomplishes the following:

- Read data from a CSV file: The file is named actors_and_roles.csv, and it's located in the resources folder of your project.
- Connect to the movie database
- Process the files data in a transaction. Ensure that all the data from the CSV file is inserted into the database successfully, or if any errors occur during insertion, no data is committed. 
- Using JDBC the program should insert the extracted actor data (name and role) into the appropriate tables in the movie database.

### Exercise 2

Establish a connection to the movie database.
Write a SQL query to retrieve a list of all unique actors present in the database. Utilize JDBC classes to execute the query and display the retrieved data.

Prompt the user to enter the name of an actor they want to find information about.

If the entered actor exists, display a message indicating the actor's name and then list all the movies they starred in. Display the role the actor played in each movie.
If the entered actor is not found, display a message informing the user that the actor was not found in the database.

Remember to include proper error handling for potential database connection issues or query execution failures.

### Exercise 3: Flights

MariaDB database
```
cd src/main/docker/flights
docker compose up
```

Exercise: [flights exercise](flights.md)

# Dice Distribution Simulation Application

## Running the application locally
Dice Distribution Simulation is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). You can build a jar file and run it from the command line:


```
git clone https://github.com/serkancaglar/dice-distribution-simulation.git
cd dice-distribution-simulation
./mvnw package
mvnw.cmd package
java -jar target/*.jar
java -jar target/dice-distribution-simulation-0.0.1-SNAPSHOT.jar
```

## Endpoints

Dice Distribution Simulation Application has 4 endpoints which are;

<ul>

<li><a href="http://localhost:8080/dice-distribution-simulation/api/v1/roll/default">Roll dice with default values</a></li>

<li><a href="http://localhost:8080/dice-distribution-simulation/api/v1/roll/custom">Roll dice with custom values</a></li>

<li><a href="http://localhost:8080/dice-distribution-simulation/api/v1/get/all">Query all results grouped by dice number and dice side</a></li>

<li><a href="http://localhost:8080/dice-distribution-simulation/api/v1/get/relative-distribution/3/6">Get relative distribution for the given dice number and dice side. Sample URL for 3-6 combination</a></li>

</ul>

To reach the Swagger UI, please follow the link below.

<a href="http://localhost:8080/dice-distribution-simulation/api/v1/swagger-ui/index.html">Swagger UI</a>

Also, a Postman collection can be found in the root directory of the project.

## Database configuration

In its default configuration, the application uses an in-memory database (H2) which can be accessed via below link.

<a href="http://localhost:8080/dice-distribution-simulation/h2-console">Open H2 console</a>

Value for the Jdbc URL is the default value which is jdbc:h2:mem:testdb

## Solution


Some assumptions and decisions are made for this assignment.


<ul>
	<li>
	<p>In order to keep the computations in the range of long type, some constraints are applied to the input parameters which are numberOfDice, numberOfSidesOfDice and numberOfTotalRuns.</br>
	numberOfDice and numberOfTotalRuns should be in the range <b>1-100000</b> whereas numberOfSidesOfDice should be between <b>4</b> and <b>1000</b>.</p>
	</li>
	<li>
	<p>Even with these constraints, computing total rolls may overflow long type which will cause inaccurate results.</br>
	Total rolls value is computed using the numberOfTotalRuns which is constrained and number of calls to the endpoint which is not, therefore overflow may occur after sufficient calls.<br/>
	To prevent overflowing, BigInteger can be used when overflow happens. In this application this case is not handled.</p>
	</li>
	<li>
	Rolling dice endpoints returns the sum of the rolled numbers and count of them as a map structure.<br/>
	These endpoints does not return all the possible sum values for the given dice number and dice side parameters but only the sums that are actually obtained.<br/>
	For example, assume that dice number is 3, dice side is 6 and number of rolls is 2. Then possible sum values are from 3 to 18. And assume that sums of those 2 rolls are 7 and 11.<br/>
	The possible sums that are not obtained by these rolls are not returned in the result (Their count values would be 0 in the result). This decision is made to prevent large payloads generated for large inputs. For this case the returned result is;
	</li>
</ul>

	{
		"7": 1,
		"11": 1
	}


<ul>
	<li>
	<p>H2 in memory database is used for this application. Therefore, data will not be persisted when the application is stopped</p>
	</li>
</ul>

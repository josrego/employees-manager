# Employees Manager

This API has as objective to be run as a microservice, 
in order to store and find employees.

## Data Model
The Data model is pretty simple it's composed by two entities: Employee and Contract
The employee entity is characterized by the following attributes:
* First Name
* Last Name
* Age

Every employee is has also an associated contract which has the following attributes:
* Contract Expiration Date

As contract entity only has one attribute, it could have been joined with employee entity, however in order to keep future
extensibility for the contracts i have chosen to leave this as it is. 

Note that no unique constraint was applied to employees, as even if by remote chance, two persons can have the same name
and age with the same contract expiration date.

## Technologies Used
* Quarkus: a full stack java framework that is similar to spring boot, however it's not as dependency heavy
and has a far lesser memory footprint. Also supports changes on the fly while developing without
the need to recompile and run again.
* Mutiny: A reactive programming library that allows to express and
 compose asynchronous actions.
* PostgresSQL: Standard SQL database. I used this as it was the DB i'm most confortable with.
* testcontainers: Library that i used to do integration tests. This will create a database in a container in order to 
test all endpoints in all of they're entirety. 

Note: I choosed not to use an ORM such as hibernate as it thought it would be overkill for just storing two simple entities.
In a far more complex data model i would have probably choose to use it.
## Installation
### Database
The supported database currently is PostgresSQL.
A db schema script is present in the resources folder under `db/schema.sql`
The database schema can be automatically created when starting the service if `schema.create` is set as true in
`application.propeties`

### API
The webservice can be started locally in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

### Docker
A docker image can be build using the following command:
`./mvnw clean package -Dquarkus.container-image.build=true`

## Endpoints
This API supports the following endpoints:

### Create Employee - POST /employees
This will create a new employee based on the JSON body sent.
When creating an id will be generated.
#### Possible responses:
* 201 - Created successfully
* 400 - Bad request. JSON was malformed.

#### example:
```
curl --request POST \
  --url http://localhost:8080/employee \
  --header 'content-type: application/json' \
  --data '{
	"firstName": "Antonio",
	"lastName": "Manuel",
	"age": 1,
	"contract": {
		"expirationDate": "23-09-2020"
	}
}'
```

### Find employees - GET /employees
This will find all employees (not that no limit is applied).

#### Query params supported
* `firstName` - this will filter all employees with the exact fist name match of the value inputted
* `lastName` - this will filter all employees with the exact last name match of the value inputted
* `contractExpirationDate` - this will filter all employees with the exact contract expiration date match of the value inputted

#### Possible Responses
* 200 - successful request

#### Example
```
curl --request GET \
  --url http://localhost:8080/employees
```

### Find employees order by days until expiration date - GET /employees/orderBy/contractExpirationDate
This will find all employees, order them by expiration date ASC 
and will calculate the days until the contract expires.

#### Query params supported
* `firstName` - this will filter all employees with the exact fist name match of the value inputted
* `lastName` - this will filter all employees with the exact last name match of the value inputted

#### Possible Responses
* 200 - successful request

#### Example
```
curl --request GET \
  --url 'http://localhost:8080/employees/orderBy/contractExpirationDate?lastName=Manuel'
```

## Improvements
* The integration tests can be refined and expanded upon;

* Currently 'find employees' endpoint is accepting a string from 'contractExiprationDate' query param 
instead of a LocalDateTime. There's a bug where REASTEASY is not able to serialize the incoming json value to a LocalDateTime
object 

* Refinement of the docker image creation. A dockerfile should be created
and a docker-compose in order to have a more direct way of running the service in a containerized environment.
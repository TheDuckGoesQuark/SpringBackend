# Junior Honours Project

A back end for the JH project, providing metaFile storage, image processing, and username permissions and groups.
## Getting Started

These instructions will get you a copy of the Started up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project_name on a live system.

### Prerequisites

What things you need to install the software and how to install them

* Git
* Java
* Maven


### Installing

A step by step series of examples that tell you have to get a development env running

Clone the repo to your directory of choice:

```
git clone https://gitlab.cs.st-andrews.ac.uk/cs3099group-be-5/project-code.git
```

>If using Intellij as your IDEA, you can use the import project option 
>and select the pom.xml 
>metaFile as build instructions.

Install the necessary packages and build the project:
> Note: The test suite will be ran automatically after building to 
> confirm the success of the project.
> You can avoid this by including the flag: 
>
>```
>-DskipTests
>```

```
mvn clean install
```

To deploy the application (tests can be skipped as above)
```
mvn clean package // Package the application
cd target // Move into where the packaged application exists 
java -jar JH-BE7-0.1.0.jar // Run package (0.1.0 is the version number, which may be different)
```

With the project now running, go to:

[http://localhost:8080/swagger-ui.html/](http://localhost:8080/swagger-ui.html/)

To see examples of how to use the API.

## Project Structure

### src/
BE package includes all source files for back end.
#### advices/
Classes for intercepting method calls to other classes. Used to wrap error and success responses 
according to protocol
#### aspects/
Classes for monitoring method calls. Used for performance monitoring by timing method execution.
#### controllers/
REST Controller classes, which maps URLS to the corresponding service call, and extracts any data
from the request body.
#### entities/
Hibernate entities for ORM from database. Different to 'models' which are used for transforming 
these entities into the requested structure for the controllers.
#### exceptions/
All exceptions extend base exception class, and contain the fields required by the Error response wrapper. 
#### repositories/
Repositories provide an interface into the DB, and simplify requests.
#### responsemodels/
Classes for mapping entities to expected response models and the reverse.
#### security/
Package contains custom implemenation of Spring Security interfaces, implementing a simple JWT system.
#### services/
Services remove business logic from controllers, and provide the actual implementation and
an interface for the controllers.     
#### resources/
Contains properties and other static files such as database connection details.
#### test/
Contains tests for various parts of the server.

## Running the tests

To run all tests, run the following command from the root directory of the project (/project-code):
```
mvn test
```

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

## Deployment

For deployment, the database connection details will have to be confirmed. These can be found
in resource/application.properties.


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Boot](https://projects.spring.io/spring-boot/) - Server Framework
* [MariaDB](https://mariadb.org/) - Relational Database
* [Hibernate](http://hibernate.org/) - Object Relational Mapping
* [Swagger](https://swagger.io/) - REST Service Documentation

## Version Control

We used [Gitlab](https://gitlab.cs.st-andrews.ac.uk/) for VC. For the versions available, see the [tags on this repository](https://gitlab.cs.st-andrews.ac.uk/). 

## Authors

* **Jordan Mackie**
* **Нестор Динолов**
* **Tim Delaney Jnr**
* **Thiadmar Jansen**
* **Arthur Gildehaus**

## Acknowledgments

* Susmit Sarkar for supervising and helping us with the project.
* The many contributors to the huge [Baeldung](http://www.baeldung.com/) collection of Spring tutorials.
* Anil KC for a [thorough guide on custom spring security for rest services.](http://anilkc.me/securing-spring-based-rest-services-spring-security/)
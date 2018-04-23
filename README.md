# Junior Honours Project

A back end for the JH project_name, providing metaFile storage, image processing, and username permissions and groups.
## Getting Started

These instructions will get you a copy of the project_name up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project_name on a live system.

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
according to protocol.
###### ErrorResponseWrapper/
A response wrapper for errors and exceptions with http requests.
###### JSONAdvisor/
Serialises the response to JSON, structures the response in expected format.
###### JSONErrorAdvisor/
Final call before serialising response to JSON.
###### ResponseWrapper/
The response wrapper itself.

#### aspects/
Classes for monitoring method calls. Used for performance monitoring by timing method execution.
###### TimerAspect/
This contains performance tracking for controllers (logging).

#### config/
Configuration classes for starting up the application itself.
###### ApplicationStartup/
This adds userModels to the database for startup functionality.
###### ContentNegotiationConfig/
This ensures that the right file paths are taken in accordance with file extensions.


#### controllers/
REST Controller classes, which maps URLS to the corresponding service call, and extracts any data
from the request body.
###### AccessRight/
A class for storing the REGULAR and ADMIN strings in regards to access level
###### Action/
A class for initialising strings in regards to actions with requests.
###### MediaType/
A class for setting up the mediaType structures expected.
###### ProjectController/
This is the where all project and file http requests are handled.
###### SystemController
This is where logging for http requests are handled.
###### UserController/
This is where all user http requests are handled.

#### entities/
Hibernate entities for ORM from database. Different to 'models' which are used for transforming 
these entities into the requested structure for the controllers.
##### project/
Containing the classes to do with the project structure.
###### tabular/
A folder containing two classes for the header and row count.
###### FileStatus/
A class for initialising strings for the file status
###### FileTypes/
A class for initialising strings for the file types
###### MetaFile/
Initialising a MetaFile object which is able to create files and directories.
###### Project/
A class for initialising a Project object.
###### Role/
Initialising the Role object.
###### SupportedView/
A class for initialising the SupportedView object.
##### security/
The security token for bearer authentication.
###### Token/
The class for the token itself used to authenticate the user.
##### user/
A folder containing classes for the the user properties.
###### MetaData/
The user's metadata.
###### UserProject/
Projects the user is involved in.
###### UserProjectPK/
The IDClass for UserProject class

#### exceptions/
All exceptions extend base exception class, and contain the fields required by the Error response wrapper. 

#### models/
Classes for mapping entities to expected response models and the reverse.
##### files/
###### supportedviewinfoobjects/
A number of classes that contain objects relevant for SupportedView
###### FileModel/
The FileModel object class.
###### FileRequestOptions/
A class for uploading and downloading files.
###### MoveFileRequestModel/
A class for the implementation of moving files.
###### SupportsViewModel/
The SupportsViewModel object class.
##### project/
A folder containing classes on the ProjectModel, the ProjectRoleModel, and the UserListModel.
##### security/
A folder containing classes on the TokenHeaderModel, the TokenModel, and the TokenRequest.
##### system/
A folder containing classes on the LoggingModel, the PropertyModel, and the SupportedProtocolListModel.
##### user/
A folder containing classes on the AvailabilityModel, the PrivilegeModel, the ProjectListModel and the UserModel.
###### JsonViews/
A class for the interface in regards to User/Admin/CurrentUser views.
###### MetaDataModel/
A class for the MetaDataModel object.

#### repositories/
Repositories provide an interface into the DB, and simplify requests.
###### ColumnHeaderRepository/
The interface for the column header.
###### FileRepository/
The interface for handling files.
###### PrivilegeRepository/
The interface for changing a users privilege.
###### ProjectRepository/
The interface projects.
###### RoleRepository/
The interface for roles.
###### SupportedViewRepository/
The interface for upportedView.
###### TokenRepository/
The interface for handling tokens.
###### UserProjectRepository/
The interface for handling user projects.
###### UserRepository/
The interface for handling users.

#### security/
Package contains custom implemenation of Spring Security interfaces, implementing a simple JWT system.
##### enums/
A folder containing the enums of AutheticanFailureType(s), GrantTypes, as well as initialising privilege strings.
##### handlers/
A folder containing handlers for 'AccessDenied', 'LoginFailure' as well as 'LoginSuccessful'.
##### passwordAuth/
A folder containing classes to handle authentication of the username and password.
##### passwordHash/
This is for hashing the password of the user.
##### tokenAuth/
A folder containing classes to handle tokens for authentication.
###### AuthenticationEntryPoint/
A class to commence the authetication request.
###### CORSFilter/
This is for internal filtering of the authentication request.
###### SecurityConfig/
This is the set up of the security authentication handling.
###### SecurityUtils/
A class with extra utility strings and functions for use with security.
###### UserAdapter/
A class for handling the user's details in conjunction with security.

#### services/
Services remove business logic from controllers, and provide the actual implementation and
an interface for the controllers.
###### FileService/
An interface for providing methods for retrieving project files.
###### FileServiceImpl/
The implementation of FileService.
###### ProjectService/
An interface for handling and retrieving Projects.
###### ProjectServiceImpl/
The implementation of ProjectService.
###### StorageService/
An interface for the storage and distribution of files.
###### StorageServiceImpl/
The implementation of StorageService.
###### TokenService/
An interface for handling tokens and retrieving user's by token Id.
###### TokenServiceImpl/
The implementation of TokenService.
###### UserService/
An interface for handling users.
###### UserServiceImpl/
The implementation of UserService.

#### util/
Utility classes for Tabular parser, as well as metafiles.
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
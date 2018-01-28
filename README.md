# Junior Honours Project

A back end for the JH project, providing file storage, image processing, and user permissions and groups.
## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
Give examples
```

### Installing

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Project Structure

* main
    * java
        * BE
            > BE package includes all source files for back end.
            * advices
                > Classes for intercepting method calls to other classes. Used to wrap error and success responses 
                according to protocol
            * aspects
                > Classes for monitoring method calls. Used for performance monitoring by timing method execution.
            * config
                > Configuration classes for Spring, such as security endpoints and filters.
            * controllers
                > REST Controller classes, which maps URLS to the corresponding service call, and extracts any data
                from the request body.
            * entities
                > Hibernate entities for ORM from database. Different to 'models' which are used for transforming 
                these entities into the requested structure for the controllers.
            * exceptions
                > All exceptions extend base exception class, and contain the fields required by the Error response wrapper. 
            * repositories
                > Repositories provide an interface into the DB, and simplify requests.
            * services
                > Services remove business logic from controllers, and provide the actual implementation and
                an interface for the controllers. 
        * ClientTest
            > A basic front end client for testing the server.     
    * resources
        > Contains properties and other static files such as database connection details.
* test
    > Contains tests for various parts of the server.

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc

# Simple User

Provides a simple set of rest endpoints to access information about simple user objects:

    GET        /users
    POST       /users
    DELETE     /users
    PUT        /users


Requirements
---

- Java 8
- Maven 3
- Mongodb


How to Start
---

Use `mongod` on the command line to start your database, ensure that your mongodb is running on the default port (`27017`) before
 you run the application (or even the integration tests).

1. Run `mvn spring-boot:run` on the command line

The services will be accessible at `http://localhost:8080`


Documentation
---

The beautiful [Swagger](https://github.com/swagger-api) has been used to document this project, the documentation
can be found at `http://localhost:8080/swagger-ui.html` or in JSON form at `http://localhost:8080/v2/api-docs` if
you are a robot.


Improvements
---

1. Make integration tests run with an embedded database
1. Queueing needs to be tested properly - cannot find a decent way of verifying that the message is present on the queue
1. Queue names should be mutable via properties
1. Ideally queue would be a totally separate concern, but for a contained example it is fine to be in the same runtime
as this service
1. Pagination for `findAll()`
1. Find users by criteria
1. Security has not been implemented at all, the service should be able to register other services and have the endpoints
accessed via an api_key (if exposed), or perhaps this would be on an internal network, in which case this is less of a concern

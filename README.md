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


How to Start
---

1. Run `mvn spring-boot:run` on the command line

The services will be accessible at `http://localhost:8080`


Documentation
---

The beautiful [Swagger](https://github.com/swagger-api) has been used to document this project, the documentation
can be found at `http://localhost:8080/swagger-ui.html` or in JSON form at `http://localhost:8080/v2/api-docs` if
you are a robot.


Improvements
---

1. Queueing needs to be tested properly - cannot find a decent way of verifying that the message is present on the queue
1. Queue names should be mutable via properties
1. Ideally queue would be a totally separate concern, but for a contained example it is fine to be in the same runtime
as this service
1. Pagination for `findAll()`
1. Find users by criteria
1. Security has not been implemented at all, the service should be able to register other services and have the endpoints
accessed via an api_key (if exposed), or perhaps this would be on an internal network, in which case this is less of a concern
1. Longer term database such as mongodb or mysql (depends what we're using this for) would be needed down the line
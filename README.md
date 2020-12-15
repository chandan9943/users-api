```                                                                      
#             .___      __     _____          __                         __  .__               
#    ______ __| _/_____/  |_  /  _  \  __ ___/  |_  ____   _____ _____ _/  |_|__| ____   ____  
#   /  ___// __ |/ __ \   __\/  /_\  \|  |  \   __\/  _ \ /     \\__  \\   __\  |/  _ \ /    \ 
#   \___ \/ /_/ \  ___/|  | /    |    \  |  /|  | (  <_> )  Y Y  \/ __ \|  | |  (  <_> )   |  \
#  /____  >____ |\___  >__| \____|__  /____/ |__|  \____/|__|_|  (____  /__| |__|\____/|___|  /
#       \/     \/    \/             \/                         \/     \/                    \/ 
```
# users-api
Sample project using Spring Boot 2 and Java

Introduction
------------
This project is made for anyone who is looking for an example of how to create a rest endpoint using Java Spring Boot & Gradle.

This service calls a local h2 database. Please see resources/data.sql directory for more details. 

This project was written using IntelliJ Community Edition.   


Installing Project Dependencies
-----
[This project uses gradle build tool.](https://gradle.org/)

If you are on MacOS you can install the dependency using homebrew:

`brew install gradle`

However, this project implements a gradle wrapper, all gradle commands can be executed using the [gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html). 


Running the application
-----
After you have performed all the dependency installations from above, you can run the following command on your terminal
to start this app.

From the root of this project enter the following terminal command:

build & test: `./gradlew clean build`

running the application locally: 

`./gradlew bootrun`

or 

`java -jar ./build/libs/users-0.0.1-SNAPSHOT.jar`

Or you can also start the application within IntelliJ.  


Project Database
-----
This project uses a local [h2](https://www.h2database.com/html/main.html) database for a repository.  


Swagger
-----
This project contains a swagger ui.  

[For more information regarding swagger. Click here.](https://swagger.io/)

[For more information regarding SpringFox. Click here.](https://springfox.github.io/springfox/)

To view this api's swagger ui, run this application locally, then navigate to [http://localhost:8080/swagger-ui.html]

This app is also deployed using [heroku](https://www.heroku.com/).  To view deployed api swagger navigate to [https://springboot-users-api.herokuapp.com/swagger-ui.html](https://springboot-users-api.herokuapp.com/swagger-ui.html)

You can test out this api entirely from the swagger ui page. 


Rest Api 
-----

#### Users Api - local

GET - getAll - local: [/users/v1](http://localhost:8080/users/v1)

GET - getById  - local: [/users/v1/{userId}](http://localhost:8080/users/{userId})

GET - getByUsername - local: [/users/v1/byusername/{username}](https://localhost:8080/users/v1/byusername/{username})

POST - createUser - local [/users/v1](http://localhost:8080/users/v1}) + include a json body with fields

PUT - updateUser - local: [/users/v1/{userId}](http://localhost:8080/users/v1/{userId}) + include a json body with fields.

DELETE - deleteUser - local: [/users/v1/{userId}](http://localhost:8080/users/v1/{userId})

#### Users Api - deployed on heroku

GET - getAll - local: [/users/v1](https://springboot-users-api.herokuapp.com/users/v1)

GET - getById  - local: [/users/v1/{userId}](https://springboot-users-api.herokuapp.com/users/v1/{userId})

GET - getByUsername - local: [/users/v1/byusername/{username}](https://springboot-users-api.herokuapp.com/users/v1/byusername/{username})

POST - createUser - local [/users/v1](https://springboot-users-api.herokuapp.com/v1/users}) + include a json body with fields

PUT - updateUser - local: [/users/v1/{userId}](hhttps://springboot-users-api.herokuapp.com/users/v1/{userId}) + include a json body with fields.

DELETE - deleteUser - local: [/users/v1/{userId}](https://springboot-users-api.herokuapp.com/users/v1/{userId})


TDD - Integration Tests
-----
This api is fully tested with Unit Tests and Integration tests.  Please see [tests directory](src/test/java/com/sdet/auto/users) for examples.

Test's connects to h2 database for integration tests.

    
Spring Boot Project
-----
This project is a Spring Boot project. [For more information click here](https://spring.io/projects/spring-boot)
    
    
Docker
-----
This application can be run in [Docker](https://www.docker.com/).  Please see Dockerfile for image setup.  Steps to create an image & how to run 
the app in a container below. (must have docker installed)

Create a docker image: `docker build -t users-api .`

Run docker container: `docker run -it -p 8080:8080 users-api`

__*** Once app has started, view the swagger ui by navigating to [http://localhost:8080/swagger-ui.html] ***__

View docker images: `docker images`

View docker containers: `docker ps -a`

Remove docker images: `docker rmi $(docker images -q)`

Remove docker containers: `docker rm $(docker ps -aq)`

[Click here for more information regarding docker](https://docs.docker.com/)

   
Continuous Integration(CI)
------------
A web hook has been setup with [GitHub Actions](https://github.com/features/actions) for all Push and Pull Requests.
 

Continuous Delivery(CD)
------------
A web hook has been setup with [heroku](https://www.heroku.com/) for all deployments.

[You can view and try the endpoints here](https://springboot-users-api.herokuapp.com/swagger-ui.html)


Step By Step Recreation
------------
Please see branches or closed pull requests to see how this project was built step by step.

You can also refer to [README_STEPS.MD](README_STEPS.MD)
 
Questions / Contact / Contribute
------------
Feel free to fork this repo, add to it, and create a pull request if you like to contribute.  

If you have any questions, you can contact me via email: `sdet.testautomation@gmail.com`
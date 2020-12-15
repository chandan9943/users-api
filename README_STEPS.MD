users-api

to build this project:

from commandline: 

`./gradlew clean build`

to start the users-api (build jar first with command above):

```
./gradlew bootrun 

or 

java -jar ./build/libs/users-0.0.1-SNAPSHOT.jar

```

#### 00-init-setup
1) Create project at https://start.spring.io
2) Add dependencies (please check build.gradle) 
3) Build and Run app to verify app is working
4) Add CI: using github actions for ci

#### 01-hello-world 
1) Create java class and add hello world code
2) Create a test for hello world endpoint
3) Add dependencies build.gradle. also add test logging to build.gradle

#### 02-get-all-users 
 1) Update application.properties to include settings for seeing sql queries and h2 setup

 2) Create User Model, @Entity, @Table, Define getter and setters

 3) Implement h2 database - in memory database, data will be lost when application restarts or when JVM reloads)

  - navigate to H2 console: localhost:8080/h2-console

  - all fields should default. Check "JDBC URL" to see if it matches this: jdbc:h2:mem:testdb

  - click connect, and now you can see the table schema and fields on the next screen

 4) Create User Repository (interface)(data layer that does all the database connections and operations)

 5) Implement getAllUsers RESTful Service, @Service, @RestController

6) Implement getAllUsers dto

     - dto stands for data transfer object

     - exposing entity objects through REST endpoints can pose security issues.  Using a dto we can control which entity 
         fields should be made available for our REST api

     - refactoring safe and type safe    

     - create UserDto class, refactor UserService and UserController to use dto

 7) Test getAllUsers
 
    - resource: Testing in Spring Boot : https://www.baeldung.com/spring-boot-testing
 
    - add new application-test.properties file for test
    
    - add test for: UserIntegrationTests, UserControllerTests, UserServiceImplTests, and UserRepositoryTests
    
    - add additional assert for UserApplicationTest

 #### 03-create-user
 1) implement createUser 

     - go to UserService / service layer create method for createUser
     
     - add createUser to UserService and implement UserServiceImpl

     - create UserExistsException class
     
     - set server.error.include-stacktrace in application.properties 

     - go to UserController create method for createUser 

         - add @PostMapping, add @RequestBody to param of method.  This is where json body will be passed in.

     - unit test createUser 
        
        - add test for: UserIntegrationTests, UserControllerTests, UserServiceImplTests. (UserRepositoryTests - 
            not necessary, create / save function already used in test setUp)
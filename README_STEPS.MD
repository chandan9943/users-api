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
            not necessary, create / save function already used in test)
           
#### 04-get-by-user-id 
1) Implement getUserById @GetMapping

    - go to UserService create method for getUserById
    
    - add getUserById to UserService and implement UserServiceImpl
    
    - create UserNotFoundException class
    
    - go to UserController create method for getUserById
        
        - add @PathVariable parameter which gets the id from the url
        
        - add @Min to check if user adds at least minimum of 1
        
            - add gradle dependency for `javax.validation:validation-api:2.0.1.Final`
        
        - add @GetMapping("/{id}) the id is a param and comes from the url input
        
    - unit test getUserById
    
        - add test for: UserIntegrationTests, UserControllerTests, UserServiceImplTests, UserRepositoryTests
        
#### 05-get-by-username
1) implement getUserByUsername @GetMapping

    - go to UserRepository add method for findByUsername (custom method)

        - see notes added to this method

    - go to UserService create method getUserByUsername and implement UserServiceImpl

    - go to UserController create method for getUserByUsername

        - add @GetMapping("/byusername/{username}") - note path has addition, since searching by string vs Long

    - unit test getUserByUsername
    
        - add test for: UserIntegrationTests, UserControllerTests, UserServiceImplTests, UserRepositoryTests

#### 06-update-by-user-id
1) Implement updateUserById @PutMapping

    - go to UserService create method for updateUserById

    - go to UserController create method for updateUserById

        - add @PathVariable for id input, add @RequestBody for json body input

    - unit test updateByUserId
    
        - add test for: UserIntegrationTests, UserControllerTests, UserServiceImplTests, (UserRepositoryTests - 
            not necessary, create / save function already used in test)

#### 07-delete-by-user-id            
10) Implement deleteUserById @DeleteMapping

    - go to UserService create method deleteUserById
        
    - go to Controller create method for deleteUserById
    
        - add @PathVariable for id input
        
    - unit test deleteUserById
    
        - add test for: UserIntegrationTests, UserControllerTests, UserServiceImplTests, UserRepositoryTests
        
#### 08-validations-global-exception-handling 

- commonly used validation annotations: @NotNull, @Size, @Min, @Max, @Email, @NotBlank, @NotEmpty

- global exception handling

    - @ControllerAdvice - write global code that can be applied to wide range of controllers

        - by default @ControllerAdvice annotation will be applicable to all classes that use @Controller

    - @ExceptionHandler - annotation for handling exceptions in specific handler classes or handler methods

        - if used with controller directly need to define per controller basis.  If used with @ControllerAdvice it will 
        only be used in Global Exception Handler class, but applicable to all controllers due to @ControllerAdvice
        
- use case combination
    
    - @ControllerAdvice class
    
        - MethodArgumentNotValidException
        
        - HttpRequestMethodNotSupportedException
    
    - @ControllerAdvice & @ExceptionHandler
    
        - for pre-defined exceptions like ConstraintViolations

1) implement Bean Validation

    - update gradle dependency to use `spring-boot-starter-validation`

    - Model / Entity Layer
    
        - implement validation for UserDto class
    
            - @NotEmpty(message="<field_name> is a required field.  Please provide a <field_name>")
    
            - @Size(min=2, message="<field_name> should contain at least 2 characters")
    
    - Controller
    
        - go to createUser method and add @Valid to params - this will validate the request body in the controller
        before it gets to the serviceLayer
    
        - user Postman to try the ceate a user
        
            - if the validations do not pass, it will return a 400 and json error message 
            
            - if you check the run console you will see a MethodArgumentNotValidException thrown

# SuperDuperDrive
![img.png](Notes/roadmap.png)
# road map:
1. The back-end with Spring Boot
2. The front-end with Thymeleaf
3. Application tests with Selenium
## back-end:
### User Access:
![img.png](Notes/UserAccessTask.png)
1. manage user access with spring security
   1. create a **security configuration** class
   2. override the default **login.html page** with one of your own
   3. created MyBaits mapper for **UserMapper** and **User POJO**. 
   4. implemented **authentication service** customized authenticate method.
2. login and signup:
   1. use thymeleaf and spring security to modify the html template.(**login.html**)
   2. implemented **userService** (isUserNameAvailable, createUser, getUser)
   3. implemented **login and signup controller** which use createUser to create user and handle some register error cases
   

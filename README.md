# USERS ADMINISTRATOR
## API RESTful to manage the users creation

#
### Requirements:
	Git, Java 1.8, SpringBoot, Maven or Gradle

#
### Github repository: 
https://github.com/SergioEstebanPi/usersadmin

### Swagger URL: 
http://localhost:8080/swagger-ui.html

### H2 database: 
http://localhost:8080/h2-console/

#

# Project structure:
![alt text](estructura.png)

### Postman requests collection
[users.postman_collection.json](users.postman_collection.json)

#

# MAVEN commands
### 1. Build the project using 
    mvn clean install
### 2. Run the unit tests 
    mvn test
### 3. Run using
    mvn spring-boot:run

#### or

    java -jar .\target\usersadmin-0.0.1-SNAPSHOT.jar

The web application is accessible via [localhost:8080](http://localhost:8080/)

#
# GRADLE commands
### 1. Build the project using 
    gradle build
### 2. Run the unit tests 
    gradle test
### 3. Run using
    gradle bootRun

#### or
    
    java -jar build\libs\usersadmin-0.0.1-SNAPSHOT.jar

The web application is accessible via [localhost:8080](http://localhost:8080/)

#

# Inmmemory database access and credentials

## URL
The web H2 database is accessible via [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/)

## Credentials
    username: h2
    password: h2



# Application diagram:
![alt text](diagrama.png)

## Usage examples using CURL

### Example request
    curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{ \ 
    "email": "new%40hotmail.com", \ 
    "name": "new", \ 
    "password": "aA123456$", \ 
    "phones": [ \ 
        { \ 
        "cityCode": "string", \ 
        "countryCode": "string", \ 
        "number": "string" \ 
        } \ 
    ] \ 
    }' 'http://localhost:8080/users'

### Example response
    {
        "id": "d96b7c7a-a4d2-4d8c-ba3b-b456ee03b06b",
        "createdAt": "2022-06-17T22:39:58.885+00:00",
        "createdBy": "ADMIN",
        "modifiedAt": "2022-06-17T22:39:58.885+00:00",
        "modifiedBy": "ADMIN",
        "lastLogin": "2022-06-17T22:39:58.885+00:00",
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuZXdAaG90bWFpbC5jb20iLCJleHAiOjE2NTU1MjM1OTgsImlhdCI6MTY1NTUwNTU5OH0.49Lqn6icRfQW_pQjMyKILq26bBshlxSqKBXK2BWjKhp3MBRnAha62qnntdq_1C2ZtezMsWDr8i4O5kdxfAifcg",
        "active": false
    }
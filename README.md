# USERS ADMINISTRATOR
## API RESTful de creación de usuarios

#
### Requisitos:
	Git, Java 1.8, SpringBoot, Maven

#
### Repositorio Github: 
https://github.com/SergioEstebanPi/usersadmin

### Swagger URL: 
http://localhost:8080/swagger-ui.html#!/user45controller/createUserUsingPOST

#

### Postman requests collection
[users.postman_collection.json](users.postman_collection.json)

#
### Build the project using 
    mvn clean install
### Run the unit tests 
    mvn test
### Run using
    mvn spring-boot:run
The web application is accessible via [localhost:8080](http://localhost:8080/)

#

### Diagrama:
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
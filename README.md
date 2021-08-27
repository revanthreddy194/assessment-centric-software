# Centric Software - assignment

### Task 1  
Write a HTTP REST API for product resource able to do the following operations:  
● Insertion of a product. The response must return the result of the insertion (the product
entity data itself)  

### Task 2  
● Search of products by category, using an exact match on the text of the ‘category’ field,
supporting pagination by page number and max entries per page. This will return the list of
products matching the criteria sorted by created_at from newest to oldest.  

# How to run
### `clean install`
### `spring-boot:run`

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.centric.assignment.AssignmentApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

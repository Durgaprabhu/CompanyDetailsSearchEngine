"# CompanyDetailsSearchEngine Microservice"

Java 17 is required to run this search engine. 
Make sure Lombok plugin is installed in the IntelliJ Make sure Maven and IntelliJ is using Java 17. Maven Apache Maven 3.6.3 has been used to build this search engine. 
IntelliJ IDEA 2024.1.1 (Community Edition) has been used to build this search engine.
Postman is used for testing and the collection has been added.
H2Database is used

"# Build"
mvn clean install

"# Start the application"
mvn spring-boot:start

"# Stop the application"
mvn spring-boot:stop

"# URL to access the H2 database for data manipulation"
http://localhost:8080/DurgaprabhuSearchAPI/h2-console/login.jsp

JDBC URL: jdbc:h2:mem:cdseng
password : localenvtest
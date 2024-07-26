# Management-System-backend
API REST developed with Java for Translator and Document Management System

<br>
The application is hosted on Render,You can try it yourself by accessing https://management-system-frontend-h23o.onrender.com.

The backend URL is https://management-system-backend-ic3p.onrender.com

NOTE: As it is a free hosting service, the speed of database operations may be reduced and the backend application may spin down with inactivity. If this happened you just need to access the backend URL and reload the page It takes 1 to 2 minutes.

<br>
Spring Framework is used to optimize application development, with Spring Boot simplifying setup and configuration, and Spring Data JPA handling database operations with PostgreSQL.


Using MVC architecture, all responsibilities are separated into specific classes, such as controllers, DTOs, models, repositories, and services, to ensure code organization.

The relationship between Translator and Document was defined to preserve the integrity of the database. All major errors are handled in the controllers to keep the frontend informed.

To handle a large volume of data, pagination was implemented in the getAll methods to return data efficiently, and batch size settings from Hibernate were used to group multiple insert statements. This approach optimizes both network and memory usage.

The OpenAI API is used to classify the content into the locale.

<br>
In order to run the application on your machine, do the following: <br>
- Make sure PostgreSQL is installed and running. <br>
- Enter your database credentials in application.properties <br>
- Enter the first command in your prompt for Maven or the second for Gradle:<br>
  ./mvnw spring-boot:run <br>
  ./gradlew bootRun

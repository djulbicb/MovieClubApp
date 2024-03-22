# MovieClubApp
Movie club application for renting and returning movies.

Features:
- Renting and returning movies
- CRUD operations on movies and users
- Sending email reminders to users with overdue rental dates

Used Technologies:
- Spring Boot
- Spring Data JPA
- RESTful Web Services for CRUD operations
- Docker
- RabbitMQ for sending email reminders

Getting Started:
- docker-compose up
- mvn spring-boot:run

Endpoints:
- "/movies": GET, POST, PUT, DELETE
- "/user": GET, POST, PUT, DELETE
- "/movies/rent/id": PUT (rent movie copy)
- "/movieCopies": PUT (return movie copy)
- "/movieCopies": POST (send email)

Testing:
- Unit tests
- Test containers

Future plans:
- Implement authentication and authorization features to enhance the security of the application.
- Application deployment


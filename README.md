Hello :)

# MovieClubApp
Movie club application for renting and returning movies.
// Dodaj tipa screenshot aplikacije

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
// Napisi celo upustvo za pokretanje
```
# Run docker containers
docker-compose up

# Run app in InteliJs
... 
or 
# spring boot command
...
```
Endpoints:
// Kad budes uvela authentifikaciju podeli endpointe na admin i regularne
// npr. /admin/movie POST PUT DELETE
//      /admin GET
- "/movies": GET, POST, PUT, DELETE
- "/user": GET, POST, PUT, DELETE
// Nemoj da mesas mnozinu i jedninu. Da li rentuje vise filmova odjednom ili samo jedan
// Onda je /movie/rent/id
- "/movies/rent/id": PUT (rent movie copy)
// Posle sam u klasi MovieCopy da umesto reci Copy bilo bi bolje da kazes Rental ili tako nesto
- "/movieCopies": PUT (return movie copy)
- "/movieCopies": POST (send email)


Testing:
- Unit tests
- Test containers

Future plans:
- Implement authentication and authorization features to enhance the security of the application.
- Application deployment
- Ideja mozes da se povezes sa ovakvim API https://www.omdbapi.com/ i tipa da koristis kao forma gde ljudi upisu koji film bi hteli da vide u ponudi u ovoj prodavnici. Cisto kao vezbe radi



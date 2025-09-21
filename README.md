# MovieAPI

MovieAPI is a Java-based backend application that interacts with [The Movie Database (TMDb)](https://www.themoviedb.org/) API to fetch, process, and store information about Danish movies, their actors, directors, and genres into a localhost database (Postgresql).

## Features

- **Fetch Danish Movies**: Retrieves all Danish movies released between 2020 and 2025 from TMDb
- **Database population**: Change objects from DTOs to Entities and then storing them in the database.

- **Specific Queries**
  - Get movies by director name, genre, or search by title (case-insensitive).
  - Retrieve top 10 and bottom 10 movies by average rating.
  - Calculate total average rating of all movies.
 

## Technology used

- **Java 17**
- **Hibernatae/JPA**
- **Jakarta Persistence**
- **Lombok**
- **Junit**


## HOW TO USE

1. **Clone repository**
  - Get the project onto your computer

2. **Configure environment variables**
   - Set your TMDB API key as an environment variable in your IDE
  
3. **Database setup**
   - Configure your database to be localhost (Name it: movie_api)


## On first run

  - Fetch danish movies from TMDB
  - Store movies, actors and directors in database


## Testing

- Unit tests are provided in the src/test/java folder for specific methods



## Made by

- https://github.com/EmilDagsberg
- https://github.com/AndreasKuke
- https://github.com/Emil2112

# About

## Demo Project

Your final product should be a backend application for movies and actors management via REST API, described in
details in the following sections. Keep in mind that application can be used by multiple clients at the same time and
all
data needs to be persisted somewhere (H2 database will be ok). UI is not part of this demo project.

## Tasks

* implement HTTP cache mechanisms where is needed
* implement request counter for all REST calls
* protect services with modern authorization mechanisms
* implement movies and actor services as at least two independent deployable services
* support the ability to handle a large number of HTTP GET requests.
* provide (or describe) mechanism how database objects can be initialized
* dockerize services
* provide docker compose file

## Movies

Movies have properties as title, year, description, list of actors, some pictures, etc. (as identifier use imdbID).

Implement REST operations to support basic UI requirements:

* list all movies
* list movies with pagination support
* search of movie
* CRUD operations

## Actors

Actors have properties as first name, last name, born date, list of movies, etc.

Implement REST operations to support basic UI requirements:

* list all actors
* list actors with pagination support
* CRUD operations

# How to run

To run the demo (on Linux), run the `build_and_run.sh` script from the project's root folder

```bash
./build_and_run.sh
```

If you can't run the script, give it execute permissions first (and then rerun it):

```bash
chmod +x build_and_run.sh
```

# URLs

## Actors

Login credentials:

* Username: `actors`
* Password: `actors`

HTTP request:

* GET: http://localhost:8081/api/v1/actors - returns a list of all actors in the DB
* GET: http://localhost:8081/api/v1/actors/{id} - returns an actor with the given `{id}` if it exists
* POST: http://localhost:8081/api/v1/actors - creates a new actor (from the request's body) and returns it if it was
  successfully added to the DB (ID doesn't already exist). Example of the request's body:
  ```json
  {
    "id": 1,
    "firstName": "Tom",
    "lastName": "Hanks",
    "bornDate": "1956-07-09",
    "moviesIds": [1]
  }
  ```
* PUT: http://localhost:8081/api/v1/actors/{id} - updates an existing actor (from the request's body, same as in the
  POST request) and returns it if it was successfully updated to the DB (valid actor `{id}` was provided)
* DELETE: http://localhost:8081/api/v1/actors/{id} - deletes an existing actor with the given `{id}`

## Movies

Login credentials:

* Username: `movies`
* Password: `movies`

HTTP request:

* GET: http://localhost:8081/api/v1/movies - returns a list of all movies in the DB
* GET: http://localhost:8081/api/v1/movies/{id} - returns a movie with the given `{id}` if it exists
* POST: http://localhost:8081/api/v1/movies - creates a new movie (from the request's body) and returns it if it was
  successfully added to the DB (ID doesn't already exist). Example of the request's body:
  ```json
  {
    "id": 1,
    "title": "The Shawshank Redemption",
    "yearReleased": 1994,
    "description": "A former banker convicted of murder befriends a fellow prisoner as he tries to prove his innocence.",
    "actorsIds": [1, 2, 3]
  }
  ```
* PUT: http://localhost:8081/api/v1/movies/{id} - updates an existing movie (from the request's body, same as in the
  POST request) and returns it if it was successfully updated to the DB (valid movie `{id}` was provided)
* DELETE: http://localhost:8081/api/v1/movies/{id} - deletes an existing movie with the given `{id}`

# Metrics

A lot of various metrics are available on the actuator URL:

* Movies: http://localhost:8080/actuator
* Actors: http://localhost:8081/actuator

Some metrics, such as server requests counts, are available on `/actuator/metrics/http.server.requests` URLs.

**Note that some URLs might not be available if a request for that URL hasn't been made yet.**

## Movies service

List of all received HTTP server requests: http://localhost:8080/actuator/metrics/http.server.requests

More URLs:

* GET all movies: http://localhost:8080/actuator/metrics/http.server.requests?tag=uri:/api/v1/movies
* GET movie by ID: http://localhost:8080/actuator/metrics/http.server.requests?tag=uri:/api/v1/movies/%7Bid%7D

## Actors service

List of all received HTTP server requests: http://localhost:8081/actuator/metrics/http.server.requests

More URLs:

* GET all actors: http://localhost:8081/actuator/metrics/http.server.requests?tag=uri:/api/v1/actors
* GET actor by ID: http://localhost:8081/actuator/metrics/http.server.requests?tag=uri:/api/v1/actors/%7Bid%7D

# Caching

Server side caches can be checked via the `/actuator/caches` URL.

# Testing

# Improvements

* Use more secure login credentials for the DB and the app (quick/test ones were used).
* Actuator endpoints could be narrowed down to just the `/actuator/metrics` URL to prevent excess data exposure (as a
  security measure).
* Lombok's `@Data` annotation could be replaced with Java's record class.
* Add more logs to see what's going on in the apps.
* Move common logic to a common lib.
* CSRF shouldn't be disabled in a production environment (it's disabled for easier testing).
* If needed, cache headers could be added to HTTP request responses as well.
* Different profiles could be used for testing, dev and prod environments.
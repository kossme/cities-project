# Cities project

#### Application has 3 endpoints:

/cities/{id} GET - to get one entity by id (allowed for users with authority "ALLOW_READ")

/cities GET - to get paginated list af all cities. Here you can specify page number and items per page.
Also case-sensitive search is available. (allowed for users with authority "ALLOW_READ")

/cities PUT - to update any entity. Must provide all 3 fields (updated or not): id, name and picture url.
(allowed only for users with authority "ALLOW_EDIT")

#### There are 2 users already exist:

"admin" - with password "admin" and both authorities "ALLOW_EDIT" and "ALLOW_READ"

"user" - with password "user" and authority "ALLOW_READ"


#### to start: 

Need to have installed docker.

Then run:

```sh
docker-compose up
```

#### Swagger available by the following link:
[Swagger](http://localhost:8080/swagger-ui/index.html#/)

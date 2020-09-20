# Voting for restaurants REST API Java application

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we asume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides new menu each day.

## Initial information

User authorization type: Basic Auth

To log in as regular USER use next credentials:

* login: user1@yandex.ru
* password: password1
* or add to CURL request this parameter:
`--user user1@yandex.ru:password1`

To log in as ADMIN use next credentials:

* login: admin@gmail.com
* password: admin
* or add to CURL request this parameter:
`--user admin@gmail.com:admin`

The main application context is "/voting"

## REST API documentation

### ADMIN commands:

#### Create a new restaurant: 

POST request on URL "http://localhost:8080/voting/admin/restaurants"

cURL example: 
`curl -s -X POST -d '{"name":"New Restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:admin`
---
#### Get restaurant by rest_ID:

GET request on URL "http://localhost:8080/voting/admin/restaurants/{rest_ID}"

cURL example: 
`curl -s http://localhost:8080/voting/admin/restaurants/100005 --user admin@gmail.com:admin`
---
#### Update restaurant by rest_ID:

PUT request on URL "http://localhost:8080/voting/admin/restaurants/{rest_ID}"

cURL example: 
`curl -s -X PUT -d '{"name":"Updated Restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/100005 --user admin@gmail.com:admin`
---
#### Delete a restaurant by rest_ID

DELETE request on URL "http://localhost:8080/voting/admin/restaurants/{rest_ID}"

cURL example: 
`curl -s -X DELETE http://localhost:8080/voting/admin/restaurants/100005 --user admin@gmail.com:admin`
---
#### Create a new dish for a restaurant with rest_ID

POST request on URL "http://localhost:8080/voting/admin/restaurants/{rest_ID}/dishes"

cURL example: 
`curl -s -X POST -d '{"name":"New Dish","date":"2020-09-04","price":"500"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/100007/dishes --user admin@gmail.com:admin`
---
#### Get dish by ID for a restaurant with rest_ID

GET request on URL "http://localhost:8080/voting/admin/restaurants/{rest_ID}/dishes/{ID}"

cURL example: 
`curl -s http://localhost:8080/voting/admin/restaurants/100007/dishes/100051 --user admin@gmail.com:admin`
---
#### Update dish by ID for a restaurant with rest_ID

PUT request on URL "http://localhost:8080/voting/admin/restaurants/{rest_ID}/dishes/{ID}"

cURL example: 
`curl -s -X PUT -d '{"name":"Updated Dish","date":"2020-08-06","price":"200"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/100007/dishes/100051 --user admin@gmail.com:admin`
---
#### Delete a dish by ID for a restaurant with rest_ID

DELETE request on URL "http://localhost:8080/voting/admin/restaurants/{rest_ID}/dishes/{ID}"

cURL example: 
`curl -s -X DELETE http://localhost:8080/voting/admin/restaurants/100007/dishes/100051 --user admin@gmail.com:admin`
---

### USER commands:

#### Get all restaurants

GET request on URL "http://localhost:8080/voting/restaurants"

cURL example: 
`curl -s http://localhost:8080/voting/restaurants --user user1@yandex.ru:password1`
---
#### Get all restaurants with today's menu

GET request on URL "http://localhost:8080/voting/restaurants/with-dishes"

cURL example: 
`curl -s http://localhost:8080/voting/restaurants/with-dishes --user user1@yandex.ru:password1`
---
#### Get one restaurant by rest_ID with dishes

GET request on URL "http://localhost:8080/voting/restaurants/{rest_ID}/with-dishes"

cURL example: 
`curl -s http://localhost:8080/voting/restaurants/100004/with-dishes --user user1@yandex.ru:password1`
---
#### Get all dishes in a restaurant with rest_ID

GET request on URL "http://localhost:8080/voting/restaurants/{rest_ID}/dishes"

cURL example: 
`curl -s http://localhost:8080/voting/restaurants/100004/dishes --user user1@yandex.ru:password1`
---
#### Get filtered dishes

GET request on URL "http://localhost:8080/voting/restaurants/{rest_ID}/dishes/filter" with parameters startDate, endDate (any parameter may be null); parameter format: yyyy-MM-dd

cURL example: 
`curl -s "http://localhost:8080/voting/restaurants/100004/dishes/filter?startDate=2020-08-02&endDate=2020-08-03" --user user1@yandex.ru:password1`
---
#### Create a new vote for a restaurant with rest_ID

POST request on URL "http://localhost:8080/voting/votes" with a parameter restId (required)

cURL example: 
`curl -s -X POST http://localhost:8080/voting/votes?restId=100004 --user user1@yandex.ru:password1`
---
#### Update a vote for a restaurant with rest_ID

PUT request on URL "http://localhost:8080/voting/votes" with a parameter restId (required)

cURL example: 
`curl -s -X PUT http://localhost:8080/voting/votes?restId=100005 --user user1@yandex.ru:password1`
---
#### Get all votes

GET request on URL "http://localhost:8080/voting/votes"

cURL example: 
`curl -s http://localhost:8080/voting/votes --user user1@yandex.ru:password1`
---
#### Get filtered votes

GET request on URL "http://localhost:8080/voting/votes/filter" parameters startDate, endDate (any parameter may be null); parameter format: yyyy-MM-dd

cURL example: 
`curl -s "http://localhost:8080/voting/votes/filter?startDate=2020-08-02&endDate=2020-08-03" --user user1@yandex.ru:password1`
---
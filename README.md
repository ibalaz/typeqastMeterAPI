# TypeqastMeterAPI

## How to run
For development of this project InteliJ was used. IDE which has build in runner for spring boot application.

To run it outside InteliJ IDE use maven command: mvn spring-boot:run

## Initialize test data
For initial database table creation and fill up of test data liquibase was used.

If tables are not created or data is missing:
* under resources/db/changelog in xml files 01-create-client-table, 02-data-insert-client, 03-data-insert-meter.xml
* change line <changeSet id="17" author="Baki"> where you can change author and increment id.
* after those changes run application again

## For easier testing

Postman collection for testing: https://pastebin.com/BnAPuAZ8

To access swagger, after locally running program use link: http://localhost:9042/swagger-ui/index.html

## Docker

To run docker-compose first you need to build jar file via: <br>
<code>mvn clean spring-boot:build-image -DskipTests=true</code>

After that manually copy jar file from target folder to root folder of app. (something was bugged in dockerfile and couldn't find my jar file)

And lastly in terminal run commands:<br>
<code>docker-compose build <br>
docker-compose up
</code>

## Documentation and GitHub pages
JavaDoc published on: https://ibalaz.github.io/typeqastMeterAPI/

## Test coverage
|                  | Class % | Method % | Line % |
|------------------|---------|----------|--------|
| typeqastMeterApi | 83      | 90       | 75     |
| controller       | 100     | 100      | 94     |
| service          | 100     | 100      | 34     |
| repository       | 100     | 100      | 100    |

## Inspiration
Code was written by following SOLID principles: https://www.digitalocean.com/community/conceptual_articles/s-o-l-i-d-the-first-five-principles-of-object-oriented-design
(spring boot version :D)

Also following Clean code principles written in book:
Robert C. Miller: Clean Code A hadnbook of Agile Software Craftmanship

Special mention mantra to follow while coding YAGNI "You Aren't Gonna Need It" written by Martin Fowloer:
https://martinfowler.com/bliki/Yagni.html

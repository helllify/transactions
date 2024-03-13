to run the application: $docker-compose up --build 
to stop the application run: $docker compose stop && docker compose down --volumes

!important: at the moment there is a bug and the sql script does not run with the docker compose command. In order to initialize
the db plz run: 
$docker exec -it transactions-cockroachdb1-1 cockroach sql --insecure -f /docker-entrypoint-initdb.d/init.sql

to transfer money from one account to another:
curl -XPOST -H "Content-type: application/json" -d '{}' 'http://localhost:9999/transfer?fromId=1&toId=2&currency=USD&amount=100.00'

to list the accounts:
curl -XGET 'http://localhost:9999/account'

to get info for a specific account e.g account with id: 1:
curl -XGET 'http://localhost:9999/account/1'

TODO list:
1. Refactor + Testing
2. add message broker and table to keep transtactions in db

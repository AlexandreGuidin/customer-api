# customer-api

CRUD API for customers!

## Running
 `lein run`

## Testing

`lein test`

## Endpoints

You can use a postman collection in `/postman-collection` folder

### Login: http://localhost:8090/authentication
Success CURL `curl --location --request POST 'http://localhost:8090/authentication' --header 'Authorization: Basic YWRtaW46YWRtaW4tcHdk'`
Unauthorized CURLs 
- `curl --location -I --request POST 'http://localhost:8090/authentication'`
- `curl --location -I --request POST 'http://localhost:8090/authentication' --header 'Authorization: Basic xxx'`
- `curl --location -I --request POST 'http://localhost:8090/authentication' --header 'Authorization: Basic YmxhYmxhYmxhCg=='`

### List all customers: GET http://localhost:8090/customer * Needs a valid Token
`curl --location --request GET 'http://localhost:8090/customer' --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTk2MzI3OTN9.86CaDF4qLCgv9GEhEpGfegETgsRiWYJIueLiFV2Wj4c'`

### Find customer by id: GET http://localhost:8090/customer/:id * Needs a valid Token
Success `curl --location --request GET 'http://localhost:8090/customer/3c4ad8fb-f361-4fd7-8dae-d0609864c0dd' --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTk2MzIyMzV9.XhZ5QkMSo80nXOvAwyV8_Jlvf3ITeE6I7CdFJV8Lo6Q'`
Invalid ID `curl --location --request GET 'http://localhost:8090/customer/xxxx' --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTk2MzIyMzV9.XhZ5QkMSo80nXOvAwyV8_Jlvf3ITeE6I7CdFJV8Lo6Q'`

### Create a customer by id: POST http://localhost:8090/customer/ * Needs a valid Token
Success `curl --location --request POST 'http://localhost:8090/customer' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTk2MzMwMTV9.nuwBPXj-ECixMcnqS2smZGkMsDvWHbiXPiJjqh5swO0' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Alexandre",
"lastName": "Guidin",
"birthDate": "1994-01-01"
}'`

Null fields `curl --location --request POST 'http://localhost:8090/customer' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTk2MzMwMTV9.nuwBPXj-ECixMcnqS2smZGkMsDvWHbiXPiJjqh5swO0' \
--header 'Content-Type: application/json' \
--data-raw '{
}'`

Invalid date `curl --location --request POST 'http://localhost:8090/customer' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTk2MzMwMTV9.nuwBPXj-ECixMcnqS2smZGkMsDvWHbiXPiJjqh5swO0' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Alexandre",
"lastName": "Guidin",
"birthDate": "1994/01/01"
}'`

### Disable a customer by id: POST http://localhost:8090/customer/:id/disable * Needs a valid Token
Success `curl --location --request POST 'http://localhost:8090/customer/3c4ad8fb-f361-4fd7-8dae-d0609864c0dd/disable' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTk2MzE4MTF9.dgaV5feL0NNLfQkOSs1z21g_JnpKBknVkJmpiIk313Y'`

Invalid ID: `curl --location -I --request POST 'http://localhost:8090/customer/xxxx/disable' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTk2MzMwMTV9.nuwBPXj-ECixMcnqS2smZGkMsDvWHbiXPiJjqh5swO0'`
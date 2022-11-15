Steps to start:

1. start the app
2. call: 

curl --location --request POST 'http://localhost:8080/auth' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "username":"user",
   "password": "user"
   }' 

3. with the jwt from above call (), call:

curl --location --request GET 'http://localhost:8080/places/GXvPAor1ifNfpF0U5PTG0w' \
--header 'Authorization: Bearer jwt'

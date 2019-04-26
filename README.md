# omnihire
Spring Boot Demo applciation along with PostgreSQL data store
The Demo named Spring boot application is created with RESTAPI 
application performs all the CRUD operations (CREATE,READ,UPDATE,DELETE) FOR USERS, STOCKS, CART tables through 
spring boot restapi

to run this application just download it and open eclipse and import th projects 

Note: if you don't have the Spring Boot Api probably you need to download to run the example 


Check the source package for the operations 

Model
Controller
Repository 

for all the 3 models the above drectories helps in performming the below operations


http://localhost:8080/api/users  GET METHOD
print all the users in the database

http://localhost:8080/api/getparticular POST METHOD
parameters are 'name'
returns the user details and the cart details


http://localhost:8080/api/users/create POST METHOD 
Content type : application/json
{
	"name" : "Sample User",
	"Password" : "123pass"
}
return json output with details like
{
    "name": "Sample User",
    "password": "123pass",
    "cart": null
}

http://localhost:8080/api/users/{id} GET METHOD
id canbe id numbers from the user table
eg : http://localhost:8080/api/users/3
returns details of the particular user with GET METHOD

http://localhost:8080/api/users/updateuser/{id} PUT METHOD
takes application/json input with name,password
Returns the Updated json

http://localhost:8080/api/users/delete/{id} DELETE METHOD
takes no input but id as get parameter in the link
returns nothing and deletes the user

http://localhost:8080/stock/allstocks/ GET METHOD
Takes nothing print all the available stocks in the stocks database

http://localhost:8080/stock/addstocks POST METHOD
takes input of itemname, price, instock as application/json type
returnt the inserted data as json format

http://localhost:8080/stock/getstocks/{id} GET METHOD
takes id as get paramater and returns the availability of the stocks 

http://localhost:8080/stock/updatestock/{id} PUT METHOD
takes id as get paramter in link and details of the stock like itemname, price, instock as put parameters and update the stock

http://localhost:8080/cart/viewcart POST METHOD
takes user_id as paramter and user_id can be found in the database if inserted
Returns various items in the cart for orders and including total price of each item repective to the quantity

http://localhost:8080/cart/additems/ POST METHOD
Takes user_id, item_id, quantity as POST PARAMETERS of type FORM_URLENCODED_VALUE
Returns the json of added item in the cart

http://localhost:8080/cart/getparticular/ POST METHOD
Takes user_id and item_id as FORM_URLENCODED_VALUE to see what are the items with respect to the item_id and also to check quantity of the items
Returns JSON of particukalr users particular items

http://localhost:8080/cart/udpateitem/{id} PUT METHOD
Takes input of user_id, item_id , quantity to do changes
Returns the updated json of the cart items

http://localhost:8080/cart/deleteitem POST METHOD
Takes input of user_id and item_id to delete the item from the cart completely
Returns Nothing



:)

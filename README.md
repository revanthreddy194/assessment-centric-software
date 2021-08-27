# Centric Software - assignment
### Task 1
Write a HTTP REST API for product resource able to do the following operations:
● Insertion of a product. The response must return the result of the insertion (the product
entity data itself)
● Sample Input:
{
"name": "Red Shirt",
"description": "Red hugo boss shirt",
"brand": "Hugo Boss",
"tags": [
"red",
"shirt",
"slim fit"
],
“category”: “apparel”
}
● Sample Output:
{
"id": "b6afac37-cf9a-4fd4-8257-f096dbb5d34d",
"name": "Red Shirt",
"description": "Red hugo boss shirt",
"brand": "Hugo Boss",
"tags": [
"red",
"shirt",
"slim fit"
],
“category”: “apparel”,
“created_at”: “2017-04-15T01:02:03Z”
}

### Task 2
● Search of products by category, using an exact match on the text of the ‘category’ field,
supporting pagination by page number and max entries per page. This will return the list of
products matching the criteria sorted by created_at from newest to oldest.
● Sample Output
[
{
"id": "b6afac37-cf9a-4fd4-8257-f096dbb5d34d",
"name": "Red Shirt",
"description": "Red hugo boss shirt",
"brand": "Hugo Boss",
"tags": [
"red",
"shirt",
"slim fit"
],
“category”: “apparel”,
“created_at”: “2017-04-15T01:02:03Z”
},
{
"id": "357cd2c8-6f69-4bea-a6fa-86e40af0d867",
"name": "Blue Shirt",
"description": "Blue hugo boss shirt",
"brand": "Hugo Boss",
"tags": [
"blue",
"shirt"
],
“category”: “apparel”,
“created_at”: “2017-04-14T01:02:03Z”
},
]



# How to run
### `clean install`
### `spring-boot:run`

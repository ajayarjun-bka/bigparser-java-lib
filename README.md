# Java library for BigParser

Java library for BigParser's API to fetch data from grids.


## How to Install the package?
Add maven dependency here

 

## How to Import bigparser module into your code?

Add these two statements

```java
import bigparser.Auth;
import bigparser.Grid;
```

**Available Methods:**
* login() - To login into BigParser account and get authId for all requests
* getHeader() - To get structure of specified grid
* getRows() - To fetch rows from the specified grid.
* getLastRow() - To get the last row or number of specified rows from the bottom


---

## How to Fetch Data?

Fetching Data from BigParser involves 3 simple steps

**1**.Login into your BigParser account.

**2**.Create an Object for the grid from which you wish to fetch data

**3**.Perform operation  the object to fetch data.


### Step 1:
In order to fetch data the user should first login into BigParser account using the *login()* method

#### Example
```java
import bigparser.Auth;
import bigparser.Grid;

String authId = Auth.login("arjun.bka@gmail.com", "AjayArjun");
```
authId has to be passed in whenver a grid Object is created 
### Step 2:

Create a object for the grid from which you wish to fetch data.

```java
import bigparser.Auth;
import bigparser.Grid;


String gridId = "57a34c80e4b017cc76c37c25";

String authId = Auth.login("arjun.bka@gmail.com", "AjayArjun");

Grid movies = new Grid(authId, gridId);

```

The gridId from which you wish to fetch the data must be specified by the user. *[Here gridId of the "Movie Beta" grid has been used]*  
### Step 3:
```java
import bigparser.Auth;
import bigparser.Grid;


String gridId = "57a34c80e4b017cc76c37c25";

String authId = Auth.login("arjun.bka@gmail.com", "AjayArjun");

Grid movies = new Grid(authId, gridId);

List<String> result = new ArrayList<String>();
    
// Build search filter
Map<String, String> searchfilter = new HashMap<String, String>();
searchfilter.put("GLOBAL", "x-men");
searchfilter.put("year", "2000,2016");
searchfilter.put("language ", "English");

//Build Sort 
Map<String, String> sort = new HashMap<String, String>();
sort.put("filmname ", "ASC");
sort.put("year", "DSC");


//columns to be displayed 
String columns = "film name ,year";


result = movies.getRows(20,searchfilter, sort, columns);
System.out.println(result);
```
**Sample Output**

*returns a list of rows.*

```java
[
  ["X-Men: Apocalypse","2016"],
  ["Ex-Men ","2016"],
  ["X-Men","2000"]
]
```
---
### Description of Available methods:


### login
```java
 login(emailId,password)
```
*Logins into BigParser account and returns the authId*

**Parameters**

#### ***Required Parameters:***
 
   `emailId` - emailId/username of your account
   
   `password` - password to login into BigParser account
   
---

### getRows
```java
 getRows(rows,searchFilter,sort,columns)
```
*To fetch rows from the grid*

This method has 4 different signatures

* getRows(Integer rows)

* getRows(Integer rows, Map<String, String> searchFilter)

* getRows(Integer rows, Map<String, String> searchFilter, Map<String, String> sort)

* getRows(Integer rows, Map<String, String> searchFilter, Map<String, String> sort, String columns)


**Parameters**

#### ***Optional Parameters:***
 
   `rows` - Number of rows to be fetched from the matching resuslts
   
    `searchFilter` - Map containing global level searches and column level searches
        
  ```java
       {"GLOBAL": "x-men", "language ": "english,French"}
  ```
    
Anything that has to be searched on global level should go under the key "GLOBAL". For terms which are to be searched on columns should be specified as key and values, where key is the column name and value is the term to be searched. 

   `sort` - Map containing the columns to be sorted and their order 
   
  ```java
       {"year": "ASC"}
  ```
    
Here "year" is the column name and the value can be "ASC" for ascending order and "DSC" for descending order.
   
   `columns` - String  of columns seperated by comma(,) to be fetched from the grid
   
```java
       "film name ,release date"
  ```
---


### getHeaders
```java
 getHeaders()
```
*To fetch headers of the specified grid*

---
### getLastRow
```java
 getRow(count,searchFilter,sort,columns)
```
*To fetch rows from the grid*

**Parameters**

#### ***Optional Parameters:***
 
   `count` - Number of rows to be fetched from the bottom of the matching resuslts
   
   `searchFilter` - Map containing global level searches and column level searches
        
  ```java
       {"GLOBAL": "x-men", "language ": "english,French"}
  ```
    
Anything that has to be searched on global level should go under the key "GLOBAL". For terms which are to be searched on columns should be specified as key and values, where key is the column name and value is the term to be searched. 

   `sort` - Map containing the columns to be sorted and their order 
   
  ```java
       {"year": "ASC"}
  ```
    
Here "year" is the column name and the value can be "ASC" for ascending order and "DSC" for descending order.
   
   `columns` - String  of columns seperated by comma(,) to be fetched from the grid
   
```java
       "film name ,release date"
  ```
---
****

## Sample Code

*Replace emailId and password in the login function*

```java
from bigparser import grid
from bigparser import auth

gridId = "57a34c80e4b017cc76c37c25"

authId = auth.login("emailId", "password")

movies = grid(authId, gridId)

headers = movies.getHeader()
print(headers)

rows = movies.getRows(searchfilter={'year': '20005'},columns=['film name ','language '],sort=[{'film name ':'ASC'}])
print(rows)


rows = movies.getRows(searchfilter={'GLOBAL': ['x-men'], 'language ': 'english'}, sort=[{"year": "ASC"}],columns=['film name ', 'release date'])
print(rows)

result = movies.getLastRow(count=2, columns=['film name ', 'language '])
print(result)

result = movies.getRange(rows=5, columns=2)
print(result)

rows = movies.getRows(searchfilter={'GLOBAL': ['x-men'], 'language ': 'english'}, sort=[{"year": "ASC"}],columns=['film name ', 'release date'])
print(rows)


```
___


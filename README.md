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

String authId = Auth.login("emailId", "password");
```
authId has to be passed in whenever a grid Object is created 
### Step 2:

Create a object for the grid from which you wish to fetch data.

```java
import bigparser.Auth;
import bigparser.Grid;


String gridId = "57a34c80e4b017cc76c37c25";

String authId = Auth.login("emailId", "password");

Grid movies = new Grid(authId, gridId);

```

The gridId from which you wish to fetch the data must be specified by the user. *[Here gridId of the "Movie Beta" grid has been used]*  
### Step 3:
```java
import bigparser.Auth;
import bigparser.Grid;


String gridId = "57a34c80e4b017cc76c37c25";

String authId = Auth.login("emailId", "password");

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
### Description of Available Auth methods:


### login
```java
 login(emailId,password)
```
*Logs into a BigParser account and returns the authId*

**Parameters**

#### ***Required Parameters:***
 
   `emailId` - emailId/username of your account
   
   `password` - password to login into BigParser account
   
---
### Description of Grid Constructor:


```java
Grid movies = new Grid(authId, gridId);
```
*Creates a Grid Object*

**Parameters**

#### ***Required Parameters:***
 
   `authId` - authId of you account retreived from login method
   
   `gridId` - gridId of your grid. It can be found in the url of the grid.
   
---

### Description of Available Grid methods:


### getRows
```java
 getRows(rows,searchFilter,sort,columns)
```
*Fetches rows from the grid*

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
    
Anything that has to be searched on a global level should go in to the list under the key "GLOBAL". Terms which are to be searched within columns should be specified as key and value(s) where key is the column name and value(s) is the term(s) to be searched.

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
*Fetches headers of the specified grid*

---
### getLastRow
```java
 getRow(count,searchFilter,sort,columns)
```
*Fetches rows from the grid*

**Parameters**

#### ***Optional Parameters:***
 
   `count` - Number of rows to be fetched from the bottom of the matching resuslts
   
   `searchFilter` - Map containing global level searches and column level searches
        
  ```java
       {"GLOBAL": "x-men", "language ": "english,French"}
  ```
    
Anything that has to be searched on a global level should go in to the list under the key "GLOBAL". Terms which are to be searched within columns should be specified as key and value(s) where key is the column name and value(s) is the term(s) to be searched.

   `sort` - Map containing the columns to be sorted and their order 
   
  ```java
       {"year": "ASC"}
  ```
    
Here "year" is the column name and the value can be "ASC" for ascending order and "DSC" for descending order.
   
   `columns` - String  of columns seperated by comma(,) to be fetched from the grid
   
```java
       "film name ,release date"
  ```
****

## Sample Code

*Replace emailId and password in the login function*

```java
public class App {

	public static void main(String args[]) {
		String gridId = "57a34c80e4b017cc76c37c25";
		String authId = Auth.login("emailId", "password");
		Grid movies = new Grid(authId, gridId);
		List<String> result = new ArrayList<String>();

		// Build search filter
		Map<String, String> searchfilter = new HashMap<String, String>();
		searchfilter.put("GLOBAL", "x-men");
		searchfilter.put("year", "2000,2016");
		searchfilter.put("language ", "English");

		// Build Sort
		Map<String, String> sort = new HashMap<String, String>();
		sort.put("filmname ", "ASC");
		sort.put("year", "DSC");

		// columns to be displayed
		String columns = "film name ,year";

		result = movies.getRows();
		System.out.println(result);

		result = movies.getRows(20, null, null, columns);
		System.out.println(result);

		result = movies.getRows(null, searchfilter, sort, columns);
		System.out.println(result);

		Map<String, String> headers = new HashMap<>();
		headers = movies.getHeaders();
		System.out.println(headers);

		result = movies.getLastRow(searchfilter, null, columns, 4);
		System.out.println(result);
	}
}


```
___


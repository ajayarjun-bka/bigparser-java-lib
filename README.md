# Python Module for BigParser

Python client library for BigParser's API to fetch data from grids.


## How to Install the package?

To install bigparser just type the following command:

```python
$ pip install bigparser
```

Right now supports only python 3.3 and up. Soon python 2.7 and up will be included. 

## How to Import bigparser module into your code?

Add these two statements

```python
from bigparser import grid
from bigparser import auth
```

**Available Methods:**
* login() - To login into BigParser account and get authId for all requests
* getHeader() - To get structure of specified grid
* getRows() - To fetch rows from the specified grid.
* getLastRow() - To get the last row or number of specified rows from the bottom
* getRange() - To get a Range of rows and columns [*returns result as dataframe*]

---

## How to Fetch Data?

Fetching Data from BigParser involves 3 simple steps

**1**.Login into your BigParser account.

**2**.Create an Object for the grid from which you wish to fetch data

**3**.Perform operation to the object to fetch data.


### Step 1:
In order to fetch data the user should first login into BigParser account using the *login()* method

#### Example
```python
from bigparser import grid
from bigparser import auth


authId = auth.login("username", "password") #Store authId for future use
```
authId has to be passed in whenver a grid Object is created 
### Step 2:

Create a object for the grid from which you wish to fetch data.

```python
from bigparser import grid
from bigparser import auth

gridId = "57a34c80e4b017cc76c37c25"

authId = auth.login("username", "password")

movies = grid(authId, gridId)

```

The gridId from which you wish to fetch the data must be specified by the user. *[Here gridId of the "Movie Beta" grid has been used]*  
### Step 3:
```python
from bigparser import grid
from bigparser import auth

gridId = "57a34c80e4b017cc76c37c25"

authId = auth.login("username", "password")

movies = grid(authId, gridId)

rows = movies.getRows()
print(rows)
```
**Sample Output**

*returns a list of rows.*

```python
[
  ['X-Men: Apocalypse', '2016'.....], 
  ['Warcraft', '2016'.....], 
  ['Captain America: Civil War', '2016'.....]
  ........
]
```




*Another example*

```python
rows = movies.getRows(searchfilter={'year': '2005'},columns=['film name ','year'])
print(rows)
```

**Output**
```
[
  ['X-Men: Apocalypse', '2016'],
  ['Warcraft', '2016'], 
  ['Captain America: Civil War', '2016'], 
  ['The Do-Over', '2016']....
]
```
---
### Description of Available methods:


### login
```python
 login(emailId,password)
```
*Logins into BigParser account and returns the authId*

**Parameters**

#### ***Required Parameters:***
 
   `emailId` - emailId/username of your account
   
   `password` - password to login into BigParser account
   
---

### getRows
```python
 getRows(rows,searchFilter,sort,columns)
```
*To fetch rows from the grid*

**Parameters**

#### ***Optional Parameters:***
 
   `rows` - Number of rows to be fetched from the matching resuslts
   
   `searchFilter` - Dictionary containing global level searches and column level searches
        
  ```python
       {'GLOBAL': ['x-men'], 'language ': ['english','French']}
  ```
    
Anything that has to be searched on global level should go in to the list under the key "GLOBAL". For terms which are to be searched on columns should be specifies as key and values where is the column name and value is the term to be searched. 

   `sort` - List of dictionaries containing the column to be sorted and their order 
   
  ```python
       [{"year": "ASC"}]
  ```
    
Here "year" is the column name and the value can be "ASC" for ascending order and "DSC" for descending order.
   
   `columns` - list of columns to be fetched from the grid
   
```python
       ['film name ', 'release date']
  ```
---


### getHeaders
```python
 getHeaders()
```
*To fetch headers of the specified grid*

---
### getLastRow
```python
 getRow(count,searchFilter,sort,columns)
```
*To fetch rows from the grid*

**Parameters**

#### ***Optional Parameters:***
 
   `count` - Number of rows to be fetched from the bottom of the matching resuslts
   
   `searchFilter` - Dictionary containing global level searches and column level searches
        
  ```python
       {'GLOBAL': ['x-men'], 'language ': 'english'}
  ```
    
Anything that has to be searched on global level should go in to the list under the key "GLOBAL". For terms which are to be searched on columns should be specifies as key and values where is the column name and value is the term to be searched. 

   `sort` - List of dictionariess containing the column to be sorted and their order 
   
  ```python
       [{"year": "ASC"}]
  ```
    
Here "year" is the column name and the value can be "ASC" for ascending order and "DSC" for descending order.
   
   `columns` - list of columns to be fetched from the grid
   
```python
       ['film name ', 'release date']
  ```
****

## Sample Code

*Replace emailId and password in the login function*

```python
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

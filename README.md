# recruiterTest
Firstly, 
-Import the file as maven project.
-Locate the DemoApplication.java file inside test\demo\src\main\java\test\demo\DemoApplication.java
-Run the application as Java Application
-The app is Up and Running. 

For Testing
API Desc
API Endpoint: http://localhost:8080/coordinates
Method: POST
Header: content-type: application/json
Body:
{
    "lattitude": 27.700769,
    "longitude": 85.300140
} 

You should be getting the Output.

Output Example:

[
    "\"M 3.9 - 15 km E of Odlābāri, India\"||357.964 Km",
    "\"M 5.2 - 35 km NNW of Samtse, Bhutan\"||363.8165 Km",
    "\"M 4.7 - 100 km W of Dêqên, China\"||494.1572 Km",
    "\"M 4.7 - 134 km WNW of Nagqu, China\"||684.573 Km",
    "\"M 6.0 - 9 km NNW of Dhekiajuli, India\"||712.68805 Km",
    "\"M 4.8 - 4 km NNW of Dhekiajuli, India\"||715.7763 Km",
    "\"M 4.9 - 4 km N of Dhekiajuli, India\"||718.0605 Km",
    "\"M 4.6 - 140 km NE of Sarāhan, India\"||823.93317 Km",
    "\"M 5.0 - 92 km ENE of Nagqu, China\"||868.3157 Km",
    "\"M 4.7 - 100 km ENE of Nagqu, China\"||877.107 Km"
]

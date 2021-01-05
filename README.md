## Purpose
This Spring Web Application computes historical stock data
by tracing through 10 years of an inputted ticker symbol and 
date range (MM-dd). While parsing through 10 years of stock
prices, the back-end calculates the percent & price differences 
between the given month and date. This then presents all values 
calculated on a graph. Check screenshots below.

## Screenshots
![Home Page](/documents/screenshots/home-page.png) <br />
**Home Page "index.html"** <br />
<br />
![Generate Form](/documents/screenshots/generate-form.png) <br />
**Generate Form Page "generate_form.html"** <br />
<br />
![Generate Form AAPL](/documents/screenshots/generate-form-aapl.png) <br />
**Generate Form with fields "generate_form.html"** <br />
<br />
![Results Page AAPL](/documents/screenshots/results-page-aapl.png) <br />
**Results using dynamic graph from Highcharts "result_form.html"** <br />
<br />
![Generate Form NIO](/documents/screenshots/generate-form-nio.png) <br />
**Generate Form. Using fields that will result in error "generate_form.html"** <br />
<br />
![Error Page NIO](/documents/screenshots/error-page-nio.png) <br />
**Error Form from previous screenshot's fields "error_form.html"** <br />
<br />

## Dependencies & Other Technology
### Client - Frontend/UI
* **Thymeleaf** - HTML Spring Dependency
* **Highcharts JS** - JS Dynamic Charts
* **Bootstrap** - CSS Framework
### Server - Backend
* **JDK 11** - Java 11 Development Kit
* **Maven** - Dependency Management
* **Spring Boot** - Using Spring Initializer for Spring Framework
### API
* **IEX Cloud** - Used for pulling stock data. [Click Here for IEX Docs](https://iexcloud.io/docs/api/)

##IEX Cloud
Before running as a java application. Make sure to enter in your
IEX Cloud API Token/Key. <br />
In '</src/main/java/com.stockcalendar.formpage/iex/IEXApi>' <br />
Enter in your token in for data member 'public final static String token' <br />

**For IEX Cloud:** When you sign up for IEX Cloud and claim your
token. Make sure that sandbox is turned on. It is just a switch to flip
on while you are on your user dashboard. <br />

##Additional Info
In your '<application.properties>' file, don't worry about any configurations.
Only property I had to set was 'DEBUG'. However that was just during applcation
development. <br />

CSS & JS files are written seperate. However, while using Intellij Community IDE, 
you have to attach all CSS and JS into html document. Which is already embedded into 
the files. <br />

##Contact
Phone: (971) 708-4444<br />
Email: ericsanderson333@gmail.com<br />
Linkedin: https://www.linkedin.com/in/ericanderson333 <br />
Please contact me and send me any questions/advice! Thanks!





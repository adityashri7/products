# products
An android application to add and manage products to be purchased

- Helps users to Register and login using a username and password, or even through Facebook.
  - Creates a SQLite database with a table for products, and a table for users
  - Each user in the user table can see what products they added in the products table
  - Ability to add photos in the table and showing it in the pending view by converting the image to base64
  - Using an MVP format for the login and register activity
  - Using RXJava to make use of event driven asynchronous calls

- Helps user to see their location on a map, and get the weather at any location using both Volley API, and AsyncTask
  - If user taps the map and taps on the generated marker, weather will be displayed using Asynctask methods
  - If user holds on a map, and taps on the generated marker, weather will be displayed by using Volley APIs


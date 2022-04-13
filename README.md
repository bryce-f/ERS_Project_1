# Employee Reimbursement System

## Project Description

This is an example website of an employee reimbursement system, that demonstrates how different users can access certain features given their particular role.  Originally, this project was deployed on Google Cloud Services (Backend/jar file) and Firebase (Frontend) for learning purposes. However, it has been reverted back to a local running instance.  

## Technologies Used
- Java8
- Gradle
- JUnit/Mockito
- Logback
- GCP Compute Engine
- GCP Cloud SQL
- Firebase
- HTML/CSS/JavaScrip

## Features

- Login Feature:
  - Login as either employee or admin
- Employee Features:
  - Login and view current/resolved reimbursements
  - Add a new reimbursement
  - Edit an existing (pending) reimbursement
  - Filter all reimbursements by status (Pending, Approved, Denied)
  - Search all columns using the search bar
- Admin Features:
  - View all reimbursements submitted
  - Filter reimbursements by status (Pending, Approved, Denied)
  - Approve/Deny any pending reimbursement
  - Search all columns using the search bar
  - Delete any reimbursement
  - View receipt images

To-do list:
* Create an option for admins to add new users to the system
* Fix overall styling 

****
## Getting Started

Prerequisites:
- npm
- git bash
- SQL database management software (DBeaver is recommended)
- live-server (`npm install live-server`)
- Java IDE (IntelliJ is recommended)

### Steps:

1. Clone repository

```
git clone https://github.com/bryce-f/ERS_Project_1.git
```
2. Create a new SQL database (postgres is recommended)
3. Locate the `connection.prop` file in the ERS_backend folder and fill in the variables (according to your newly created SQL database) 
    - db_username
    - db_password
    - db_url
4. Run the `upload_data.sql` file in the new SQL database. This will fill the database with fake data
5. Open the ERS_backend folder in the Java IDE and build and run the gradle project
   - This will enable connnection to the backend API via port :8081
6. Once the backend is running, open the ERS_frontend folder (VS Code is recommended) and run the command `live-server` in the terminal (must be opened inside the ERS_frontend folder). 
    - This will create a local dev server on (the default) port 8080
7. Once the front and backends are deployed, open internet browser to http://localhost:8080
   - The webpage should open the Login page by default (see below)

## Usage

User can log in using there username and password, select a username or password from the SQL database or choose these recommended fake users... 

**Admin:** 
\
username: `ray_charles1` password: `a1` 

**Employee:**
\
username: `john_doe1` password: `p1`


### Login Page
![Login](https://github.com/bryce-f/ERS_Project_1/blob/main/ScreenShots/Login.PNG?raw=true)

If the user is a normal employee the Employee Page will be displayed
- The user has the option to add a new reimbursement [top left green button], edit an existing (pending) reimbursement [second to last column, black button] or delete a pending reimbursement [last column, red button]

### Employee Page
![Employee Page](https://github.com/bryce-f/ERS_Project_1/blob/main/ScreenShots/EmployeePage.PNG?raw=true)

If the green "Add New Reimbursement" button is clicked, a new page will display with the options to upload reimbursement information

### New Reimbursement
![New Reimbursement](https://github.com/bryce-f/ERS_Project_1/blob/main/ScreenShots/NewReimbursement.PNG?raw=true)

If the "Edit" button is clicked, the reimbursement page will display with the existing information within each field. Click "Submit Form" to override/save the information. 

### Edit Reimbursement
![Edit Reimbursement](https://github.com/bryce-f/ERS_Project_1/blob/main/ScreenShots/EditReimbursement.PNG?raw=true)

If the user logs in as an admin the Admin page will be displayed. This page is similar to the employee page but displays all reimbursement that have been submitted. This table can be filtered like the employee table by clicking the filter button (shown below), in addtion, the ability to Approve or Deny a pending reimbursement is available. Note: An admin cannot approve or deny their own reimbursement. Also clicking on the receipt preview (3rd to last column) enlarges the image for proper analysis. Additionally, the admin has the ability to delete any reimbursement from the table/database.

### Admin Page
![Admin Page](https://github.com/bryce-f/ERS_Project_1/blob/main/ScreenShots/AdminPage.PNG?raw=true)

### Filter Side Bar
![Filter](https://github.com/bryce-f/ERS_Project_1/blob/main/ScreenShots/Filter.PNG?raw=true)    

And final note, the logout button is located at the top right of both the Employee and Admin Page


## License

This project uses the following license:

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

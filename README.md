# Auto Detailing App
## Rest API for a mobile auto detailing application
This is an application that tracks employee productivity in the auto detailing industry. 
Easily add upcoming jobs, and in what order they have been received. Once jobs have been added, 
the job can be started by you or any of your employees. The application will track how long 
each job is taking, and who performed work on each job.

Additional features coming soon!

## REST API Endpoints
### Fetch vehicle information by VIN
**POST** /vehicles/vin

Request payload

    {
        "vin": "WDBEA26E6KB002021"
    }

***
### Create a vehicle
**POST** /vehicles

Request payload

    {
        "vin": "WAUEH94F87N552471",
        "catalogId": 128,
        "color": "Black",
        "arrivalDate": "03/05/2021",
        "arrivalTime": "05:25 PM"
    }

***
### Register a new user account
**POST** /register

Request payload

    {
        "company": {
        "name": "Brandy's Slick Whips",
        "address1": "5505 Sunset Ave",
        "address2": "Suite B",
        "city": "Sunnydale",
        "state": "CA",
        "zip": "62254",
        "email": "brandy@slickwhips.com",
        "phone": "555-556-1234",
        "receivedTexts": true,
        "website": "slickwhips.com"
    },
        "user": {
        "firstName": "Brandy",
        "middle": "S",
        "lastName": "Maples",
        "phone": "555-456-4899",
        "isMobile": true,
        "email": "brandy@slickwhips.com",
        "password": "12345",
        "passwordConfirm": "12345"
    }
    }

***
### List all employees
**GET** /employees

***
### List all vehicles
**GET** /vehicles?page=2

***
### Login (returns a JWT in response header)
#### email field can be a valid email or username
**POST** /login

Request payload

    {
        "email": "tlangworthy",
        "password": "12345"

    }

***
### Create a job
**POST** /jobs

Request payload

    {
        "customer": {
        "customerType": "DEALER",
        "firstName": "Jack",
        "lastName": "Smith",
        "phone": "5734458119",
        "receiveTexts": true,
        "business": "Smith's Used Cars"
    },
        "vehicle": {
        "vin": "JA4AS2AW4AZ405315",
        "catalogId": 140,
        "color": "Silver",
        "arrivalDate": "03/07/2021",
        "arrivalTime": "08:33 PM"
    },
        "serviceIds": [98],
        "managerNotes": "No dressing on interior"
    }

*** 
### Create services
**POST** /services

Request payload

    {
        "services": [
            {
                "name": "Ceramic Coating (2 year)",
                "description": "Two coats of ceramic pro ceramic coating.",
                "price": "499.00"
            },
            {
                "name": "Ceramic Coating (5 year)",
                "description": "Five coats of ceramic pro ceramic coating.",
                "price": "1199.00"
            }
        ]
    
    }

*** 
### List all services
**GET** /services

***
### List all jobs
**GET** /jobs

*** 
### Employee Clock-In
**POST** /time-clock

Request payload

    {
        "clockedReasonId": 1,
        "clockedStatus": "IN",
        "clockedAt": "04/27/2021 10:05 PM",
        "employeeNote": "Running late due to doctor appointment"
    }

***
### Fetch vehicle information by internal catalog ID
**GET** /vehicles/catalog/14

***
### Update Job Status
**PATCH** /jobs/11

#### Valid actions are
* START
* PAUSE
* RESUME
* FINISH
* CANCEL
* APPROVE
* DENY

Request payload

    {
        "action": "FINISH",
        "actionAt": "04/20/2021 11:30 AM"
    }

***
### View Job Details
**GET** /jobs/7

*** 
### Fetch authenticated employee details
**GET** /employees/authenticated

***
### Fetch currently clocked-in employees
**GET** /time-clock/clocked-in

***
### Fetch clocked reasons
**GET** /time-clock/reasons

Example Response Payload

    [
        {
            "id": 1,
            "name": "Clocked In"
        },
        {
            "id": 2,
            "name": "Clocked Out"
        },
        {
            "id": 3,
            "name": "Out to Lunch"
        },
        {
            "id": 4,
            "name": "Back from Lunch"
        }
    ]

*** 
### Customer List
**GET** /customers?page=1&type=dealer

Example Response Payload

    {
        "_embedded": {
            "customers": [
                 {
                    "id": 4,
                    "customerType": "DEALER",
                    "fullName": "Lindy Renish",
                    "business": "Kilback-Runolfsdottir",
                    "phone": "971-829-4545",
                    "_links": {
                    "self": {
                    "href": "http://localhost:8085/customers/4"
                }
            ]
        }
    }

***
### Customer Details
**GET** /customers/42

Example Response Payload

    {
        "id": 42,
        "customerType": "DEALER",
        "firstName": "Lewie",
        "lastName": "Heelis",
        "business": "Lockman, Hoeger and Hintz",
        "email": "lheelis15@mtv.com",
        "phone": "110-277-0006",
        "receiveTexts": false,
        "notes": "Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh. Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.",
        "createdAt": "05/17/2021 11:51:01 AM",
        "updatedAt": "08/12/2020 07:47:47 AM",
        "_links": {
            "customers": {
                "href": "http://localhost:8085/customers?type=retail"
            }
        }
    }
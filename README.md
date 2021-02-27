#BEERQUEST

## Application Purpose

This application provides a convenience API on front of the existing API at `https://datamillnorth.org/api/table/wk7xz_hf8bv` which provides a list of pubs visited and reviewed by the Leeds Beer Quest founders!

This application offers the functionality to return pubs from this list by location, by rating in a number of categories, and by searching through tags for a given feature


## Running the application
### Docker
The Dockerfile should contain everything required to build and run the jar, so if docker is installed and running locally, the application can be brought up from the command line by navigating to the project root directory and running 

`docker-compose up -d`

This will start the application listening on port 8090

### Build and run jar
If docker is not installed locally, the application can be built and run manually using an IDE, or through command line by navigating to the project root directory and running

`mvn clean package`

`java -jar ./target/beerquest-0.0.1-SNAPSHOT.jar`

To build and run the jar manually, please ensure jdk11 is previously installed. The application is configured to start on port 8090


## API

The API for the application can be explored by navigating to `localhost:8090/swagger-ui/` once the application is running as per the above steps.

There are four endpoints presented by the application, as follows:

* `GET /pubs` - this returns a list of all pubs

* `GET /pubs/closest` - this returns a list of pubs closest to provided latitude and longitude. 
    * There are two required query parameters for this endpoint - `lat` and `long` which are used to provide the latitude and longitude co-ordinates
    * There is one optional query parameter - `limit` which determines how many pubs to return. The default value for this is 1, so if the endpoint is called without it, it will show the single closest pub. If a larger limit is provided, pubs are shown in order from closest pub to further pubs

* `GET /pubs/tag` - this returns a list of pubs which contain the given tag in the tags field
    * There is one required query parameter `tag` which is the tag to saerch by

* `GET /pubs/top` - this returns a list of pubs which exceed a provided score threshold for the ratings provided for beer, amenities, atmosphere or value
    * There are two required query parameters
        * `criteria` which must be one of 'beer', 'atmosphere', 'amenities' or 'value' and is used to determine which field to use when filtering
        * `minimum-score` which will determine the minimum 'star rating' for the given criteria above that a pub must meet to be included in the results
    * There is one optional query parameter - `limit` which determines how many pubs to return. THe default value is 1.
    

## Avenues for improvement

* Testing - have not ventured far beyond happy path testing for the sake of time - could benefit from more comprehensive thought around possible sad paths
* The call out to the `datamillnorth` API makes a naive assumption based on the number of records in the list, and sets a limit of 500 pubs to return. This would become problematic if the number of pubs in the list later exceeded this, and could be more elegantly handled through something like pagination
* The above API is called upon application startup and all data dumped into a H2 in-memory database. This approach is probably fine in this instance as the API endpoints that this app adds are all consumers - the data is not in any way changed by this app. If the app were to be further developed to make updates to the database, this would require instead an approach not involving in-memory storage
* I may have been too slapdash in the lombok annotations used in the PubDAO model
* The mapping between Pub and PubDAO objects is one-to-one at the minute, this could be made a bit leaner to present just the minimum useful information back to the user
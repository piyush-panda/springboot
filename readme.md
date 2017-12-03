##Pre-requisites
### In order to run the application maven needs to be installed on the system

## Steps to run the application
### 1) Download symantec-test-piyush.zip onto a desired folder
### 2) Unzip the above zip file
### 3) Open command prompt and go the root folder of the application.
### 4) Run `mvn clean verify`
### 5) Run `java -jar target/reposearch-1.0-SNAPSHOT.jar`
### 6) Now open a browser or any other REST client and go to URL `http://localhost:8080/githubrepositories/search?language=<LANGUAGE_NAME>`

## Few Important bits
### 1) Due to limitation on max number of search results returned by GitHub API, for any language the max number of results returned in 1000
### 2) The GitHub API has a search rate limit of 10 requests per minute from an IP. So after first request the subsequent requests if done immediately would be a bit slower

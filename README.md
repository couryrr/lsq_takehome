Did not get the Docker Compose Up and Running. Took too much time trying to get familar with it.
Spring Boot was new and I took some time to get familar with that.

Created this on my local without docker

MySql will need to be locally installed and the scripts in misc Database can be used to create the tables.

to build
mvn clean install
to run
java -jar .\target\api-0.0.1-SNAPSHOT.jar

at that point you can use 

File Upload
http://localhost:8080/supplier/invoice/bulk/csv


To access the summaries
http://localhost:8080/supplier/invoice/SupplierSummary

I know this is not nearly complete. I appreciate all the time you have put in and was looking forward to working at lsq.
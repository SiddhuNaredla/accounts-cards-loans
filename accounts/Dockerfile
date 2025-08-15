#for jdk openjdk is imagename and 17-jdk-slim is tag
FROM openjdk:17-jdk-slim
#who is maintainer
LABEL authors="Siddh"
# add the application  jar file to image one is for specifying the file and take the same file and add it to the image also
COPY target/accounts-microservice.jar accounts-microservice.jar
#to run the container fro the above image means execute the application
ENTRYPOINT ["java", "-jar", "accounts-microservice.jar"]


#docker build . -t siddhaardh/accounts:m1  // building docker image
#docker run -p 8080:8080 siddhaardh/accounts:m1  // to make running container from the image -p explicit port
#docker run -d -p 8080:8080 siddhaardh/accounts:m1  // -d detached mode not display the entire logs only displays the conta_id
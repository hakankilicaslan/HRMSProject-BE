FROM amazoncorretto:19
COPY build/libs/guest-microservice-v0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
#Dockerfile dosyası ile image oluşturmak için gerekli kod(ama ilgili microservice üzerinden terminali açmamız gerekiyor):
#docker build -t hakankilicaslan/hrmsguestmicroservice:001 .
#docker run -p 9091:9091 hakankilicaslan/hrmsguestmicroservice:001
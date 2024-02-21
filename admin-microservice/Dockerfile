FROM amazoncorretto:19
COPY build/libs/admin-microservice-v0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
#Dockerfile dosyası ile image oluşturmak için gerekli kod(ama ilgili microservice üzerinden terminali açmamız gerekiyor):
#docker build -t hakankilicaslan/hrmsadminmicroservice:001 .
#docker run -p 9094:9094 hakankilicaslan/hrmsadminmicroservice:001
FROM amazoncorretto:19
COPY build/libs/mail-service-v0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
#Dockerfile dosyası ile image oluşturmak için gerekli kod(ama ilgili microservice üzerinden terminali açmamız gerekiyor):
#docker build -t hakankilicaslan/hrmsmailservice:001 .
#docker run -p 9096:9096 hakankilicaslan/hrmsmailservice:001
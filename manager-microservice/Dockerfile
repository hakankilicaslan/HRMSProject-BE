FROM amazoncorretto:19
COPY build/libs/manager-microservice-v0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
#Dockerfile dosyası ile image oluşturmak için gerekli kod(ama ilgili microservice üzerinden terminali açmamız gerekiyor):
#docker build -t hakankilicaslan/hrmsmanagermicroservice:001 .
#docker run -p 9093:9093 hakankilicaslan/hrmsmanagermicroservice:001
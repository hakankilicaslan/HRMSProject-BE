FROM amazoncorretto:19
COPY build/libs/employee-microservice-v0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
#Dockerfile dosyası ile image oluşturmak için gerekli kod(ama ilgili microservice üzerinden terminali açmamız gerekiyor):
#docker build -t hakankilicaslan/hrmsemployeemicroservice:001 .
#docker run -p 9092:9092 hakankilicaslan/hrmsemployeemicroservice:001
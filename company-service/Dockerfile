FROM amazoncorretto:19
COPY build/libs/company-service-v0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
#Dockerfile dosyası ile image oluşturmak için gerekli kod(ama ilgili microservice üzerinden terminali açmamız gerekiyor):
#docker build -t hakankilicaslan/hrmscompanyservice:001 .
#docker run -p 9095:9095 hakankilicaslan/hrmscompanyservice:001
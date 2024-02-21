package org.hrms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * @SpringBootApplication bir Spring Boot uygulamasının ana bileşenini işaret eden bir anotasyondur.
 * Bu anotasyon, bir Spring Boot uygulamasının başlatılmasını sağlayan temel yapıyı oluşturur ve genellikle uygulamanın ana sınıfı üzerinde kullanılır.
 * @SpringBootApplication anotasyonu @SpringBootConfiguration, @EnableAutoConfiguration ve @ComponentScan anotasyonlarının birleşimini temsil eder.
 *
 * main metodumuzun içinde Spring Boot uygulamalarını başlatmak için kullanılan SpringApplication sınıfı üzerinden run metodunu çağırarak başlatılacak olan sınıfımızı veriyoruz
 * Bu şekilde uygulamanın başlangıç yani giriş noktasını belirtmiş oluyoruz.
 */
@SpringBootApplication
public class AuthMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthMicroserviceApplication.class, args);
    }

}

package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.EmployeeCreateModel;
import org.hrms.service.AuthService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 *
 * @RequiredArgsConstructor anotasyonu dependencies injection edilen final fieldlara(GuestService) constructor oluşturmak için kullanılan bir anotasyondur.
 */
@Service
@RequiredArgsConstructor
public class EmployeeCreateConsumer {

    private final AuthService authService;

    /*
     * @RabbitListener anotasyonu RabbitMQ mesaj kuyruklarından mesajları dinlemek için kullanılır.
     * Bu anotasyonla birlikte Bir RabbitMQ kuyruğu dinleyen mesaj alıcı(consumer) oluşturuyoruz.
     * (queues = "${rabbitmq.manager-addEmployee-queue}") şeklinde queues içine parametre olarak girilen bütün kuyruklar dinleniyor.
     * Bu kuyruğa gelen her mesajda aşağıdaki metoda iletiliyor.
     */
    @RabbitListener(queues = "${rabbitmq.manager-addEmployee-queue}")
    public void createEmployeeFromQueue(EmployeeCreateModel employeeCreateModel){
        authService.createEmployee(employeeCreateModel);
    }

}

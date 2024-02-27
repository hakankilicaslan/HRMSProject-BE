package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.CompanySetManagerIdModel;
import org.hrms.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 *
 * @RequiredArgsConstructor anotasyonu dependencies injection edilen final fieldlara(CompanyService) constructor oluşturmak için kullanılan bir anotasyondur.
 */
@Service
@RequiredArgsConstructor
public class CompanySetManagerIdConsumer {

    private final CompanyService companyService;

    /*
     * @RabbitListener anotasyonu RabbitMQ mesaj kuyruklarından mesajları dinlemek için kullanılır.
     * Bu anotasyonla birlikte Bir RabbitMQ kuyruğu dinleyen mesaj alıcı(consumer) oluşturuyoruz.
     * (queues = "${rabbitmq.company-set-manager-id-queue}") şeklinde queues içine parametre olarak girilen bütün kuyruklar dinleniyor.
     * Bu kuyruğa gelen her mesajda aşağıdaki metoda iletiliyor.
     */
    @RabbitListener(queues = "${rabbitmq.company-set-manager-id-queue}")
    public void setManagerIdFromQueue(CompanySetManagerIdModel model){
        companyService.setManagerId(model);
    }

}

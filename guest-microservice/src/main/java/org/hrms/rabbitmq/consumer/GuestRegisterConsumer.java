package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.GuestRegisterModel;
import org.hrms.repository.entity.Guest;
import org.hrms.repository.enums.ERole;
import org.hrms.service.GuestService;
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
public class GuestRegisterConsumer {

    private final GuestService guestService;

    /*
     * @RabbitListener anotasyonu RabbitMQ mesaj kuyruklarından mesajları dinlemek için kullanılır.
     * Bu anotasyonla birlikte Bir RabbitMQ kuyruğu dinleyen mesaj alıcı(consumer) oluşturuyoruz.
     * (queues = "${rabbitmq.guest-register-queue}") şeklinde queues içine parametre olarak girilen bütün kuyruklar dinleniyor.
     * Bu kuyruğa gelen her mesajda aşağıdaki metoda iletiliyor.
     */
    @RabbitListener(queues = "${rabbitmq.guest-register-queue}")
    public void createGuestFromQueue(GuestRegisterModel model){
        Guest guest = Guest.builder()
                .authId(model.getAuthId())
                .name(model.getName())
                .surname(model.getSurname())
                .email(model.getEmail())
                .password(model.getPassword())
                .phoneNumber(model.getPhoneNumber())
                .gender(model.getGender())
                .role(ERole.GUEST)
                .build();
        guestService.save(guest);
    }

}

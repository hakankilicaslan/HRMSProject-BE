package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.AdminSaveModel;
import org.hrms.repository.entity.Auth;
import org.hrms.repository.enums.EStatus;
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
public class AdminSaveConsumer {

    private final AuthService authService;

    /*
     * @RabbitListener anotasyonu RabbitMQ mesaj kuyruklarından mesajları dinlemek için kullanılır.
     * Bu anotasyonla birlikte Bir RabbitMQ kuyruğu dinleyen mesaj alıcı(consumer) oluşturuyoruz.
     * (queues = "${rabbitmq.admin-save-queue}") şeklinde queues içine parametre olarak girilen bütün kuyruklar dinleniyor.
     * Bu kuyruğa gelen her mesajda aşağıdaki metoda iletiliyor.
     */
    @RabbitListener(queues = "${rabbitmq.admin-save-queue}")
    public void saveAdminFromQueue(AdminSaveModel model){
        Auth auth = Auth.builder()
                .name(model.getName())
                .surname(model.getSurname())
                .email(model.getEmail())
                .password(model.getPassword())
                .phoneNumber(model.getPhoneNumber())
                .identityNumber(model.getIdentityNumber())
                .role(model.getRole())
                .gender(model.getGender())
                .status(EStatus.ACTIVE)
                .build();
        authService.save(auth);
    }
}

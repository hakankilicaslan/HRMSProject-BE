package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.ActivateStatusModel;
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
public class GuestActivateStatusConsumer {

    private final GuestService guestService;

    @RabbitListener(queues = "${rabbitmq.guest-activate-status-queue}")
    public void activateStatusFromQueue(ActivateStatusModel activateStatusModel){
        guestService.activeStatus(activateStatusModel.getAuthId());
    }
}

package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.ActivateStatusModel;
import org.hrms.service.ManagerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 *
 * @RequiredArgsConstructor anotasyonu dependencies injection edilen final fieldlara(ManagerService) constructor oluşturmak için kullanılan bir anotasyondur.
 */
@Service
@RequiredArgsConstructor
public class ManagerActivateStatusConsumer {

    private final ManagerService managerService;

    @RabbitListener(queues = "${rabbitmq.manager-activate-status-queue}")
    public void activateStatusFromQueue(ActivateStatusModel activateStatusModel){
        managerService.activeStatus(activateStatusModel.getAuthId());
    }
}

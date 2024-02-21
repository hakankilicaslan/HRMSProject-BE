package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.CompanyRegisterModel;
import org.hrms.repository.entity.Manager;
import org.hrms.repository.enums.ERole;
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
public class CompanyRegisterConsumer {

    private final ManagerService managerService;

    @RabbitListener(queues = "${rabbitmq.company-register-queue}")
    public void createCompanyManagerFromQueue(CompanyRegisterModel model){
        Manager manager = Manager.builder()
                .authId(model.getAuthId())
                .name(model.getName())
                .surname(model.getSurname())
                .email(model.getEmail())
                .password(model.getPassword())
                .phoneNumber(model.getPhoneNumber())
                .identityNumber(model.getIdentityNumber())
                .address(model.getAddress())
                .companyName(model.getCompanyName())
                .dateOfBirth(model.getDateOfBirth())
                .gender(model.getGender())
                .role(ERole.MANAGER)
                .build();
        managerService.save(manager);
    }

}

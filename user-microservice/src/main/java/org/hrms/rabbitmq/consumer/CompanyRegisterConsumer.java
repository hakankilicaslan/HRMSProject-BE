package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.CompanyRegisterModel;
import org.hrms.repository.entity.User;
import org.hrms.repository.enums.ERole;
import org.hrms.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyRegisterConsumer {

    private final UserService userService;

    @RabbitListener(queues = "${rabbitmq.company-register-queue}")
    public void createCompanyManagerFromQueue(CompanyRegisterModel model){
        User user = User.builder()
                .authId(model.getAuthId())
                .name(model.getName())
                .surname(model.getSurname())
                .email(model.getEmail())
                .phoneNumber(model.getPhoneNumber())
                .address(model.getAddress())
                .role(ERole.COMPANY_MANAGER)
                .build();
        userService.save(user);
    }
}

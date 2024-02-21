package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.GuestRegisterModel;
import org.hrms.repository.entity.User;
import org.hrms.repository.enums.ERole;
import org.hrms.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestRegisterConsumer {

    private final UserService userService;

    @RabbitListener(queues = "${rabbitmq.guest-register-queue}")
    public void createGuestFromQueue(GuestRegisterModel model){
        User user = User.builder()
                .authId(model.getAuthId())
                .name(model.getName())
                .surname(model.getSurname())
                .email(model.getEmail())
                .phoneNumber(model.getPhoneNumber())
                .role(ERole.GUEST)
                .build();
        userService.save(user);
    }
}

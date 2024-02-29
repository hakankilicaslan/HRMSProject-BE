package org.hrms.rabbitmq.consumer;

import com.rabbitmq.client.AMQP;
import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.AuthUpdateModel;
import org.hrms.service.AuthService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 *
 * @RequiredArgsConstructor anotasyonu dependencies injection edilen final fieldlara(AuthService) constructor oluşturmak için kullanılan bir anotasyondur.
 */
@Service
@RequiredArgsConstructor
public class AuthUpdateConsumer {

    private final AuthService authService;

    /*
     * @RabbitListener anotasyonu RabbitMQ mesaj kuyruklarından mesajları dinlemek için kullanılır.
     * Bu anotasyonla birlikte Bir RabbitMQ kuyruğu dinleyen mesaj alıcı(consumer) oluşturuyoruz.
     * (queues = "${rabbitmq.auth-delete-queue}") şeklinde queues içine parametre olarak girilen bütün kuyruklar dinleniyor.
     * Bu kuyruğa gelen her mesajda aşağıdaki metoda iletiliyor.
     */
    @RabbitListener(queues = "${rabbitmq.auth-update-queue}")
    public void updateAuthFromQueue(AuthUpdateModel authUpdateModel){
            authService.softUpdate(authUpdateModel);
    }

//    @RabbitListener(queues = "${rabbitmq.auth-update-queue}")
//    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
//    public void updateAuthFromQueue(AuthUpdateModel authUpdateModel) throws Exception {
//        try{
//            authService.softUpdate(authUpdateModel);
//        }catch(Exception e){
//            throw e; // This rethrows the exception for retry
//        }
//    }

//    @RabbitListener(queues = "${rabbitmq.auth-update-queue}", ackMode = "true")
//    public void handleMessage(String message) {
//        // Message processing logic
//    }

//    @RabbitListener(queues = "${rabbitmq.auth-update-queue}")
//    public void handleMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
//        try {
//            // Message processing logic
//            // Acknowledge the message
//            channel.basicAck(deliveryTag, false); // false indicates not to acknowledge multiple messages
//        } catch (Exception e) {
//            // Handle exceptions and optionally reject the message
//            channel.basicNack(deliveryTag, false, true); // Requeue the message if true
//        }
//    }

}

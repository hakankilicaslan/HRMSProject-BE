package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.GuestRegisterModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class GuestRegisterProducer {

    private final RabbitTemplate rabbitTemplate;

    public GuestRegisterProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.guest-exchange}")
    private String guestExchange;

    @Value("${rabbitmq.guest-register-bindingKey}")
    private String guestRegisterBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * GuestRegisterProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için guestExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan guestExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan guestRegisterBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan GuestRegisterModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz GuestRegisterModel'imizi ilgili kuyruk olan guestRegisterQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(GuestRegisterModel guestRegisterModel){
        rabbitTemplate.convertAndSend(guestExchange,guestRegisterBindingKey,guestRegisterModel);
    }

}

package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.ActivateStatusModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class GuestActivateStatusProducer {

    private final RabbitTemplate rabbitTemplate;

    public GuestActivateStatusProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.guest-exchange}")
    private String guestExchange;

    @Value("${rabbitmq.guest-activate-status-bindingKey}")
    private String guestActivateStatusBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * ActivateStatusProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için guestExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan guestExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan guestActivateStatusBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan ActivateStatusModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz ActivateStatusModel'imizi ilgili kuyruk olan guestActivateStatusQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(ActivateStatusModel activateStatusModel){
        rabbitTemplate.convertAndSend(guestExchange,guestActivateStatusBindingKey,activateStatusModel);
    }

}

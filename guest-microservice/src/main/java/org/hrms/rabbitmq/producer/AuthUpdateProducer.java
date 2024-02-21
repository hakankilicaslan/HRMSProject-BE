package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.AuthUpdateModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class AuthUpdateProducer {

    private final RabbitTemplate rabbitTemplate;

    public AuthUpdateProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.auth-exchange}")
    private String authExchange;

    @Value("${rabbitmq.auth-update-bindingKey}")
    private String authUpdateBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * AuthUpdateProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için authExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan authExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan authUpdateBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan AuthUpdateModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz AuthUpdateModel'imizi ilgili kuyruk olan authUpdateQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(AuthUpdateModel authUpdateModel){
        rabbitTemplate.convertAndSend(authExchange,authUpdateBindingKey,authUpdateModel);
    }

}

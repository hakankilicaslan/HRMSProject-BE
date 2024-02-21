package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.AuthDeleteModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class AuthDeleteProducer {

    private final RabbitTemplate rabbitTemplate;

    public AuthDeleteProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.auth-exchange}")
    private String authExchange;

    @Value("${rabbitmq.auth-delete-bindingKey}")
    private String authDeleteBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * AuthDeleteProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için authExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan authExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan authDeleteBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan AuthDeleteModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz AuthDeleteModel'imizi ilgili kuyruk olan authDeleteQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(AuthDeleteModel authDeleteModel){
        rabbitTemplate.convertAndSend(authExchange,authDeleteBindingKey,authDeleteModel);
    }
}

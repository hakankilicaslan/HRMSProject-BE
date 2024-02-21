package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.AdminSaveModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class AdminSaveProducer {

    private final RabbitTemplate rabbitTemplate;

    public AdminSaveProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.auth-exchange}")
    private String authExchange;

    @Value("${rabbitmq.admin-save-bindingKey}")
    private String adminSaveBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * AdminSaveProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için authExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan authExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan adminSaveBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan AdminSaveModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz AdminSaveModel'imizi ilgili kuyruk olan adminSaveQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(AdminSaveModel adminSaveModel){
        rabbitTemplate.convertAndSend(authExchange,adminSaveBindingKey,adminSaveModel);
    }
}

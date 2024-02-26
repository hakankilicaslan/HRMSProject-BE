package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.AdminSaveSetAuthIdModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class AdminSaveSetAuthIdProducer {

    private final RabbitTemplate rabbitTemplate;

    public AdminSaveSetAuthIdProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.admin-exchange}")
    private String adminExchange;

    @Value("${rabbitmq.admin-set-authid-bindingKey}")
    private String adminSetAuthIdBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * EmployeeCreateProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için adminExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan adminExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan adminSetAuthIdBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan AdminSaveSetAuthIdModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz AdminSaveSetAuthIdModel'imizi ilgili kuyruk olan adminSetAuthIdQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(AdminSaveSetAuthIdModel adminSaveSetAuthIdModel){
        rabbitTemplate.convertAndSend(adminExchange,adminSetAuthIdBindingKey,adminSaveSetAuthIdModel);
    }
}

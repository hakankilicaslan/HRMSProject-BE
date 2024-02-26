package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.EmployeeCreateSetAuthIdModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class EmployeeCreateSetAuthIdProducer {

    private final RabbitTemplate rabbitTemplate;

    public EmployeeCreateSetAuthIdProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.employee-exchange}")
    private String employeeExchange;

    @Value("${rabbitmq.employee-set-authid-bindingKey}")
    private String employeeSetAuthIdBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * EmployeeCreateProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için employeeExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan employeeExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan employeeSetAuthIdBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan EmployeeCreateSetAuthIdModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz EmployeeCreateSetAuthIdModel'imizi ilgili kuyruk olan employeeSetAuthIdQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(EmployeeCreateSetAuthIdModel employeeCreateSetAuthIdModel){
        rabbitTemplate.convertAndSend(employeeExchange,employeeSetAuthIdBindingKey,employeeCreateSetAuthIdModel);
    }

}

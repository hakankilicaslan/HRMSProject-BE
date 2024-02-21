package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.EmployeeCreateModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class EmployeeCreateProducer {

    private final RabbitTemplate rabbitTemplate;

    public EmployeeCreateProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.auth-exchange}")
    private String authExchange;

    @Value("${rabbitmq.manager-addEmployee-bindingKey}")
    private String managerAddEmployeeBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * EmployeeCreateProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için authExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan authExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan managerAddEmployeeBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan EmployeeCreateModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz EmployeeCreateModel'imizi ilgili kuyruk olan managerAddEmployeeQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(EmployeeCreateModel employeeCreateModel){
        rabbitTemplate.convertAndSend(authExchange,managerAddEmployeeBindingKey,employeeCreateModel);
    }

}

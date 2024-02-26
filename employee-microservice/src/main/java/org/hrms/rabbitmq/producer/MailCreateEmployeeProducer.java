package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.MailCreateEmployeeModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class MailCreateEmployeeProducer {

    private final RabbitTemplate rabbitTemplate;

    public MailCreateEmployeeProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.mail-exchange}")
    private String mailExchange;

    @Value("${rabbitmq.mail-create-employee-bindingKey}")
    private String mailCreateEmployeeBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * MailForgotPasswordProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için mailExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan mailExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan mailCreateEmployeeBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan MailCreateEmployeeModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz MailCreateEmployeeModel'imizi ilgili kuyruk olan mailCreateEmployeeQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(MailCreateEmployeeModel mailCreateEmployeeModel){
        rabbitTemplate.convertAndSend(mailExchange,mailCreateEmployeeBindingKey,mailCreateEmployeeModel);
    }
}

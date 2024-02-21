package org.hrms.rabbitmq.producer;

import org.hrms.rabbitmq.model.AuthForgotPasswordModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 */
@Service
public class EmployeeForgotPasswordProducer {

    private final RabbitTemplate rabbitTemplate;

    public EmployeeForgotPasswordProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.employee-exchange}")
    private String employeeExchange;

    @Value("${rabbitmq.employee-forgot-password-bindingKey}")
    private String employeeForgotPasswordBindingKey;

    /*
     * RabbitTemplate üzerinden çağırdığımız convertAndSend() metoduna gerekli parametreleri girerek mesajımızı ilgili microservice'e gönderiyoruz.
     * CompanyRegisterProducer kullanarak göndereceğimiz mesajı ilgili kuyruklara gönderilmesini sağlamak için employeeExchange'i kullanacağız.
     * Exchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan employeeExchange değişkenimizi tanımlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan employeeForgotPasswordBindingKey değişkeni veriyoruz.
     * Model olarakta göndermek istediğimiz model olan AuthForgotPasswordModel sınıfımızı veriyoruz.
     * Bu şekilde serilize ettiğimiz AuthForgotPasswordModel'imizi ilgili kuyruk olan employeeForgotPasswordQueue kuyruğumuza göndermiş oluyoruz.
     */
    public void convertAndSend(AuthForgotPasswordModel authForgotPasswordModel){
        rabbitTemplate.convertAndSend(employeeExchange,employeeForgotPasswordBindingKey,authForgotPasswordModel);
    }

}

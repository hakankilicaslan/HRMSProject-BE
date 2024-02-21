package org.hrms.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //Bir konfigürasyon sınıfı olduğunu belirtiyoruz yani bu sınıfın Spring Container'ında yapılandırma sınıfı olarak kullanılacağını belirtmiş oluyoruz.
public class RabbitMqConfig {

    //Mesajlaşmak için RabbitMQ kullandığımız servislerde config sınıflarını kullanıyoruz yani hem mesajı gönderen microservice'te hem de mesajı alacak microservice'te aynı RabbitMqConfig sınıfının olması gerekiyor.

    /*
     * @Value annotasyonu, Spring uygulamalarında dış yapılandırma dosyalarından değerleri doğrudan enjekte etmek için kullanılır.
     * Parantez içinde yml dosyamızdaki yolu vererek o yola karşılık gelen değeri authExchange değişkenimize enjekte etmiş oluyoruz.
     */
    @Value("${rabbitmq.auth-exchange}")
    private String authExchange;
    @Value("${rabbitmq.admin-exchange}")
    private String adminExchange;
    @Value("${rabbitmq.manager-exchange}")
    private String managerExchange;
    @Value("${rabbitmq.employee-exchange}")
    private String employeeExchange;
    @Value("${rabbitmq.guest-exchange}")
    private String guestExchange;
    @Value("${rabbitmq.company-exchange}")
    private String companyExchange;
    @Value("${rabbitmq.mail-exchange}")
    private String mailExchange;

    @Value("${rabbitmq.guest-register-queue}")
    private String guestRegisterQueueName;
    @Value("${rabbitmq.guest-register-bindingKey}")
    private String guestRegisterBindingKey;

    @Value("${rabbitmq.company-register-queue}")
    private String companyRegisterQueueName;
    @Value("${rabbitmq.company-register-bindingKey}")
    private String companyRegisterBindingKey;

    @Value("${rabbitmq.mail-queue}")
    private String mailQueueName;
    @Value("${rabbitmq.mail-bindingKey}")
    private String mailBindingKey;

    @Value("${rabbitmq.auth-update-queue}")
    private String authUpdateQueueName;
    @Value("${rabbitmq.auth-update-bindingKey}")
    private String authUpdateBindingKey;

    @Value("${rabbitmq.auth-delete-queue}")
    private String authDeleteQueueName;
    @Value("${rabbitmq.auth-delete-bindingKey}")
    private String authDeleteBindingKey;

    @Value("${rabbitmq.admin-save-queue}")
    private String adminSaveQueueName;
    @Value("${rabbitmq.admin-save-bindingKey}")
    private String adminSaveBindingKey;

    @Value("${rabbitmq.admin-update-queue}")
    private String adminUpdateQueueName;
    @Value("${rabbitmq.admin-update-bindingKey}")
    private String adminUpdateBindingKey;

    @Value("${rabbitmq.admin-delete-queue}")
    private String adminDeleteQueueName;
    @Value("${rabbitmq.admin-delete-bindingKey}")
    private String adminDeleteBindingKey;

    @Value("${rabbitmq.manager-save-queue}")
    private String managerSaveQueueName;
    @Value("${rabbitmq.manager-save-bindingKey}")
    private String managerSaveBindingKey;

    @Value("${rabbitmq.manager-update-queue}")
    private String managerUpdateQueueName;
    @Value("${rabbitmq.manager-update-bindingKey}")
    private String managerUpdateBindingKey;

    @Value("${rabbitmq.manager-delete-queue}")
    private String managerDeleteQueueName;
    @Value("${rabbitmq.manager-delete-bindingKey}")
    private String managerDeleteBindingKey;

    @Value("${rabbitmq.manager-forgot-password-queue}")
    private String managerForgotPasswordQueueName;
    @Value("${rabbitmq.manager-forgot-password-bindingKey}")
    private String managerForgotPasswordBindingKey;

    @Value("${rabbitmq.manager-addCompany-queue}")
    private String managerAddCompanyQueueName;
    @Value("${rabbitmq.manager-addCompany-bindingKey}")
    private String managerAddCompanyBindingKey;

    @Value("${rabbitmq.manager-updateCompany-queue}")
    private String managerUpdateCompanyQueueName;
    @Value("${rabbitmq.manager-updateCompany-bindingKey}")
    private String managerUpdateCompanyBindingKey;

    @Value("${rabbitmq.manager-deleteCompany-queue}")
    private String managerDeleteCompanyQueueName;
    @Value("${rabbitmq.manager-deleteCompany-bindingKey}")
    private String managerDeleteCompanyBindingKey;

    @Value("${rabbitmq.manager-addEmployee-queue}")
    private String managerAddEmployeeQueueName;
    @Value("${rabbitmq.manager-addEmployee-bindingKey}")
    private String managerAddEmployeeBindingKey;

    @Value("${rabbitmq.manager-updateEmployee-queue}")
    private String managerUpdateEmployeeQueueName;
    @Value("${rabbitmq.manager-updateEmployee-bindingKey}")
    private String managerUpdateEmployeeBindingKey;

    @Value("${rabbitmq.manager-deleteEmployee-queue}")
    private String managerDeleteEmployeeQueueName;
    @Value("${rabbitmq.manager-deleteEmployee-bindingKey}")
    private String managerDeleteEmployeeBindingKey;

    @Value("${rabbitmq.employee-save-queue}")
    private String employeeSaveQueueName;
    @Value("${rabbitmq.employee-save-bindingKey}")
    private String employeeSaveBindingKey;

    @Value("${rabbitmq.employee-update-queue}")
    private String employeeUpdateQueueName;
    @Value("${rabbitmq.employee-update-bindingKey}")
    private String employeeUpdateBindingKey;

    @Value("${rabbitmq.employee-delete-queue}")
    private String employeeDeleteQueueName;
    @Value("${rabbitmq.employee-delete-bindingKey}")
    private String employeeDeleteBindingKey;

    @Value("${rabbitmq.employee-forgot-password-queue}")
    private String employeeForgotPasswordQueueName;
    @Value("${rabbitmq.employee-forgot-password-bindingKey}")
    private String employeeForgotPasswordBindingKey;

    @Value("${rabbitmq.guest-save-queue}")
    private String guestSaveQueueName;
    @Value("${rabbitmq.guest-save-bindingKey}")
    private String guestSaveBindingKey;

    @Value("${rabbitmq.guest-update-queue}")
    private String guestUpdateQueueName;
    @Value("${rabbitmq.guest-update-bindingKey}")
    private String guestUpdateBindingKey;

    @Value("${rabbitmq.guest-delete-queue}")
    private String guestDeleteQueueName;
    @Value("${rabbitmq.guest-delete-bindingKey}")
    private String guestDeleteBindingKey;

    @Value("${rabbitmq.guest-forgot-password-queue}")
    private String guestForgotPasswordQueueName;
    @Value("${rabbitmq.guest-forgot-password-bindingKey}")
    private String guestForgotPasswordBindingKey;

    /*
     * @Bean ile işaretlediğimizde metot her çağırıldığında bir nesne üretiyor ve bu nesne Spring Container'ında yönetilen bir bean olarak kaydediliyor.
     * @Bean anotasyonu ile nesne oluşturulması ve yönetimi Spring konteyneri tarafından gerçekleştiriliyor.
     * Producer'dan gelen mesajları ilgili kuyruklara gönderilmesini sağlamak için Exchange kullanmalıyız.
     * Burada DirectExchange kullanıyoruz ve metodumuz geriye bir DirectExchange nesnesi dönüyor.
     * DirectExchange'imize isim olarak yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan authExchange değişkenimizi tanımlıyoruz.
     */
    @Bean
    DirectExchange authExchange(){
        return new DirectExchange(authExchange);
    }
    @Bean
    DirectExchange adminExchange(){
        return new DirectExchange(adminExchange);
    }
    @Bean
    DirectExchange managerExchange(){
        return new DirectExchange(managerExchange);
    }
    @Bean
    DirectExchange employeeExchange(){
        return new DirectExchange(employeeExchange);
    }
    @Bean
    DirectExchange guestExchange(){
        return new DirectExchange(guestExchange);
    }
    @Bean
    DirectExchange companyExchange(){
        return new DirectExchange(companyExchange);
    }
    @Bean
    DirectExchange mailExchange(){
        return new DirectExchange(mailExchange);
    }

    /*
     * @Bean anotasyonu ile nesne oluşturulması ve yönetimini yine Spring konteynerine bırakıyoruz.
     * Producer'dan gelen mesajları Exchange üzerinden alması için bir kuyruk oluşturmalıyız.
     * Burada geriye bir Queue nesnesi dönen ve içine yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan guestRegisterQueueName değişkeni veriyoruz.
     */
    @Bean
    Queue guestRegisterQueue(){
        return new Queue(guestRegisterQueueName);
    }

    /*
     * @Bean anotasyonu ile nesne oluşturulması ve yönetimini yine Spring konteynerine bırakıyoruz.
     * Geriye Binding dönen bir bağlama metodu oluşturmalıyız. Builder kullanarak gerekli parametreleri alıp mesajımızı iletmesi için bind etmeliyiz.
     * Burada bind() metodu ile hangi kuyruğa göndereceksek onu giriyoruz, to() metoduyla kullanacağımız exchange'i giriyoruz ve with() metoduyla da routingKey(yönlendirme anahtarı) girerek mesajımızı bağlıyoruz.
     * Kullanacağımız bindingKey içinde yukarıda yml dosyasından yolunu vererek karşılık gelen değeri alan guestRegisterBindingKey değişkeni veriyoruz.
     */
    @Bean
    public Binding bindingGuestRegister(Queue guestRegisterQueue, DirectExchange guestExchange){
        return BindingBuilder.bind(guestRegisterQueue).to(guestExchange).with(guestRegisterBindingKey);
    }

    @Bean
    Queue companyRegisterQueue(){
        return new Queue(companyRegisterQueueName);
    }

    @Bean
    public Binding bindingCompanyRegister(Queue companyRegisterQueue, DirectExchange managerExchange){
        return BindingBuilder.bind(companyRegisterQueue).to(managerExchange).with(companyRegisterBindingKey);
    }

    @Bean
    Queue mailQueue(){
        return new Queue(mailQueueName);
    }

    @Bean
    public Binding bindingMail(Queue mailQueue, DirectExchange authExchange){
        return BindingBuilder.bind(mailQueue).to(authExchange).with(mailBindingKey);
    }

    @Bean
    Queue authUpdateQueue(){
        return new Queue(authUpdateQueueName);
    }

    @Bean
    public Binding bindingAuthUpdate(Queue authUpdateQueue, DirectExchange authExchange){
        return BindingBuilder.bind(authUpdateQueue).to(authExchange).with(authUpdateBindingKey);
    }

    @Bean
    Queue authDeleteQueue(){
        return new Queue(authDeleteQueueName);
    }

    @Bean
    public Binding bindingAuthDelete(Queue authDeleteQueue, DirectExchange authExchange){
        return BindingBuilder.bind(authDeleteQueue).to(authExchange).with(authDeleteBindingKey);
    }

    @Bean
    Queue adminSaveQueue(){
        return new Queue(adminSaveQueueName);
    }

    @Bean
    public Binding bindingAdminSave(Queue adminSaveQueue, DirectExchange adminExchange){
        return BindingBuilder.bind(adminSaveQueue).to(adminExchange).with(adminSaveBindingKey);
    }

    @Bean
    Queue adminUpdateQueue(){
        return new Queue(adminUpdateQueueName);
    }

    @Bean
    public Binding bindingAdminUpdate(Queue adminUpdateQueue, DirectExchange adminExchange){
        return BindingBuilder.bind(adminUpdateQueue).to(adminExchange).with(adminUpdateBindingKey);
    }

    @Bean
    Queue adminDeleteQueue(){
        return new Queue(adminDeleteQueueName);
    }

    @Bean
    public Binding bindingAdminDelete(Queue adminDeleteQueue, DirectExchange adminExchange){
        return BindingBuilder.bind(adminDeleteQueue).to(adminExchange).with(adminDeleteBindingKey);
    }

    @Bean
    Queue managerSaveQueue(){
        return new Queue(managerSaveQueueName);
    }

    @Bean
    public Binding bindingManagerSave(Queue managerSaveQueue, DirectExchange managerExchange){
        return BindingBuilder.bind(managerSaveQueue).to(managerExchange).with(managerSaveBindingKey);
    }

    @Bean
    Queue managerUpdateQueue(){
        return new Queue(managerUpdateQueueName);
    }

    @Bean
    public Binding bindingManagerUpdate(Queue managerUpdateQueue, DirectExchange managerExchange){
        return BindingBuilder.bind(managerUpdateQueue).to(managerExchange).with(managerUpdateBindingKey);
    }

    @Bean
    Queue managerDeleteQueue(){
        return new Queue(managerDeleteQueueName);
    }

    @Bean
    public Binding bindingManagerDelete(Queue managerDeleteQueue, DirectExchange managerExchange){
        return BindingBuilder.bind(managerDeleteQueue).to(managerExchange).with(managerDeleteBindingKey);
    }

    @Bean
    Queue managerForgotPasswordQueue(){
        return new Queue(managerForgotPasswordQueueName);
    }

    @Bean
    public Binding bindingManagerForgotPassword(Queue managerForgotPasswordQueue, DirectExchange managerExchange){
        return BindingBuilder.bind(managerForgotPasswordQueue).to(managerExchange).with(managerForgotPasswordBindingKey);
    }

    @Bean
    Queue managerAddCompanyQueue(){
        return new Queue(managerAddCompanyQueueName);
    }

    @Bean
    public Binding bindingManagerAddCompany(Queue managerAddCompanyQueue, DirectExchange managerExchange){
        return BindingBuilder.bind(managerAddCompanyQueue).to(managerExchange).with(managerAddCompanyBindingKey);
    }

    @Bean
    Queue managerUpdateCompanyQueue(){
        return new Queue(managerUpdateCompanyQueueName);
    }

    @Bean
    public Binding bindingManagerUpdateCompany(Queue managerUpdateCompanyQueue, DirectExchange managerExchange){
        return BindingBuilder.bind(managerUpdateCompanyQueue).to(managerExchange).with(managerUpdateCompanyBindingKey);
    }

    @Bean
    Queue managerDeleteCompanyQueue(){
        return new Queue(managerDeleteCompanyQueueName);
    }

    @Bean
    public Binding bindingManagerDeleteCompany(Queue managerDeleteCompanyQueue, DirectExchange managerExchange){
        return BindingBuilder.bind(managerDeleteCompanyQueue).to(managerExchange).with(managerDeleteCompanyBindingKey);
    }

    @Bean
    Queue managerAddEmployeeQueue(){
        return new Queue(managerAddEmployeeQueueName);
    }

    @Bean
    public Binding bindingManagerAddEmployee(Queue managerAddEmployeeQueue, DirectExchange managerExchange){
        return BindingBuilder.bind(managerAddEmployeeQueue).to(managerExchange).with(managerAddEmployeeBindingKey);
    }

    @Bean
    Queue managerUpdateEmployeeQueue(){
        return new Queue(managerUpdateEmployeeQueueName);
    }

    @Bean
    public Binding bindingManagerUpdateEmployee(Queue managerUpdateEmployeeQueue, DirectExchange managerExchange){
        return BindingBuilder.bind(managerUpdateEmployeeQueue).to(managerExchange).with(managerUpdateEmployeeBindingKey);
    }

    @Bean
    Queue managerDeleteEmployeeQueue(){
        return new Queue(managerDeleteEmployeeQueueName);
    }

    @Bean
    public Binding bindingManagerDeleteEmployee(Queue managerDeleteEmployeeQueue, DirectExchange managerExchange){
        return BindingBuilder.bind(managerDeleteEmployeeQueue).to(managerExchange).with(managerDeleteEmployeeBindingKey);
    }

    @Bean
    Queue employeeSaveQueue(){
        return new Queue(employeeSaveQueueName);
    }

    @Bean
    public Binding bindingEmployeeSave(Queue employeeSaveQueue, DirectExchange employeeExchange){
        return BindingBuilder.bind(employeeSaveQueue).to(employeeExchange).with(employeeSaveBindingKey);
    }

    @Bean
    Queue employeeUpdateQueue(){
        return new Queue(employeeUpdateQueueName);
    }

    @Bean
    public Binding bindingEmployeeUpdate(Queue employeeUpdateQueue, DirectExchange employeeExchange){
        return BindingBuilder.bind(employeeUpdateQueue).to(employeeExchange).with(employeeUpdateBindingKey);
    }

    @Bean
    Queue employeeDeleteQueue(){
        return new Queue(employeeDeleteQueueName);
    }

    @Bean
    public Binding bindingEmployeeDelete(Queue employeeDeleteQueue, DirectExchange employeeExchange){
        return BindingBuilder.bind(employeeDeleteQueue).to(employeeExchange).with(employeeDeleteBindingKey);
    }

    @Bean
    Queue employeeForgotPasswordQueue(){
        return new Queue(employeeForgotPasswordQueueName);
    }

    @Bean
    public Binding bindingEmployeeForgotPassword(Queue employeeForgotPasswordQueue, DirectExchange employeeExchange){
        return BindingBuilder.bind(employeeForgotPasswordQueue).to(employeeExchange).with(employeeForgotPasswordBindingKey);
    }

    @Bean
    Queue guestSaveQueue(){
        return new Queue(guestSaveQueueName);
    }

    @Bean
    public Binding bindingGuestSave(Queue guestSaveQueue, DirectExchange guestExchange){
        return BindingBuilder.bind(guestSaveQueue).to(guestExchange).with(guestSaveBindingKey);
    }

    @Bean
    Queue guestUpdateQueue(){
        return new Queue(guestUpdateQueueName);
    }

    @Bean
    public Binding bindingGuestUpdate(Queue guestUpdateQueue, DirectExchange guestExchange){
        return BindingBuilder.bind(guestUpdateQueue).to(guestExchange).with(guestUpdateBindingKey);
    }

    @Bean
    Queue guestDeleteQueue(){
        return new Queue(guestDeleteQueueName);
    }

    @Bean
    public Binding bindingGuestDelete(Queue guestDeleteQueue, DirectExchange guestExchange){
        return BindingBuilder.bind(guestDeleteQueue).to(guestExchange).with(guestDeleteBindingKey);
    }

    @Bean
    Queue guestForgotPasswordQueue(){
        return new Queue(guestForgotPasswordQueueName);
    }

    @Bean
    public Binding bindingGuestForgotPassword(Queue guestForgotPasswordQueue, DirectExchange guestExchange){
        return BindingBuilder.bind(guestForgotPasswordQueue).to(guestExchange).with(guestForgotPasswordBindingKey);
    }

    /*
     * Model sınıflarını Serializable interface'inden implement ederek serilize ve deserilize işlemi yaptığımız için Jackson2JsonMessageConverter yöntemini kullanmıyoruz.
     * Jackson2JsonMessageConverter yöntemini kullanırsak model sınıflarını Serializable interface'inden implement etmeye gerek kalmıyor.
     * MessageConverter metodunda nesne-JSON dönüşümü için bir converter ekliyoruz ve metot geriye bir converter dönüyor ve bu sayede nesne-JSON dönüşümünü yapabiliyoruz.
     * RabbitTemplate metodunda ise converter aktifleştirmek için RabbitTemplate içinde tanımlıyoruz.
     * Bu converter yani dönüştürme işini RabbitTemplate direkt kullanamıyor. Bundan dolayı messageConverter olarak yukarıda oluşturduğumuz converter set ediyoruz ve bu şekilde kullanabiliyoruz.
     */
    /*@Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }*/

}
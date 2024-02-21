package org.hrms.service;

import org.hrms.repository.ICompanyRepository;
import org.hrms.repository.entity.Company;
import org.hrms.utility.ServiceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 *
 * ICompanyRepository interface'imizin MongoRepository'den miras alması gibi biz de kendi ServiceManager sınıfımızı yazdık ve CompanyService sınıfımızın oradan miras almasını sağladık.
 * Bu şekilde MongoRepository içindeki hazır metotlara ICompanyRepository üzerinden ulaşabildiğimiz gibi biz de ServiceManager içindeki yazdığımız metotlara ulaşabileceğiz.
 * ServiceManager parametreleri de ilgili entity sınıfını ve bu sınıfın birincil anahtar türünü içerir.
 */
@Service
public class CompanyService extends ServiceManager<Company, String> {

    private final ICompanyRepository repository;

    public CompanyService(MongoRepository<Company, String> mongoRepository, ICompanyRepository repository) {
        super(mongoRepository);
        this.repository = repository;
    }
}

package org.hrms.service;

import org.hrms.dto.request.CompanySaveRequestDto;
import org.hrms.dto.request.CompanySaveResponseDto;
import org.hrms.exception.CompanyServiceException;
import org.hrms.exception.ErrorType;
import org.hrms.mapper.ICompanyMapper;
import org.hrms.rabbitmq.model.CompanySetManagerIdModel;
import org.hrms.rabbitmq.model.ManagerSetCompanyIdModel;
import org.hrms.rabbitmq.producer.ManagerSetCompanyIdProducer;
import org.hrms.repository.ICompanyRepository;
import org.hrms.repository.entity.Company;
import org.hrms.utility.ServiceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    private final ManagerSetCompanyIdProducer managerSetCompanyIdProducer;

    public CompanyService(MongoRepository<Company, String> mongoRepository, ICompanyRepository repository, ManagerSetCompanyIdProducer managerSetCompanyIdProducer) {
        super(mongoRepository);
        this.repository = repository;
        this.managerSetCompanyIdProducer = managerSetCompanyIdProducer;
    }

    public CompanySaveResponseDto saveCompany(CompanySaveRequestDto dto) {

        if (repository.existsByCompanyName(dto.getCompanyName()) || repository.existsByCompanyPhoneNumber(dto.getCompanyPhoneNumber()) || repository.existsByInfoEmail(dto.getInfoEmail())) {
            throw new CompanyServiceException(ErrorType.PARAMETER_ALREADY_EXISTS);
        }

        Company company = ICompanyMapper.INSTANCE.companySaveRequestDtoToCompany(dto);
        save(company);

        ManagerSetCompanyIdModel managerSetCompanyIdModel = ManagerSetCompanyIdModel.builder()
                .companyId(company.getId())
                .companyName(company.getCompanyName())
                .build();
        managerSetCompanyIdProducer.convertAndSend(managerSetCompanyIdModel);

        return ICompanyMapper.INSTANCE.companyToCompanySaveResponseDto(company);
    }

    public void setManagerId(CompanySetManagerIdModel model) {

        Optional<Company> optionalCompany = repository.findOptionalByCompanyName(model.getCompanyName());
        if (optionalCompany.isEmpty()) {
            throw new CompanyServiceException(ErrorType.COMPANY_NAME_NOT_FOUND);
        }

        optionalCompany.get().setManagerId(model.getManagerId());
        update(optionalCompany.get());
    }
}

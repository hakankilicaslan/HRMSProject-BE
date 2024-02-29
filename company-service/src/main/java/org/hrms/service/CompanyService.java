package org.hrms.service;

import jakarta.annotation.PostConstruct;
import org.hrms.dto.request.CompanySaveRequestDto;
import org.hrms.dto.request.CompanyUpdateRequestDto;
import org.hrms.dto.response.CompanySaveResponseDto;
import org.hrms.dto.response.FindAllCompaniesResponseDto;
import org.hrms.dto.response.FindCompanyByCompanyNameResponseDto;
import org.hrms.dto.response.FindCompanyByIdResponseDto;
import org.hrms.exception.CompanyServiceException;
import org.hrms.exception.ErrorType;
import org.hrms.mapper.ICompanyMapper;
import org.hrms.rabbitmq.model.CompanySetManagerIdModel;
import org.hrms.rabbitmq.model.ManagerSetCompanyIdModel;
import org.hrms.rabbitmq.producer.ManagerSetCompanyIdProducer;
import org.hrms.repository.ICompanyRepository;
import org.hrms.repository.entity.Company;
import org.hrms.repository.enums.EStatus;
import org.hrms.utility.ServiceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public String softUpdate(CompanyUpdateRequestDto dto) {

        Optional<Company> optionalCompany = findById(dto.getId());
        if (optionalCompany.isEmpty()) {
            throw new CompanyServiceException(ErrorType.COMPANY_NAME_NOT_FOUND);
        }
        Company updatedCompany = optionalCompany.get();

        if (updatedCompany.getStatus().equals(EStatus.DELETED)) {
            throw new CompanyServiceException(ErrorType.COMPANY_ALREADY_DELETED);
        }

        if (repository.existsByCompanyName(dto.getCompanyName()) || repository.existsByCompanyPhoneNumber(dto.getCompanyPhoneNumber()) || repository.existsByInfoEmail(dto.getInfoEmail())) {
            throw new CompanyServiceException(ErrorType.PARAMETER_ALREADY_EXISTS);
        }

        if (dto.getCompanyName() != null) {
            updatedCompany.setCompanyName(dto.getCompanyName());
        }
        if (dto.getCompanyPhoneNumber() != null) {
            updatedCompany.setCompanyPhoneNumber(dto.getCompanyPhoneNumber());
        }
        if (dto.getInfoEmail() != null) {
            updatedCompany.setInfoEmail(dto.getInfoEmail());
        }
        if (dto.getCompanyAddress() != null) {
            updatedCompany.setCompanyAddress(dto.getCompanyAddress());
        }
        if (dto.getLogo() != null) {
            updatedCompany.setLogo(dto.getLogo());
        }
        if (dto.getRevenue() != null) {
            updatedCompany.setRevenue(dto.getRevenue());
        }
        if (dto.getExpense() != null) {
            updatedCompany.setExpense(dto.getExpense());
        }
        if (dto.getProfit() != null) {
            updatedCompany.setProfit(dto.getProfit());
        }
        if (dto.getLoss() != null) {
            updatedCompany.setLoss(dto.getLoss());
        }
        if (dto.getNetIncome() != null) {
            updatedCompany.setNetIncome(dto.getNetIncome());
        }
        if (dto.getSalaries() != null) {
            updatedCompany.setSalaries(dto.getSalaries());
        }
        if (dto.getEmployees() != null) {
            updatedCompany.setEmployees(dto.getEmployees());
        }
        if (dto.getShifts() != null) {
            updatedCompany.setShifts(dto.getShifts());
        }
        if (dto.getHolidays() != null) {
            updatedCompany.setHolidays(dto.getHolidays());
        }

        update(updatedCompany);

        return "Successfully updated.";
    }


    public String softDelete(String id) {

        Optional<Company> optionalCompany = findById(id);
        if (optionalCompany.isEmpty()) {
            throw new CompanyServiceException(ErrorType.COMPANY_NAME_NOT_FOUND);
        }
        Company deletedCompany = optionalCompany.get();

        if (deletedCompany.getStatus().equals(EStatus.DELETED)) {
            throw new CompanyServiceException(ErrorType.COMPANY_ALREADY_DELETED);
        }

        deletedCompany.setStatus(EStatus.DELETED);
        update(deletedCompany);

        return "The company named " + deletedCompany.getCompanyName() + " has been deleted";

    }


    public List<FindAllCompaniesResponseDto> findAllCompanies() {
        return findAll().stream()
                .filter(item -> item.getStatus() == EStatus.ACTIVE)
                .map(ICompanyMapper.INSTANCE::companyToFindAllCompaniesResponseDto)
                .collect(Collectors.toList());
    }

    public FindCompanyByIdResponseDto findCompanyById(String id) {

        Optional<Company> optionalCompany = findById(id);
        if (optionalCompany.isEmpty()) {
            throw new CompanyServiceException(ErrorType.COMPANY_NAME_NOT_FOUND);
        }

        if(optionalCompany.get().getStatus() == EStatus.ACTIVE) {
            return ICompanyMapper.INSTANCE.companyToFindCompanyByIdResponseDto(optionalCompany.get());
        } else {
            throw new CompanyServiceException(ErrorType.COMPANY_NOT_ACTIVE);
        }
    }


    public FindCompanyByIdResponseDto findCompanyByManagerId(Long managerId) {

        Optional<Company> optionalCompany = repository.findOptionalByManagerId(managerId);
        if (optionalCompany.isEmpty()) {
            throw new CompanyServiceException(ErrorType.COMPANY_NAME_NOT_FOUND);
        }

        if(optionalCompany.get().getStatus() == EStatus.ACTIVE) {
            return ICompanyMapper.INSTANCE.companyToFindCompanyByIdResponseDto(optionalCompany.get());
        } else {
            throw new CompanyServiceException(ErrorType.COMPANY_NOT_ACTIVE);
        }
    }


    public FindCompanyByCompanyNameResponseDto findCompanyByCompanyName(String companyName) {

        Optional<Company> optionalCompany = repository.findOptionalByCompanyName(companyName);
        if (optionalCompany.isEmpty()) {
            throw new CompanyServiceException(ErrorType.COMPANY_NAME_NOT_FOUND);
        }

        if(optionalCompany.get().getStatus() == EStatus.ACTIVE) {
            return ICompanyMapper.INSTANCE.companyToFindCompanyByCompanyNameResponseDto(optionalCompany.get());
        } else {
            throw new CompanyServiceException(ErrorType.COMPANY_NOT_ACTIVE);
        }
    }

    public Integer findNumbersOfCompanies() {
        return findAll().stream()
                .filter(item -> item.getStatus() == EStatus.ACTIVE)
                .toList()
                .size();
    }

    /*
     * @PostConstruct anotasyonu, Java'nın Enterprise Edition (Java EE) ve Spring Framework gibi bazı çerçevelerde kullanılan bir anotasyondur.
     * Bu anotasyon, bir sınıfın veya yönteminin, ilgili nesnenin oluşturulmasından hemen sonra çalıştırılmasını sağlar.
     * Bu şekilde sistem ayağa kalktığında ilk bu metot çalıştırılacak ve veritabanına eklenmiş şirket yoksa aşağıda girdiğimiz şirketleri veritabanına kaydedecek.
     */
    @PostConstruct
    private void addDefaultCompany() {
        long numberOfCompanies = repository.count();
        if (numberOfCompanies == 0) {
            save(Company.builder().companyName("TUPRAŞ").companyPhoneNumber("02124441230").infoEmail("info@tupras.com").logo("https://www.tupras.com.tr/assets/img/logo/koc-logo.png").companyAddress("Gülbahar Mah. Büyükdere Cad. No: 101/A 34394 Şişli - İSTANBUL").about("60 yılı aşkın süredir Türkiye’nin enerjisini kesintisiz üretiyor, Ülkemiz ve tüm paydaşlarımız için katma değer sağlıyoruz.").build());
            save(Company.builder().companyName("BILGE ADAM").companyPhoneNumber("08502016000").infoEmail("info@bilgeadam.com").logo("https://cdn.cloudwises.com/ba-assets/genel/icon.svg").companyAddress("Reşitpaşa Mah. Katar Cad. İTÜ Teknokent Arı 3 No:4 B3 Sarıyer / İSTANBUL").about("BilgeAdam Teknoloji; 1997’de İstanbul’da kurulmuş, global operasyonlarını İngiltere ve Avrupa üzerinden yaygınlaştıran bir yazılım şirketidir.").build());
            save(Company.builder().companyName("PETKIM.").companyPhoneNumber("02123050000").infoEmail("info@petkim.com").logo("https://www.petkim.com.tr/assets/uploads/petkim.png").companyAddress("Siteler Mahallesi Necmettin Giritlioğlu Cad. SOCAR Türkiye Aliağa Yönetim Binası No 6/1 Aliağa-İzmir/Türkiye").about("Sürdürülebilir değer yaratmak için endüstriyel ürünler üreten Türkiye’nin ilk ve tek entegre petrokimya şirketiyiz.").build());
            save(Company.builder().companyName("ARÇELİK A.Ş").companyPhoneNumber("02123143434").infoEmail("info@arcelik.com").logo("https://www.arcelikglobal.com/Content/images/navbar/logo-en.png").companyAddress("Arçelik A.Ş. Karaağaç Caddesi 2-6 Sütlüce Beyoğlu 34445 İstanbul").about("Arçelik, 45.000 çalışanı, 12 markası 9 ülkede, 30 üretim tesisi ve 53 ülkedeki iştirakleriyle global olarak faaliyet göstermektedir.").build());
            save(Company.builder().companyName("ASELSAN").companyPhoneNumber("02164930406").infoEmail("info@aselsan.com").logo("https://www.aselsan.com/assets/logo-white.png").companyAddress("ASELSAN Teknopark Sanayi Mahallesi Teknopark Bulvarı No:1/5A 303 34906 Pendik- İstanbul, Türkiye").about("Türkiye’nin en büyük savunma elektroniği kuruluşu olan firmadır").build());
            save(Company.builder().companyName("FORTE SAV. SAN. A.Ş").companyPhoneNumber("08508094887").infoEmail("info@forte.com").logo("https://forte.com.tr/assets/imgs/imgsa/logo.webp").companyAddress("Mustafa Kemal Mahallesi 2123.Cadde Cepa Sitesi Alışveriş Merkezi No:2/501 Çankaya/ANKARA").about("Forte, sistem entegrasyonu ve yazılım geliştirme alanlarında faaliyet göstermek üzere 2006 yılında kurulmuş olan, kamu kurumlarında ve savunma sanayii endüstrisinde çalışmasına devam eden %100 bir Türk firmasıdır.").build());
        }
    }





}

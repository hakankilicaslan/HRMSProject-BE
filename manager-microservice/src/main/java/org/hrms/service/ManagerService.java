package org.hrms.service;

import org.hrms.dto.request.ManagerUpdateRequestDto;
import org.hrms.dto.response.FindAllManagersResponseDto;
import org.hrms.dto.response.FindManagerByIdResponseDto;
import org.hrms.exception.ErrorType;
import org.hrms.exception.ManagerServiceException;
import org.hrms.mapper.IManagerMapper;
import org.hrms.rabbitmq.model.AuthDeleteModel;
import org.hrms.rabbitmq.model.AuthUpdateModel;
import org.hrms.rabbitmq.producer.AuthDeleteProducer;
import org.hrms.rabbitmq.producer.AuthUpdateProducer;
import org.hrms.repository.IManagerRepository;
import org.hrms.repository.entity.Manager;
import org.hrms.repository.enums.EStatus;
import org.hrms.utility.ServiceManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 *
 * IManagerRepository interface'imizin JpaRepository'den miras alması gibi biz de kendi ServiceManager sınıfımızı yazdık ve ManagerService sınıfımızın oradan miras almasını sağladık.
 * Bu şekilde JpaRepository içindeki hazır metotlara IManagerRepository üzerinden ulaşabildiğimiz gibi biz de ServiceManager içindeki yazdığımız metotlara ulaşabileceğiz.
 * ServiceManager parametreleri de ilgili entity sınıfını ve bu sınıfın birincil anahtar türünü içerir.
 */
@Service
public class ManagerService extends ServiceManager<Manager, Long>  {

    private final IManagerRepository repository;
    private final AuthUpdateProducer authUpdateProducer;
    private final AuthDeleteProducer authDeleteProducer;

    public ManagerService(JpaRepository<Manager, Long> jpaRepository, IManagerRepository repository, AuthUpdateProducer authUpdateProducer, AuthDeleteProducer authDeleteProducer) {
        super(jpaRepository);
        this.repository = repository;
        this.authUpdateProducer = authUpdateProducer;
        this.authDeleteProducer = authDeleteProducer;
    }

    public String softUpdate(ManagerUpdateRequestDto dto) {

        Optional<Manager> optionalManager = findById(dto.getId());
        if (optionalManager.isEmpty()) {
            throw new ManagerServiceException(ErrorType.USER_NOT_FOUND);
        }
        Manager manager = optionalManager.get();

        if (manager.getStatus().equals(EStatus.DELETED)) {
            throw new ManagerServiceException(ErrorType.USER_ALREADY_DELETED);
        }

        if (repository.existsByEmail(manager.getEmail()) || repository.existsByPhoneNumber(manager.getPhoneNumber()) || repository.existsByIdentityNumber(manager.getIdentityNumber())) {
            throw new ManagerServiceException(ErrorType.PARAMETER_ALREADY_EXISTS);
        }

        Manager updatedManager = IManagerMapper.INSTANCE.managerUpdateRequestDtoToManager(dto);
        update(updatedManager);

        AuthUpdateModel authUpdateModel = IManagerMapper.INSTANCE.managerToAuthUpdateModel(updatedManager);
        authUpdateProducer.convertAndSend(authUpdateModel);

        return "Successfully updated.";
    }

    public String softDelete(Long id) {

        Optional<Manager> optionalManager = findById(id);
        if (optionalManager.isEmpty()) {
            throw new ManagerServiceException(ErrorType.USER_NOT_FOUND);
        }
        Manager deletedManager = optionalManager.get();

        if (deletedManager.getStatus().equals(EStatus.DELETED)) {
            throw new ManagerServiceException(ErrorType.USER_ALREADY_DELETED);
        }
        
        deletedManager.setStatus(EStatus.DELETED);
        save(deletedManager);

        authDeleteProducer.convertAndSend(AuthDeleteModel.builder()
                .authId(deletedManager.getAuthId())
                .status(deletedManager.getStatus())
                .build());

        return deletedManager.getName() + deletedManager.getSurname() + " user named has been deleted";
    }

    public List<FindAllManagersResponseDto> findAllManagers() {
        return findAll().stream().map(IManagerMapper.INSTANCE::managerToFindAllManagersResponseDto).collect(Collectors.toList());
    }

    public FindManagerByIdResponseDto findManagerById(Long id) {

        Optional<Manager> optionalManager = findById(id);
        if (optionalManager.isEmpty()) {
            throw new ManagerServiceException(ErrorType.USER_NOT_FOUND);
        }

        return IManagerMapper.INSTANCE.managerToFindManagerByIdResponseDto(optionalManager.get());
    }

    public FindManagerByIdResponseDto findManagerByAuthId(Long authId) {

        Optional<Manager> optionalManager = repository.findOptionalByAuthId(authId);
        if (optionalManager.isEmpty()) {
            throw new ManagerServiceException(ErrorType.USER_NOT_FOUND);
        }

        return IManagerMapper.INSTANCE.managerToFindManagerByIdResponseDto(optionalManager.get());
    }

    public FindManagerByIdResponseDto findManagerByCompanyId(Long companyId) {

        Optional<Manager> optionalManager = repository.findOptionalByCompanyId(companyId);
        if (optionalManager.isEmpty()) {
            throw new ManagerServiceException(ErrorType.USER_NOT_FOUND);
        }

        return IManagerMapper.INSTANCE.managerToFindManagerByIdResponseDto(optionalManager.get());
    }

    public List<FindAllManagersResponseDto> findManagersByCompanyName(String companyName) {

        if (!repository.existsByCompanyName(companyName)){
            throw new ManagerServiceException(ErrorType.COMPANY_NAME_NOT_FOUND);
        }
        List<Manager> managersList = repository.findManagersByCompanyName(companyName);

        return managersList.stream().map(IManagerMapper.INSTANCE::managerToFindAllManagersResponseDto).collect(Collectors.toList());
    }

}

package org.hrms.service;

import org.hrms.dto.request.*;
import org.hrms.dto.response.*;
import org.hrms.exception.*;
import org.hrms.mapper.IAdminMapper;
import org.hrms.rabbitmq.model.*;
import org.hrms.rabbitmq.producer.*;
import org.hrms.repository.IAdminRepository;
import org.hrms.repository.entity.Admin;
import org.hrms.repository.enums.ERole;
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
 * IAdminRepository interface'imizin JpaRepository'den miras alması gibi biz de kendi ServiceManager sınıfımızı yazdık ve AdminService sınıfımızın oradan miras almasını sağladık.
 * Bu şekilde JpaRepository içindeki hazır metotlara IAdminRepository üzerinden ulaşabildiğimiz gibi biz de ServiceManager içindeki yazdığımız metotlara ulaşabileceğiz.
 * ServiceManager parametreleri de ilgili entity sınıfını ve bu sınıfın birincil anahtar türünü içerir.
 */
@Service
public class AdminService extends ServiceManager<Admin, Long> {

    public final IAdminRepository repository;
    public final AdminSaveProducer adminSaveProducer;
    private final AuthUpdateProducer authUpdateProducer;
    private final AuthDeleteProducer authDeleteProducer;

    public AdminService(JpaRepository<Admin, Long> jpaRepository, IAdminRepository repository, AdminSaveProducer adminSaveProducer, AuthUpdateProducer authUpdateProducer, AuthDeleteProducer authDeleteProducer) {
        super(jpaRepository);
        this.repository = repository;
        this.adminSaveProducer = adminSaveProducer;
        this.authUpdateProducer = authUpdateProducer;
        this.authDeleteProducer = authDeleteProducer;
    }

    public AdminSaveResponseDto saveAdmin(AdminSaveRequestDto dto) {

        if (repository.existsByEmail(dto.getEmail()) || repository.existsByPhoneNumber(dto.getPhoneNumber()) || repository.existsByIdentityNumber(dto.getIdentityNumber())) {
            throw new AdminServiceException(ErrorType.PARAMETER_ALREADY_EXISTS);
        }

        Admin admin = IAdminMapper.INSTANCE.adminSaveRequestDtoToAdmin(dto);
        admin.setRole(ERole.ADMIN);
        save(admin);

        AdminSaveModel adminSaveModel = IAdminMapper.INSTANCE.adminToAdminSaveModel(admin);
        adminSaveProducer.convertAndSend(adminSaveModel);

        return IAdminMapper.INSTANCE.adminToAdminSaveResponseDto(admin);
    }

    public void setAuthId(AdminSaveSetAuthIdModel model) {

        Optional<Admin> optionalAdmin = repository.findOptionalByEmail(model.getEmail());
        if (optionalAdmin.isEmpty()) {
            throw new AdminServiceException(ErrorType.USER_NOT_FOUND);
        }

        //Burada model ile gelen authId önceden kaydedilmiş mi diye bakmaya gerek var mı

        optionalAdmin.get().setAuthId(model.getAuthId());
        update(optionalAdmin.get());
    }

    public String softUpdate(AdminUpdateRequestDto dto) {

        Optional<Admin> optionalAdmin = findById(dto.getId());
        if (optionalAdmin.isEmpty()) {
            throw new AdminServiceException(ErrorType.USER_NOT_FOUND);
        }
        Admin updatedAdmin = optionalAdmin.get();

        if (updatedAdmin.getStatus().equals(EStatus.DELETED)) {
            throw new AdminServiceException(ErrorType.USER_ALREADY_DELETED);
        }

        if (repository.existsByEmail(dto.getEmail()) || repository.existsByPhoneNumber(dto.getPhoneNumber()) || repository.existsByIdentityNumber(dto.getIdentityNumber())) {
            throw new AdminServiceException(ErrorType.PARAMETER_ALREADY_EXISTS);
        }

        if (dto.getName() != null) {
            updatedAdmin.setName(dto.getName());
        }
        if (dto.getSurname() != null) {
            updatedAdmin.setSurname(dto.getSurname());
        }
        if (dto.getPhoneNumber() != null) {
            updatedAdmin.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getIdentityNumber() != null) {
            updatedAdmin.setIdentityNumber(dto.getIdentityNumber());
        }
        if (dto.getEmail() != null) {
            updatedAdmin.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            updatedAdmin.setPassword(dto.getPassword());
        }
        if (dto.getAddress() != null) {
            updatedAdmin.setAddress(dto.getAddress());
        }
        if (dto.getGender() != null) {
            updatedAdmin.setGender(dto.getGender());
        }

        update(updatedAdmin);

        AuthUpdateModel authUpdateModel = IAdminMapper.INSTANCE.adminToAuthUpdateModel(updatedAdmin);
        authUpdateProducer.convertAndSend(authUpdateModel);

        return "Successfully updated.";
    }

    public String softDelete(Long id) {

        Optional<Admin> optionalAdmin = findById(id);
        if (optionalAdmin.isEmpty()) {
            throw new AdminServiceException(ErrorType.USER_NOT_FOUND);
        }
        Admin deletedAdmin = optionalAdmin.get();

        if (deletedAdmin.getStatus().equals(EStatus.DELETED)) {
            throw new AdminServiceException(ErrorType.USER_ALREADY_DELETED);
        }

        deletedAdmin.setStatus(EStatus.DELETED);
        update(deletedAdmin);

        authDeleteProducer.convertAndSend(AuthDeleteModel.builder()
                .authId(deletedAdmin.getAuthId())
                .status(deletedAdmin.getStatus())
                .build());

        return deletedAdmin.getName() + " " + deletedAdmin.getSurname() + " user named has been deleted";
    }

    public List<FindAllAdminsResponseDto> findAllAdmins() {
        return findAll().stream()
                .filter(item -> item.getStatus() == EStatus.ACTIVE)
                .map(IAdminMapper.INSTANCE::adminToFindAllAdminsResponseDto)
                .collect(Collectors.toList());
    }

    public FindAdminByIdResponseDto findAdminById(Long id) {

        Optional<Admin> optionalAdmin = findById(id);
        if (optionalAdmin.isEmpty()) {
            throw new AdminServiceException(ErrorType.USER_NOT_FOUND);
        }

        if(optionalAdmin.get().getStatus() == EStatus.ACTIVE) {
            return IAdminMapper.INSTANCE.adminToFindAdminByIdResponseDto(optionalAdmin.get());
        } else {
            throw new AdminServiceException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
    }

    public FindAdminByIdResponseDto findAdminByAuthId(Long authId) {

        Optional<Admin> optionalAdmin = repository.findOptionalByAuthId(authId);
        if (optionalAdmin.isEmpty()) {
            throw new AdminServiceException(ErrorType.USER_NOT_FOUND);
        }

        if(optionalAdmin.get().getStatus() == EStatus.ACTIVE) {
            return IAdminMapper.INSTANCE.adminToFindAdminByIdResponseDto(optionalAdmin.get());
        } else {
            throw new AdminServiceException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
    }



}

package org.hrms.service;

import org.hrms.dto.request.GuestUpdateRequestDto;
import org.hrms.dto.response.FindAllGuestsResponseDto;
import org.hrms.dto.response.FindGuestByIdResponseDto;
import org.hrms.exception.ErrorType;
import org.hrms.exception.GuestServiceException;
import org.hrms.mapper.IGuestMapper;
import org.hrms.rabbitmq.model.AuthDeleteModel;
import org.hrms.rabbitmq.model.AuthForgotPasswordModel;
import org.hrms.rabbitmq.model.AuthUpdateModel;
import org.hrms.rabbitmq.producer.AuthDeleteProducer;
import org.hrms.rabbitmq.producer.AuthUpdateProducer;
import org.hrms.repository.IGuestRepository;
import org.hrms.repository.entity.Guest;
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
 * IGuestRepository interface'imizin JpaRepository'den miras alması gibi biz de kendi ServiceManager sınıfımızı yazdık ve GuestService sınıfımızın oradan miras almasını sağladık.
 * Bu şekilde JpaRepository içindeki hazır metotlara IGuestRepository üzerinden ulaşabildiğimiz gibi biz de ServiceManager içindeki yazdığımız metotlara ulaşabileceğiz.
 * ServiceManager parametreleri de ilgili entity sınıfını ve bu sınıfın birincil anahtar türünü içerir.
 */
@Service
public class GuestService extends ServiceManager<Guest, Long> {

    private final IGuestRepository repository;
    private final AuthUpdateProducer authUpdateProducer;
    private final AuthDeleteProducer authDeleteProducer;

    public GuestService(JpaRepository<Guest, Long> jpaRepository, IGuestRepository repository, AuthUpdateProducer authUpdateProducer, AuthDeleteProducer authDeleteProducer) {
        super(jpaRepository);
        this.repository = repository;
        this.authUpdateProducer = authUpdateProducer;
        this.authDeleteProducer = authDeleteProducer;
    }

    public String softUpdate(GuestUpdateRequestDto dto) {

        Optional<Guest> optionalGuest = findById(dto.getId());
        if (optionalGuest.isEmpty()) {
            throw new GuestServiceException(ErrorType.USER_NOT_FOUND);
        }
        Guest updatedGuest = optionalGuest.get();

        if (updatedGuest.getStatus().equals(EStatus.DELETED)) {
            throw new GuestServiceException(ErrorType.USER_ALREADY_DELETED);
        }

        if (repository.existsByEmail(dto.getEmail()) || repository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new GuestServiceException(ErrorType.EMAIL_OR_PHONE_ALREADY_EXISTS);
        }

        if (dto.getName() != null) {
            updatedGuest.setName(dto.getName());
        }
        if (dto.getSurname() != null) {
            updatedGuest.setSurname(dto.getSurname());
        }
        if (dto.getPhoneNumber() != null) {
            updatedGuest.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getEmail() != null) {
            updatedGuest.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            updatedGuest.setPassword(dto.getPassword());
        }
        if (dto.getGender() != null) {
            updatedGuest.setGender(dto.getGender());
        }

        update(updatedGuest);

        AuthUpdateModel authUpdateModel = IGuestMapper.INSTANCE.guestToAuthUpdateModel(updatedGuest);
//       AuthUpdateModel authUpdateModel = AuthUpdateModel.builder()
//              .authId(updatedGuest.getAuthId())
//              .name(updatedGuest.getName())
//              .surname(updatedGuest.getSurname())
//              .phoneNumber(updatedGuest.getPhoneNumber())
//              .email(updatedGuest.getEmail())
//              .password(updatedGuest.getPassword())
//              .gender(updatedGuest.getGender())
//              .build();
        authUpdateProducer.convertAndSend(authUpdateModel);

        return "Successfully updated.";
    }

    public String softDelete(Long id) {

        Optional<Guest> optionalGuest = findById(id);
        if (optionalGuest.isEmpty()) {
            throw new GuestServiceException(ErrorType.USER_NOT_FOUND);
        }
        Guest deletedGuest = optionalGuest.get();

        if (deletedGuest.getStatus().equals(EStatus.DELETED)) {
            throw new GuestServiceException(ErrorType.USER_ALREADY_DELETED);
        }

        deletedGuest.setStatus(EStatus.DELETED);
        update(deletedGuest);

        authDeleteProducer.convertAndSend(AuthDeleteModel.builder()
                .authId(deletedGuest.getAuthId())
                .status(deletedGuest.getStatus())
                .build());

        return deletedGuest.getName() + " " + deletedGuest.getSurname() + " user named has been deleted";

    }

    public List<FindAllGuestsResponseDto> findAllGuests() {
        return findAll().stream()
                .filter(item -> item.getStatus() == EStatus.ACTIVE)
                .map(IGuestMapper.INSTANCE::guestToFindAllGuestsResponseDto)
                .collect(Collectors.toList());
    }

    public FindGuestByIdResponseDto findGuestById(Long id) {

        Optional<Guest> optionalGuest = findById(id);
        if (optionalGuest.isEmpty()) {
            throw new GuestServiceException(ErrorType.USER_NOT_FOUND);
        }

        if(optionalGuest.get().getStatus() == EStatus.ACTIVE) {
            return IGuestMapper.INSTANCE.guestToFindGuestByIdResponseDto(optionalGuest.get());
        } else {
            throw new GuestServiceException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
    }

    public FindGuestByIdResponseDto findGuestByAuthId(Long authId) {

        Optional<Guest> optionalGuest = repository.findOptionalByAuthId(authId);
        if (optionalGuest.isEmpty()) {
            throw new GuestServiceException(ErrorType.USER_NOT_FOUND);
        }

        if(optionalGuest.get().getStatus() == EStatus.ACTIVE) {
            return IGuestMapper.INSTANCE.guestToFindGuestByIdResponseDto(optionalGuest.get());
        } else {
            throw new GuestServiceException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
    }

    public void activeStatus(Long authId) {

        Optional<Guest> optionalGuest = repository.findOptionalByAuthId(authId);
        if (optionalGuest.isEmpty()) {
            throw new GuestServiceException(ErrorType.USER_NOT_FOUND);
        }

        optionalGuest.get().setStatus(EStatus.ACTIVE);
        update(optionalGuest.get());
    }

    public void updatePassword(AuthForgotPasswordModel model) {

        Optional<Guest> optionalGuest = repository.findOptionalByAuthId(model.getAuthId());
        if (optionalGuest.isEmpty()) {
            throw new GuestServiceException(ErrorType.USER_NOT_FOUND);
        }

        optionalGuest.get().setPassword(model.getPassword());
    }

}

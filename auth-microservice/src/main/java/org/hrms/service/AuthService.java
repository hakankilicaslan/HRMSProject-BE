package org.hrms.service;

import org.hrms.dto.request.*;
import org.hrms.dto.response.*;
import org.hrms.exception.*;
import org.hrms.mapper.IAuthMapper;
import org.hrms.rabbitmq.model.*;
import org.hrms.rabbitmq.producer.*;
import org.hrms.repository.IAuthRepository;
import org.hrms.repository.entity.Auth;
import org.hrms.repository.enums.ERole;
import org.hrms.repository.enums.EStatus;
import org.hrms.utility.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 *
 * IAuthRepository interface'imizin JpaRepository'den miras alması gibi biz de kendi ServiceManager sınıfımızı yazdık ve AuthService sınıfımızın oradan miras almasını sağladık.
 * Bu şekilde JpaRepository içindeki hazır metotlara IAuthRepository üzerinden ulaşabildiğimiz gibi biz de ServiceManager içindeki yazdığımız metotlara ulaşabileceğiz.
 * ServiceManager parametreleri de ilgili entity sınıfını ve bu sınıfın birincil anahtar türünü içerir.
 */
@Service
public class AuthService extends ServiceManager<Auth, Long> {

    private final IAuthRepository repository;
    private final JwtTokenManager jwtTokenManager;
    private final GuestRegisterProducer guestRegisterProducer;
    private final CompanyRegisterProducer companyRegisterProducer;
    private final GuestForgotPasswordProducer guestForgotPasswordProducer;
    private final EmployeeForgotPasswordProducer employeeForgotPasswordProducer;
    private final ManagerForgotPasswordProducer managerForgotPasswordProducer;

    public AuthService(IAuthRepository repository, JwtTokenManager jwtTokenManager, GuestRegisterProducer guestRegisterProducer, CompanyRegisterProducer companyRegisterProducer, GuestForgotPasswordProducer guestForgotPasswordProducer, EmployeeForgotPasswordProducer employeeForgotPasswordProducer, ManagerForgotPasswordProducer managerForgotPasswordProducer) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
        this.guestRegisterProducer = guestRegisterProducer;
        this.companyRegisterProducer = companyRegisterProducer;
        this.guestForgotPasswordProducer = guestForgotPasswordProducer;
        this.employeeForgotPasswordProducer = employeeForgotPasswordProducer;
        this.managerForgotPasswordProducer = managerForgotPasswordProducer;
    }

    /*
     * @Transactional anotasyonu, Spring Framework içinde işlem yönetimi (transaction management) için kullanılan bir anotasyondur.
     * @Transactional ile metodumuzu işaretleyerek bazı işlemler tamamlanır ve daha sonrakilerde hata meydana gelirse, Spring otomatik olarak yapıln tüm işlemi geri alıyor.
     * Bu şekilde veritabanı önceki durumuna dönüyor yani ilgili servislerde bir tarafa kaydedilip öbür tarafa kaydedilmeme gibi durumların önüne geçiyor.
     */
    //@Transactional
    public GuestRegisterResponseDto guestRegister(GuestRegisterRequestDto dto) {

        if (repository.existsByEmail(dto.getEmail())){
            throw new AuthServiceException(ErrorType.EMAIL_ALREADY_EXISTS);
        }
        if (repository.existsByPhoneNumber(dto.getPhoneNumber())){
            throw new AuthServiceException(ErrorType.PHONE_NUMBER_ALREADY_EXISTS);
        }

        Auth auth = IAuthMapper.INSTANCE.guestRegisterRequestDtoToAuth(dto);
        auth.setRole(ERole.GUEST);
        save(auth);

        GuestRegisterModel guestRegisterModel = GuestRegisterModel.builder()
                .authId(auth.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phoneNumber(dto.getPhoneNumber())
                .gender(dto.getGender())
                .role(auth.getRole())
                .build();
        guestRegisterProducer.convertAndSend(guestRegisterModel);

        return IAuthMapper.INSTANCE.authToGuestRegisterResponseDto(auth);
    }

    //@Transactional
    public CompanyRegisterResponseDto companyRegister(CompanyRegisterRequestDto dto) {

        if (repository.existsByEmail(dto.getEmail())){
            throw new AuthServiceException(ErrorType.EMAIL_ALREADY_EXISTS);
        }
        if (repository.existsByPhoneNumber(dto.getPhoneNumber())){
            throw new AuthServiceException(ErrorType.PHONE_NUMBER_ALREADY_EXISTS);
        }
        if (repository.existsByIdentityNumber(dto.getIdentityNumber())){
            throw new AuthServiceException(ErrorType.IDENTITY_NUMBER_ALREADY_EXISTS);
        }

        Auth auth = IAuthMapper.INSTANCE.companyRegisterRequestDtoToAuth(dto);
        auth.setRole(ERole.MANAGER);
        save(auth);

        CompanyRegisterModel companyRegisterModel = CompanyRegisterModel.builder()
                .authId(auth.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phoneNumber(dto.getPhoneNumber())
                .identityNumber(dto.getIdentityNumber())
                .address(dto.getAddress())
                .dateOfBirth(dto.getDateOfBirth())
                .companyName(dto.getCompanyName())
                .gender(dto.getGender())
                .role(auth.getRole())
                .build();
        companyRegisterProducer.convertAndSend(companyRegisterModel);

        return IAuthMapper.INSTANCE.authToCompanyRegisterResponseDto(auth);
    }


    public AuthLoginResponseDto login(AuthLoginRequestDto dto) {

        Optional<Auth> optionalAuth = repository.findOptionalByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if (optionalAuth.isEmpty()){
            throw new AuthServiceException(ErrorType.EMAIL_OR_PASSWORD_NOT_EXISTS);
        }
        if (optionalAuth.get().getStatus() != EStatus.ACTIVE) {
            throw new AuthServiceException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }

        Optional<String> optionalToken = jwtTokenManager.createToken(optionalAuth.get().getId(), optionalAuth.get().getRole(), CodeGenerator.generateCode());
        if (optionalToken.isEmpty()){
            throw new AuthServiceException(ErrorType.TOKEN_NOT_CREATED);
        }

        return AuthLoginResponseDto.builder()
                .id(optionalAuth.get().getId())
                .token(optionalToken.get())
                .role(optionalAuth.get().getRole())
                .build();
    }

    public String forgotPassword(AuthForgotPasswordRequestDto dto) {

        Optional<Auth> optionalAuth = repository.findOptionalByEmail(dto.getEmail());
        if (optionalAuth.isEmpty()) {
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        }
        Auth auth = optionalAuth.get();

        String randomPassword = PasswordGenerator.generatePassword();
        auth.setPassword(randomPassword);
        save(auth);

        AuthForgotPasswordModel authForgotPasswordModel = AuthForgotPasswordModel.builder()
                .authId(auth.getId())
                .password(auth.getPassword())
                .build();

        if(auth.getRole() == ERole.GUEST) {
            guestForgotPasswordProducer.convertAndSend(authForgotPasswordModel);
        } else if(auth.getRole() == ERole.EMPLOYEE) {
            employeeForgotPasswordProducer.convertAndSend(authForgotPasswordModel);
        } else if(auth.getRole() == ERole.MANAGER) {
            managerForgotPasswordProducer.convertAndSend(authForgotPasswordModel); //MANAGER SERVİCE İ YAZINCA AuthForgotPasswordModel EKLENECEK.
        } else {
            throw new AuthServiceException(ErrorType.INVALID_ROLE);
        }

        return "Password reset successful. New password is: " + randomPassword;
    }

    public List<FindAllResponseDto> findAllUsers(String token) {

        Optional<Long> optionalIdFromToken;
        try {
            optionalIdFromToken = jwtTokenManager.decodeToken(token);
        } catch (Exception e) {
            throw new AuthServiceException(ErrorType.INVALID_TOKEN_FORMAT);
        }

        if (!repository.existsById(optionalIdFromToken.get())){
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        }

        return findAll().stream().map(IAuthMapper.INSTANCE::authToFindAllResponseDto).collect(Collectors.toList());
    }

    public FindByIdResponseDto findUserById(Long id) {

        Optional<Auth> optionalAuth = findById(id);
        if (optionalAuth.isEmpty()) {
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        }

        return IAuthMapper.INSTANCE.authToFindByIdResponseDto(optionalAuth.get());
    }

    public String softDelete(Long id) {

        Optional<Auth> optionalAuth = findById(id);
        if (optionalAuth.isEmpty()) {
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        }

        if (optionalAuth.get().getStatus().equals(EStatus.DELETED)) {
            throw new AuthServiceException(ErrorType.USER_ALREADY_DELETED);
        }

        optionalAuth.get().setStatus(EStatus.DELETED);
        save(optionalAuth.get());

        return optionalAuth.get().getName() + optionalAuth.get().getSurname() + " user named has been deleted";
    }

    public void softUpdate(AuthUpdateModel authUpdateModel) {

        Optional<Auth> optionalAuth = findById(authUpdateModel.getAuthId());
        if (optionalAuth.isEmpty()) {
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        }

        if (optionalAuth.get().getStatus().equals(EStatus.DELETED)) {
            throw new AuthServiceException(ErrorType.USER_ALREADY_DELETED);
        }

        if (repository.existsByEmail(authUpdateModel.getEmail()) || repository.existsByPhoneNumber(authUpdateModel.getPhoneNumber())) {
            throw new AuthServiceException(ErrorType.EMAIL_OR_PHONE_ALREADY_EXISTS);
        }

        Auth updatedAuth = IAuthMapper.INSTANCE.authUpdateModelToAuth(authUpdateModel);
        update(updatedAuth);
    }


    public void createEmployee(EmployeeCreateModel employeeCreateModel) {

        if (repository.existsByEmail(employeeCreateModel.getEmail()) || repository.existsByPhoneNumber(employeeCreateModel.getPhoneNumber()) || repository.existsByIdentityNumber(employeeCreateModel.getIdentityNumber())) {
            throw new AuthServiceException(ErrorType.PARAMETER_ALREADY_EXISTS);
        }

        Auth auth = IAuthMapper.INSTANCE.employeeCreateModelToAuth(employeeCreateModel);
        save(auth);
    }

}

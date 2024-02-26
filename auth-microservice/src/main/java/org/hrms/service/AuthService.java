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
    private final EmployeeCreateSetAuthIdProducer employeeCreateSetAuthIdProducer;
    private final MailSenderProducer mailSenderProducer;
    private final ManagerActivateStatusProducer managerActivateStatusProducer;
    private final GuestActivateStatusProducer guestActivateStatusProducer;
    private final MailForgotPasswordProducer mailForgotPasswordProducer;
    private final AdminSaveSetAuthIdProducer adminSaveSetAuthIdProducer;

    public AuthService(IAuthRepository repository, JwtTokenManager jwtTokenManager, GuestRegisterProducer guestRegisterProducer, CompanyRegisterProducer companyRegisterProducer, GuestForgotPasswordProducer guestForgotPasswordProducer, EmployeeForgotPasswordProducer employeeForgotPasswordProducer, ManagerForgotPasswordProducer managerForgotPasswordProducer, EmployeeCreateSetAuthIdProducer employeeCreateSetAuthIdProducer, MailSenderProducer mailSenderProducer, ManagerActivateStatusProducer managerActivateStatusProducer, GuestActivateStatusProducer guestActivateStatusProducer, MailForgotPasswordProducer mailForgotPasswordProducer, AdminSaveSetAuthIdProducer adminSaveSetAuthIdProducer) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
        this.guestRegisterProducer = guestRegisterProducer;
        this.companyRegisterProducer = companyRegisterProducer;
        this.guestForgotPasswordProducer = guestForgotPasswordProducer;
        this.employeeForgotPasswordProducer = employeeForgotPasswordProducer;
        this.managerForgotPasswordProducer = managerForgotPasswordProducer;
        this.employeeCreateSetAuthIdProducer = employeeCreateSetAuthIdProducer;
        this.mailSenderProducer = mailSenderProducer;
        this.managerActivateStatusProducer = managerActivateStatusProducer;
        this.guestActivateStatusProducer = guestActivateStatusProducer;
        this.mailForgotPasswordProducer = mailForgotPasswordProducer;
        this.adminSaveSetAuthIdProducer = adminSaveSetAuthIdProducer;
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

        activateAccount(auth);

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

        activateAccount(auth);

        return IAuthMapper.INSTANCE.authToCompanyRegisterResponseDto(auth);
    }

    public void activateAccount(Auth auth){

        Optional<String> optionalToken = jwtTokenManager.createToken(auth.getId(), auth.getRole());
        if (optionalToken.isEmpty()){
            throw new AuthServiceException(ErrorType.TOKEN_NOT_CREATED);
        }

        String activationLink = "http://localhost:9090/api/v1/auth/activate?token=" + optionalToken.get();
        try {
            mailSenderProducer.convertAndSend(MailSenderModel.builder()
                    .email(auth.getEmail())
                    .activationLink(activationLink)
                    .build());
            System.out.println("Mail sent successfully.");
        } catch (Exception e) {
            System.err.println("Error sending mail: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println(activationLink);
    }

    public String activateCode(String token) {

        Optional<Long> optionalIdFromToken = jwtTokenManager.decodeToken(token);
        if (optionalIdFromToken.isEmpty()) {
            throw new AuthServiceException(ErrorType.INVALID_TOKEN_FORMAT);
        }

        Optional<Auth> optionalAuth = findById(optionalIdFromToken.get());
        if (optionalAuth.isEmpty()) {
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        }

        return statusControl(optionalAuth.get());
    }

    private String statusControl(Auth auth) {
        switch (auth.getStatus()) {
            case ACTIVE -> {
                return "Your account has already been activated. Your role: " + auth.getRole();
            }
            case PENDING -> {
                auth.setStatus(EStatus.ACTIVE);
                update(auth);

                ActivateStatusModel activateStatusModel = ActivateStatusModel.builder()
                        .authId(auth.getId())
                        .build();

                if(auth.getRole() == ERole.GUEST) {
                    guestActivateStatusProducer.convertAndSend(activateStatusModel);
                } else if(auth.getRole() == ERole.MANAGER) {
                    managerActivateStatusProducer.convertAndSend(activateStatusModel);
                } else {
                    throw new AuthServiceException(ErrorType.INVALID_ROLE);
                }

                return "Your account has been successfully activated. Your role: " + auth.getRole();
            }
            case BANNED -> {
                throw new AuthServiceException(ErrorType.ACCOUNT_BANNED);
            }
            case DELETED -> {
                throw new AuthServiceException(ErrorType.USER_ALREADY_DELETED);
            }
            default -> {
                throw new AuthServiceException(ErrorType.INTERNAL_SERVER_ERROR);
            }
        }
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
        update(auth);

        AuthForgotPasswordModel authForgotPasswordModel = AuthForgotPasswordModel.builder()
                .authId(auth.getId())
                .password(auth.getPassword())
                .build();

        if(auth.getRole() == ERole.GUEST) {
            guestForgotPasswordProducer.convertAndSend(authForgotPasswordModel);
        } else if(auth.getRole() == ERole.EMPLOYEE) {
            employeeForgotPasswordProducer.convertAndSend(authForgotPasswordModel);
        } else if(auth.getRole() == ERole.MANAGER) {
            managerForgotPasswordProducer.convertAndSend(authForgotPasswordModel);
        } else {
            throw new AuthServiceException(ErrorType.INVALID_ROLE);
        }

        MailForgotPasswordModel mailForgotPasswordModel = MailForgotPasswordModel.builder()
                .email(auth.getEmail())
                .password(auth.getPassword())
                .build();
        mailForgotPasswordProducer.convertAndSend(mailForgotPasswordModel);

        return "Password reset successful. Your new password has been sent to your e-mail address.";
    }

    public List<FindAllResponseDto> findAllUsers(String token) {

        Optional<Long> optionalIdFromToken = jwtTokenManager.decodeToken(token);
        if (optionalIdFromToken.isEmpty()) {
            throw new AuthServiceException(ErrorType.INVALID_TOKEN_FORMAT);
        }

        if (!repository.existsById(optionalIdFromToken.get())){
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        }

        return findAll().stream()
                .filter(item -> item.getStatus() == EStatus.ACTIVE)
                .map(IAuthMapper.INSTANCE::authToFindAllResponseDto)
                .collect(Collectors.toList());
    }

    public FindByIdResponseDto findUserById(Long id) {

        Optional<Auth> optionalAuth = findById(id);
        if (optionalAuth.isEmpty()) {
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        }

        if(optionalAuth.get().getStatus() == EStatus.ACTIVE) {
            return IAuthMapper.INSTANCE.authToFindByIdResponseDto(optionalAuth.get());
        } else {
            throw new AuthServiceException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
    }

    public void softDelete(Long id) {

        Optional<Auth> optionalAuth = findById(id);
        if (optionalAuth.isEmpty()) {
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        }

        if (optionalAuth.get().getStatus().equals(EStatus.DELETED)) {
            throw new AuthServiceException(ErrorType.USER_ALREADY_DELETED);
        }

        optionalAuth.get().setStatus(EStatus.DELETED);
        update(optionalAuth.get());

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

        if (repository.existsByEmail(employeeCreateModel.getEmail()) || repository.existsByPhoneNumber(employeeCreateModel.getPhoneNumber())) {
            throw new AuthServiceException(ErrorType.EMAIL_OR_PHONE_ALREADY_EXISTS);
        }

        Auth auth = IAuthMapper.INSTANCE.employeeCreateModelToAuth(employeeCreateModel);
        save(auth);

        EmployeeCreateSetAuthIdModel employeeCreateSetAuthIdModel = EmployeeCreateSetAuthIdModel.builder()
                .authId(auth.getId())
                .email(auth.getEmail())
                .build();
        employeeCreateSetAuthIdProducer.convertAndSend(employeeCreateSetAuthIdModel);
    }

    public void saveAdmin(AdminSaveModel adminSaveModel) {

        if (repository.existsByEmail(adminSaveModel.getEmail()) || repository.existsByPhoneNumber(adminSaveModel.getPhoneNumber())) {
            throw new AuthServiceException(ErrorType.EMAIL_OR_PHONE_ALREADY_EXISTS);
        }

        Auth auth = IAuthMapper.INSTANCE.adminSaveModelToAuth(adminSaveModel);
        save(auth);

        AdminSaveSetAuthIdModel adminSaveSetAuthIdModel = AdminSaveSetAuthIdModel.builder()
                .authId(auth.getId())
                .email(auth.getEmail())
                .build();
        adminSaveSetAuthIdProducer.convertAndSend(adminSaveSetAuthIdModel);
    }



}

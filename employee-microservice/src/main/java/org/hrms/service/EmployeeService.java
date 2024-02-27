package org.hrms.service;

import org.hrms.dto.request.EmployeeCreateRequestDto;
import org.hrms.dto.request.EmployeeUpdateRequestDto;
import org.hrms.dto.response.EmployeeCreateResponseDto;
import org.hrms.dto.response.FindAllEmployeesResponseDto;
import org.hrms.dto.response.FindEmployeeByIdResponseDto;
import org.hrms.exception.EmployeeServiceException;
import org.hrms.exception.ErrorType;
import org.hrms.mapper.IEmployeeMapper;
import org.hrms.rabbitmq.model.*;
import org.hrms.rabbitmq.producer.AuthDeleteProducer;
import org.hrms.rabbitmq.producer.AuthUpdateProducer;
import org.hrms.rabbitmq.producer.EmployeeCreateProducer;
import org.hrms.rabbitmq.producer.MailCreateEmployeeProducer;
import org.hrms.repository.IEmployeeRepository;
import org.hrms.repository.entity.Employee;
import org.hrms.repository.enums.ERole;
import org.hrms.repository.enums.EStatus;
import org.hrms.utility.PasswordGenerator;
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
 * IEmployeeRepository interface'imizin JpaRepository'den miras alması gibi biz de kendi ServiceManager sınıfımızı yazdık ve EmployeeService sınıfımızın oradan miras almasını sağladık.
 * Bu şekilde JpaRepository içindeki hazır metotlara IEmployeeRepository üzerinden ulaşabildiğimiz gibi biz de ServiceManager içindeki yazdığımız metotlara ulaşabileceğiz.
 * ServiceManager parametreleri de ilgili entity sınıfını ve bu sınıfın birincil anahtar türünü içerir.
 */
@Service
public class EmployeeService extends ServiceManager<Employee, Long> {

    private final IEmployeeRepository repository;
    private final EmployeeCreateProducer employeeCreateProducer;
    private final AuthUpdateProducer authUpdateProducer;
    private final AuthDeleteProducer authDeleteProducer;
    private final MailCreateEmployeeProducer mailCreateEmployeeProducer;

    public EmployeeService(JpaRepository<Employee, Long> jpaRepository, IEmployeeRepository repository, EmployeeCreateProducer employeeCreateProducer, AuthUpdateProducer authUpdateProducer, AuthDeleteProducer authDeleteProducer, MailCreateEmployeeProducer mailCreateEmployeeProducer) {
        super(jpaRepository);
        this.repository = repository;
        this.employeeCreateProducer = employeeCreateProducer;
        this.authUpdateProducer = authUpdateProducer;
        this.authDeleteProducer = authDeleteProducer;
        this.mailCreateEmployeeProducer = mailCreateEmployeeProducer;
    }

    public EmployeeCreateResponseDto createEmployee(EmployeeCreateRequestDto dto) {

        if(repository.existsByEmail(dto.getEmail()) || repository.existsByPhoneNumber(dto.getPhoneNumber()) || repository.existsByIdentityNumber(dto.getIdentityNumber())) {
            throw new EmployeeServiceException(ErrorType.PARAMETER_ALREADY_EXISTS);
        }

        Employee employee = IEmployeeMapper.INSTANCE.employeeCreateRequestDtoToEmployee(dto);
        employee.setRole(ERole.EMPLOYEE);

        String randomPassword = PasswordGenerator.generatePassword();
        employee.setPassword(randomPassword);

        EmployeeCreateModel employeeCreateModel = IEmployeeMapper.INSTANCE.employeeToEmployeeCreateModel(employee);
        employeeCreateProducer.convertAndSend(employeeCreateModel);

        //auth-microservice tarafına employee'yi gönderdiğimiz gibi company-service tarafına da göndermeliyiz. UNUTMA

        save(employee);

        MailCreateEmployeeModel mailCreateEmployeeModel = MailCreateEmployeeModel.builder()
                .personalEmail(dto.getPersonalEmail())
                .email(employee.getEmail())
                .password(employee.getPassword())
                .build();
        mailCreateEmployeeProducer.convertAndSend(mailCreateEmployeeModel);

        return IEmployeeMapper.INSTANCE.employeeToEmployeeCreateResponseDto(employee);
    }

    public void setAuthId(EmployeeCreateSetAuthIdModel model){

        Optional<Employee> optionalEmployee = repository.findOptionalByEmail(model.getEmail());
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeServiceException(ErrorType.USER_NOT_FOUND);
        }

        //Burada model ile gelen authId önceden kaydedilmiş mi diye bakmaya gerek var mı

        optionalEmployee.get().setAuthId(model.getAuthId());
        update(optionalEmployee.get());
    }

    public String softUpdate(EmployeeUpdateRequestDto dto) {

        Optional<Employee> optionalEmployee = findById(dto.getId());
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeServiceException(ErrorType.USER_NOT_FOUND);
        }
        Employee updatedEmployee = optionalEmployee.get();

        if (updatedEmployee.getStatus().equals(EStatus.DELETED)) {
            throw new EmployeeServiceException(ErrorType.USER_ALREADY_DELETED);
        }

        if (repository.existsByEmail(dto.getEmail()) || repository.existsByPhoneNumber(dto.getPhoneNumber()) || repository.existsByIdentityNumber(dto.getIdentityNumber())) {
            throw new EmployeeServiceException(ErrorType.PARAMETER_ALREADY_EXISTS);
        }

        if (dto.getName() != null) {
            updatedEmployee.setName(dto.getName());
        }
        if (dto.getSurname() != null) {
            updatedEmployee.setSurname(dto.getSurname());
        }
        if (dto.getPhoneNumber() != null) {
            updatedEmployee.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getIdentityNumber() != null) {
            updatedEmployee.setIdentityNumber(dto.getIdentityNumber());
        }
        if (dto.getEmail() != null) {
            updatedEmployee.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            updatedEmployee.setPassword(dto.getPassword());
        }
        if (dto.getAddress() != null) {
            updatedEmployee.setAddress(dto.getAddress());
        }
        if (dto.getCompanyName() != null) {
            updatedEmployee.setCompanyName(dto.getCompanyName());
        }
        if (dto.getTitle() != null) {
            updatedEmployee.setTitle(dto.getTitle());
        }
        if (dto.getSalary() != null) {
            updatedEmployee.setSalary(dto.getSalary());
        }
        if (dto.getPhoto() != null) {
            updatedEmployee.setPhoto(dto.getPhoto());
        }
        if (dto.getGender() != null) {
            updatedEmployee.setGender(dto.getGender());
        }
        if (dto.getDateOfBirth() != null) {
            updatedEmployee.setDateOfBirth(dto.getDateOfBirth());
        }

        update(updatedEmployee);

        AuthUpdateModel authUpdateModel = IEmployeeMapper.INSTANCE.employeeToAuthUpdateModel(updatedEmployee);
        authUpdateProducer.convertAndSend(authUpdateModel);

        return "Successfully updated.";
    }


    public String softDelete(Long id) {

        Optional<Employee> optionalEmployee = findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeServiceException(ErrorType.USER_NOT_FOUND);
        }
        Employee deletedEmployee = optionalEmployee.get();

        if (deletedEmployee.getStatus().equals(EStatus.DELETED)) {
            throw new EmployeeServiceException(ErrorType.USER_ALREADY_DELETED);
        }

        deletedEmployee.setStatus(EStatus.DELETED);
        update(deletedEmployee);

        authDeleteProducer.convertAndSend(AuthDeleteModel.builder()
                .authId(deletedEmployee.getAuthId())
                .status(deletedEmployee.getStatus())
                .build());

        return deletedEmployee.getName() + " " + deletedEmployee.getSurname() + " user named has been deleted";
    }

    public List<FindAllEmployeesResponseDto> findAllEmployees() {
        return findAll().stream()
                .filter(item -> item.getStatus() == EStatus.ACTIVE)
                .map(IEmployeeMapper.INSTANCE::employeeToFindAllEmployeesResponseDto)
                .collect(Collectors.toList());
    }

    public FindEmployeeByIdResponseDto findEmployeeById(Long id) {

        Optional<Employee> optionalEmployee = findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeServiceException(ErrorType.USER_NOT_FOUND);
        }

        if(optionalEmployee.get().getStatus() == EStatus.ACTIVE) {
            return IEmployeeMapper.INSTANCE.employeeToFindEmployeeByIdResponseDto(optionalEmployee.get());
        } else {
            throw new EmployeeServiceException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
    }

    public FindEmployeeByIdResponseDto findEmployeeByAuthId(Long authId) {

        Optional<Employee> optionalEmployee = repository.findOptionalByAuthId(authId);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeServiceException(ErrorType.USER_NOT_FOUND);
        }

        if(optionalEmployee.get().getStatus() == EStatus.ACTIVE) {
            return IEmployeeMapper.INSTANCE.employeeToFindEmployeeByIdResponseDto(optionalEmployee.get());
        } else {
            throw new EmployeeServiceException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
    }

    public FindEmployeeByIdResponseDto findEmployeeByCompanyId(String companyId) {

        Optional<Employee> optionalEmployee = repository.findOptionalByCompanyId(companyId);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeServiceException(ErrorType.USER_NOT_FOUND);
        }

        if(optionalEmployee.get().getStatus() == EStatus.ACTIVE) {
            return IEmployeeMapper.INSTANCE.employeeToFindEmployeeByIdResponseDto(optionalEmployee.get());
        } else {
            throw new EmployeeServiceException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
    }

    public List<FindAllEmployeesResponseDto> findEmployeesByCompanyName(String companyName) {

        if (!repository.existsByCompanyName(companyName)){
            throw new EmployeeServiceException(ErrorType.COMPANY_NAME_NOT_FOUND);
        }
        List<Employee> employeesList = repository.findEmployeesByCompanyName(companyName);

        return employeesList.stream()
                .filter(item -> item.getStatus() == EStatus.ACTIVE)
                .map(IEmployeeMapper.INSTANCE::employeeToFindAllEmployeesResponseDto)
                .collect(Collectors.toList());
    }

    public void updatePassword(AuthForgotPasswordModel model) {

        Optional<Employee> optionalEmployee = repository.findOptionalByAuthId(model.getAuthId());
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeServiceException(ErrorType.USER_NOT_FOUND);
        }

        optionalEmployee.get().setPassword(model.getPassword());
        update(optionalEmployee.get());
    }
}

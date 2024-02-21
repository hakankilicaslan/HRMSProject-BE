package org.hrms.service;

import org.hrms.dto.request.EmployeeCreateRequestDto;
import org.hrms.dto.request.EmployeeUpdateRequestDto;
import org.hrms.dto.response.EmployeeCreateResponseDto;
import org.hrms.dto.response.FindAllEmployeesResponseDto;
import org.hrms.dto.response.FindEmployeeByIdResponseDto;
import org.hrms.exception.EmployeeServiceException;
import org.hrms.exception.ErrorType;
import org.hrms.mapper.IEmployeeMapper;
import org.hrms.rabbitmq.model.AuthDeleteModel;
import org.hrms.rabbitmq.model.AuthUpdateModel;
import org.hrms.rabbitmq.model.EmployeeCreateModel;
import org.hrms.rabbitmq.producer.AuthDeleteProducer;
import org.hrms.rabbitmq.producer.AuthUpdateProducer;
import org.hrms.rabbitmq.producer.EmployeeCreateProducer;
import org.hrms.repository.IEmployeeRepository;
import org.hrms.repository.entity.Employee;
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

    public EmployeeService(JpaRepository<Employee, Long> jpaRepository, IEmployeeRepository repository, EmployeeCreateProducer employeeCreateProducer, AuthUpdateProducer authUpdateProducer, AuthDeleteProducer authDeleteProducer) {
        super(jpaRepository);
        this.repository = repository;
        this.employeeCreateProducer = employeeCreateProducer;
        this.authUpdateProducer = authUpdateProducer;
        this.authDeleteProducer = authDeleteProducer;
    }

    public EmployeeCreateResponseDto createEmployee(EmployeeCreateRequestDto dto) {

        if(repository.existsByEmail(dto.getEmail()) || repository.existsByPhoneNumber(dto.getPhoneNumber()) || repository.existsByIdentityNumber(dto.getIdentityNumber())) {
            throw new EmployeeServiceException(ErrorType.PARAMETER_ALREADY_EXISTS);
        }

        Employee employee = IEmployeeMapper.INSTANCE.employeeCreateRequestDtoToEmployee(dto);
        save(employee);

        EmployeeCreateModel employeeCreateModel = IEmployeeMapper.INSTANCE.employeeToEmployeeCreateModel(employee);
        employeeCreateProducer.convertAndSend(employeeCreateModel);

        //Company service tarafına da employee kaydedilsin.

        return IEmployeeMapper.INSTANCE.employeeToEmployeeCreateResponseDto(employee);
    }

    public String softUpdate(EmployeeUpdateRequestDto dto) {

        Optional<Employee> optionalEmployee = findById(dto.getId());
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeServiceException(ErrorType.USER_NOT_FOUND);
        }
        Employee employee = optionalEmployee.get();

        if (employee.getStatus().equals(EStatus.DELETED)) {
            throw new EmployeeServiceException(ErrorType.USER_ALREADY_DELETED);
        }

        if (repository.existsByEmail(employee.getEmail()) || repository.existsByPhoneNumber(employee.getPhoneNumber()) || repository.existsByIdentityNumber(employee.getIdentityNumber()) || repository.existsByAuthId(employee.getAuthId()) || repository.existsByCompanyId(employee.getCompanyId())) {
            throw new EmployeeServiceException(ErrorType.PARAMETER_ALREADY_EXISTS);
        }

        Employee updatedEmployee = IEmployeeMapper.INSTANCE.employeeUpdateRequestDtoToEmployee(dto);
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
        save(deletedEmployee);

        authDeleteProducer.convertAndSend(AuthDeleteModel.builder()
                .authId(deletedEmployee.getAuthId())
                .status(deletedEmployee.getStatus())
                .build());

        return deletedEmployee.getName() + deletedEmployee.getSurname() + " user named has been deleted";
    }

    public List<FindAllEmployeesResponseDto> findAllEmployees() {
        return findAll().stream().map(IEmployeeMapper.INSTANCE::employeeToFindAllEmployeesResponseDto).collect(Collectors.toList());
    }

    public FindEmployeeByIdResponseDto findEmployeeById(Long id) {

        Optional<Employee> optionalEmployee = findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeServiceException(ErrorType.USER_NOT_FOUND);
        }

        return IEmployeeMapper.INSTANCE.employeeToFindEmployeeByIdResponseDto(optionalEmployee.get());
    }

    public FindEmployeeByIdResponseDto findEmployeeByAuthId(Long authId) {

        Optional<Employee> optionalEmployee = repository.findOptionalByAuthId(authId);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeServiceException(ErrorType.USER_NOT_FOUND);
        }

        return IEmployeeMapper.INSTANCE.employeeToFindEmployeeByIdResponseDto(optionalEmployee.get());
    }

    public FindEmployeeByIdResponseDto findEmployeeByCompanyId(Long companyId) {

        Optional<Employee> optionalEmployee = repository.findOptionalByCompanyId(companyId);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeServiceException(ErrorType.USER_NOT_FOUND);
        }

        return IEmployeeMapper.INSTANCE.employeeToFindEmployeeByIdResponseDto(optionalEmployee.get());
    }

    public List<FindAllEmployeesResponseDto> findEmployeesByCompanyName(String companyName) {

        if (!repository.existsByCompanyName(companyName)){
            throw new EmployeeServiceException(ErrorType.COMPANY_NAME_NOT_FOUND);
        }
        List<Employee> employeesList = repository.findEmployeesByCompanyName(companyName);

        return employeesList.stream().map(IEmployeeMapper.INSTANCE::employeeToFindAllEmployeesResponseDto).collect(Collectors.toList());
    }

}

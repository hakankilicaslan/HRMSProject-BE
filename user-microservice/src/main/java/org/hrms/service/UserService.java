package org.hrms.service;

import org.hrms.dto.request.CreateEmployeeRequestDto;
import org.hrms.dto.request.EmployeeInfoUpdateDto;
import org.hrms.dto.request.GuestInfoUpdateDto;
import org.hrms.dto.response.EmployeeResponseDto;
import org.hrms.mapper.IUserMapper;
import org.hrms.repository.IUserRepository;
import org.hrms.repository.entity.User;
import org.hrms.repository.enums.ERole;
import org.hrms.utility.ServiceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends ServiceManager<User,String> {

    private final IUserRepository userRepository;
    public UserService(MongoRepository<User, String> repository, IUserRepository userRepository) {
        super(repository);
        this.userRepository = userRepository;
    }

    public String createEmployee(CreateEmployeeRequestDto createEmployeeRequestDto) {
        if (userRepository.findOptionalByUsername(createEmployeeRequestDto.getUsername()).isPresent()){
            return "Zaten bu mail adresi ile kayıt yapılmış";
        }
        User user= IUserMapper.INSTANCE.createEmployeeRequestDtoToUser(createEmployeeRequestDto);
        user.setUsername(createEmployeeRequestDto.getUsername());
        user.setRole(createEmployeeRequestDto.getRole());
        user.setAddress(createEmployeeRequestDto.getAddress());
        user.setBirthday(createEmployeeRequestDto.getBirthday());
        user.setInfo(createEmployeeRequestDto.getInfo());
        user.setPassword(createEmployeeRequestDto.getPassword());
        user.setEmail(createEmployeeRequestDto.getEmail());
        user.setName(createEmployeeRequestDto.getName());
        user.setSurname(createEmployeeRequestDto.getSurname());

        save(user);
        return "Employee basarıyla kaydedildi";
    }

    public Optional<User> findEmployeeByAuthId(Long authId) {
        Optional<User> employee = userRepository.findOptionalByAuthId(authId);

        if (employee.isPresent()) {
            return employee;
        } else {
            System.out.println("Employee bulunamadı");
            return Optional.empty();
        }
    }

    public Boolean updateEmployeeInfo(EmployeeInfoUpdateDto dto, Long authId) {
        Optional<User> employee=userRepository.findOptionalByAuthId(authId);
        if (employee.isPresent()){
            userRepository.save(IUserMapper.INSTANCE.fromEmployeeInfoUpdateRequestDtoToUser(dto, employee.get()));
            return true;
        }
        System.out.println("kullanıcı bulunamadı");
        return false;
    }

    public Boolean updateGuestInfo(GuestInfoUpdateDto dto, Long authId) {
        Optional<User> employee=userRepository.findOptionalByAuthId(authId);
        if (employee.isPresent()){
            userRepository.save(IUserMapper.INSTANCE.fromGuestInfoUpdateRequestDtoToUser(dto, employee.get()));
            return true;
        }
        return false;
    }

    public Integer getNumberOfEmployees() {
        List<User> employeeList = userRepository.findByRole(ERole.EMPLOYEE);
        List<User> companyManagerList = userRepository.findByRole(ERole.COMPANY_MANAGER);
        return (employeeList.size() + companyManagerList.size());
    }

//    public List<EmployeeResponseDto> getAllEmployeesInCompany() throws Exception {
//        List<User> userList = userRepository.findByRole(ERole.EMPLOYEE);
//
//        if (!userList.isEmpty()) {
//            List<EmployeeResponseDto> employeeResponseDtoList = new ArrayList<>();
//            for (User user : userList) {
//                EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
//                employeeResponseDto.setSurname(user.getSurname());
//                employeeResponseDto.setName(user.getName());
//                employeeResponseDtoList.add(employeeResponseDto);
//            }
//            return employeeResponseDtoList;
//        }
//        throw new Exception("Liste boş");
//    }

    public List<EmployeeResponseDto> getAllEmployeesInCompany() throws Exception {
        List<User> userList = userRepository.findByRole(ERole.EMPLOYEE);

        if (userList.isEmpty()) {
            throw new Exception("Liste boş");
        }

        return userList.stream()
                .map(user -> EmployeeResponseDto.builder()
                        .name(user.getName())
                        .surname(user.getSurname())
                        .build())
                .collect(Collectors.toList());
    }
}

package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.CreateEmployeeRequestDto;
import org.hrms.dto.request.EmployeeInfoUpdateDto;
import org.hrms.dto.request.GuestInfoUpdateDto;
import org.hrms.dto.response.EmployeeResponseDto;
import org.hrms.repository.entity.User;
import org.hrms.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.hrms.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserController {
    private final UserService userService;

    @PostMapping(CREATE_EMPLOYEE)
    public ResponseEntity<String> addEmployee(@RequestBody CreateEmployeeRequestDto createEmployeeRequestDto){
        return ResponseEntity.ok(userService.createEmployee(createEmployeeRequestDto));
    }

    @GetMapping(FIND_BY_ID+"/{authId}")
    public ResponseEntity<Optional<User>> findEmployeeByAuthId(@PathVariable Long authId){
        return ResponseEntity.ok(userService.findEmployeeByAuthId(authId));
    }

    @PutMapping(UPDATE+"/{authId}")
    public ResponseEntity<Boolean> updateEmployeeInfo(@RequestBody EmployeeInfoUpdateDto dto, @PathVariable Long authId){
        return ResponseEntity.ok(userService.updateEmployeeInfo(dto,authId));
    }

    @PutMapping(UPDATE_GUEST+"/{authId}")
    public ResponseEntity<Boolean> updateGuestInfo(@RequestBody GuestInfoUpdateDto dto, @PathVariable Long authId){
        return ResponseEntity.ok(userService.updateGuestInfo(dto,authId));
    }

    @GetMapping(GET_NUMBER_OF_EMPLOYEES_AND_MANAGERS)
    public ResponseEntity<Integer> getNumberOfEmployees(){
        return ResponseEntity.ok(userService.getNumberOfEmployees());
    }

    @GetMapping(GET_ALL_EMPLOYEES_IN_COMPANY)
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployeesInCompany() throws Exception {
        return ResponseEntity.ok(userService.getAllEmployeesInCompany());
    }

}

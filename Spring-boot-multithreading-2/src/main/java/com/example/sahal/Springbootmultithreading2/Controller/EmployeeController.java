package com.example.sahal.Springbootmultithreading2.Controller;

import com.example.sahal.Springbootmultithreading2.model.Employee;
import com.example.sahal.Springbootmultithreading2.service.EmployeeService;
import com.example.sahal.Springbootmultithreading2.valueObject.ResponseValueObject;
import com.example.sahal.Springbootmultithreading2.dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import static com.example.sahal.Springbootmultithreading2.constant.Constant.*;

@RestController
@Slf4j
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/employees", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity<String> saveEmployeesThroughFile(
            @Valid @RequestParam(value = "files")MultipartFile[] files) throws Exception {
        String result = null;
            for (MultipartFile file : files) {
                result = employeeService.saveEmployeesThroughFile(file).get();
            }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDto> saveEmployee(
            @Valid @RequestBody EmployeeDto employeeDto) throws Exception {
            EmployeeDto createdEmployee = employeeService.saveEmployee(employeeDto).get();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdEmployee);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @Positive(message = ID_SHOULD_BE_POSITIVE_ERROR_MESSAGE)
            @PathVariable long id, @Valid @RequestBody EmployeeDto employeeDto) throws Exception {
            EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDto).get();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(updatedEmployee);
    }

    @GetMapping(value = "/employees")
    public ResponseEntity<List<EmployeeDto>> findAllEmployees() throws Exception {
        List<EmployeeDto> employeeDtoList;
        employeeDtoList = employeeService.findAllEmployees().get();
        return ResponseEntity.ok(employeeDtoList);
    }

    @GetMapping(value = "/employee/{id}")
    public ResponseEntity<ResponseValueObject> findEmployeeById(
            @Positive(message = ID_SHOULD_BE_POSITIVE_ERROR_MESSAGE)
            @PathVariable long id) throws Exception {
        ResponseValueObject vo;
        vo = employeeService.findEmployeeById(id);
        return ResponseEntity.ok(vo);
//        responseEntity = student != null ? ok(student)
//                : status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/employees/city/{name}")
    public ResponseEntity<ResponseValueObject> findEmployeesByCityName(
            @PathVariable String name) throws Exception {
        ResponseValueObject vo;
        vo = employeeService.findEmployeesByCityName(name).get();
        return ResponseEntity.ok(vo);
    }

    @GetMapping("/employees/company/{name}")
    public ResponseEntity<ResponseValueObject> findEmployeesByCompanyName(
            @PathVariable String name) throws Exception {
        ResponseValueObject vo;
        vo = employeeService.findEmployeesByCompanyName(name).get();
        return ResponseEntity.ok(vo);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity deleteEmployee(
            @Positive(message = ID_SHOULD_BE_POSITIVE_ERROR_MESSAGE)
            @PathVariable long id) throws Exception {
            employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "employees/thread")
    public ResponseEntity<List<Employee>> findAllEmployeesByThread() throws Exception {
        List<Employee> employeeList = new ArrayList<>();
        Employee employee = new Employee();
            for (long i = 1; i <= 3000; i++) {
                employee = employeeService.findAllEmployeesByThread(i).get();
                employeeList.add(employee);
            }
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(employeeList);
    }

    @DeleteMapping(value = "delete/test")
    public ResponseEntity<String> deleteTestData(){
        String message;
        message = employeeService.deleteTestData();
        return ResponseEntity.ok(message);
    }
}

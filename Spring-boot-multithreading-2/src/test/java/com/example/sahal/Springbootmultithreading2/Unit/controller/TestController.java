package com.example.sahal.Springbootmultithreading2.Unit.controller;

import com.example.sahal.Springbootmultithreading2.Controller.EmployeeController;
import com.example.sahal.Springbootmultithreading2.dto.CityDto;
import com.example.sahal.Springbootmultithreading2.dto.CompanyDto;
import com.example.sahal.Springbootmultithreading2.dto.EmployeeDto;
import com.example.sahal.Springbootmultithreading2.dto.ErrorDto;
import com.example.sahal.Springbootmultithreading2.exception.GlobalException;
import com.example.sahal.Springbootmultithreading2.model.Employee;
import com.example.sahal.Springbootmultithreading2.service.EmployeeService;
import com.example.sahal.Springbootmultithreading2.valueObject.ResponseValueObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestController {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeDto mockEmployeeDto;
    private List<EmployeeDto> mockEmployeeDtoList;
    private CityDto mockCityDto;
    private CompanyDto mockCompanyDto;
    private ResponseValueObject mockValueObject;


    @Before
    public void createEmployeeMock() throws IOException {
        // create a dummy object

        mockEmployeeDto = new EmployeeDto(
                1L,
                "Sahal",
                "Yousuf",
                "sahal.yousuf10@gmail.com",
                "Male",
                3L,
                "Engineer",
                22345L,
                5L);
        mockCityDto = new CityDto(
                5L,
                "Karachi",
                null);
        mockCompanyDto = new CompanyDto(
                3L,
                "Nisum",
                null);

        mockEmployeeDtoList = new ArrayList<>();
        mockEmployeeDtoList.add(mockEmployeeDto);
        mockValueObject = new ResponseValueObject();
        mockValueObject.setCity(mockCityDto);
        mockValueObject.setCompany(mockCompanyDto);
        mockValueObject.setEmployeeList(mockEmployeeDtoList);
    }

    @Test
    public void testCreateEmployee() throws Exception {
        when(employeeService.saveEmployee(any(EmployeeDto.class))).thenReturn(CompletableFuture.completedFuture(mockEmployeeDto));
        ResponseEntity<EmployeeDto> responseEntity = employeeController.saveEmployee(mockEmployeeDto);
        Assert.assertEquals(responseEntity.getBody(), mockEmployeeDto);
        Assert.assertEquals(responseEntity.getStatusCode().value(), 201);
    }

    @Test
    public void testFindEmployeeByID() throws Exception {
        when(employeeService.findEmployeeById(anyLong())).thenReturn(mockValueObject);
        ResponseEntity<ResponseValueObject> vo = employeeController.findEmployeeById(anyLong());
        Assert.assertEquals(vo.getBody().getEmployeeList().get(0).getFirstName(),mockEmployeeDtoList.get(0).getFirstName());
        Assert.assertEquals(vo.getStatusCode().value(), 302);
    }

    @Test
    public void testFindAllEmployees() throws Exception {
        when(employeeService.findAllEmployees()).thenReturn(CompletableFuture.completedFuture(mockEmployeeDtoList));
        ResponseEntity<List<EmployeeDto>> responseEntity = employeeController.findAllEmployees();
        Assert.assertEquals(302, responseEntity.getStatusCode().value());
        Assert.assertEquals(responseEntity.getBody(), mockEmployeeDtoList);
    }

//    public void testFindAllEmployeesIfNotFound() throws GlobalException {
//        when(employeeService.findAllEmployees()).thenReturn(CompletableFuture.completedFuture(new ArrayList<>()));
//        aMockErrorDto = new ErrorDto()
//    }

    @Test
    public void testSaveEmployee() throws Exception {
        when(employeeService.saveEmployee(mockEmployeeDto)).thenReturn(CompletableFuture.completedFuture(mockEmployeeDto));
        ResponseEntity<EmployeeDto> responseEntity = employeeController.saveEmployee(mockEmployeeDto);
        Assert.assertEquals(201, responseEntity.getStatusCode().value());
        Assert.assertEquals(responseEntity.getBody(), mockEmployeeDto);
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        when(employeeService.updateEmployee(5L, mockEmployeeDto)).thenReturn(CompletableFuture.completedFuture(mockEmployeeDto));
        ResponseEntity<EmployeeDto> responseEntity = employeeController.updateEmployee(5L, mockEmployeeDto);
        Assert.assertEquals(201, responseEntity.getStatusCode().value());
        Assert.assertEquals(responseEntity.getBody(), mockEmployeeDto);
    }

    @Test
    public void testFindEmployeesByCityName() throws Exception {
        when(employeeService.findEmployeesByCityName(anyString())).thenReturn(CompletableFuture.completedFuture(mockValueObject));
        ResponseEntity<ResponseValueObject> responseEntity = employeeController.findEmployeesByCityName(anyString());
        Assert.assertEquals(302, responseEntity.getStatusCode().value());
        Assert.assertEquals(responseEntity.getBody().getEmployeeList().get(0).getFirstName(), mockEmployeeDtoList.get(0).getFirstName());
    }

    @Test
    public void testFindEmployeesByCompanyName() throws Exception {
        when(employeeService.findEmployeesByCompanyName(anyString())).thenReturn(CompletableFuture.completedFuture(mockValueObject));
        ResponseEntity<ResponseValueObject> responseEntity = employeeController.findEmployeesByCompanyName(anyString());
        Assert.assertEquals(302, responseEntity.getStatusCode().value());
        Assert.assertEquals(responseEntity.getBody().getEmployeeList().get(0).getFirstName(), mockEmployeeDtoList.get(0).getFirstName());
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        //when(employeeService.deleteEmployee(anyLong())).thenReturn(CompletableFuture.completedFuture("Employee deleted successfully"));
        ResponseEntity responseEntity = employeeController.deleteEmployee(anyLong());
        Assert.assertEquals(204, responseEntity.getStatusCode().value());
        //Assert.assertEquals("Employee deleted successfully", responseEntity.getBody());
    }
}

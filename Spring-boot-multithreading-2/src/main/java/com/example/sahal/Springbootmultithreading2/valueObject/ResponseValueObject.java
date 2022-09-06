package com.example.sahal.Springbootmultithreading2.valueObject;

import com.example.sahal.Springbootmultithreading2.dto.CityDto;
import com.example.sahal.Springbootmultithreading2.dto.CompanyDto;
import com.example.sahal.Springbootmultithreading2.dto.EmployeeDto;
import lombok.Data;

import java.util.List;

@Data
public class ResponseValueObject {

    private CityDto city;
    private CompanyDto company;
    private List<EmployeeDto> employeeList;
}

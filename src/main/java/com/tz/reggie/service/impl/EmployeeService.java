package com.tz.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tz.reggie.entity.Employee;
import com.tz.reggie.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends ServiceImpl<EmployeeMapper, Employee> implements com.tz.reggie.service.EmployeeService {
}

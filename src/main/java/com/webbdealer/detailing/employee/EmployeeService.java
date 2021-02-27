package com.webbdealer.detailing.employee;

import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.employee.dto.EmployeeCreateForm;
import com.webbdealer.detailing.employee.dto.EmployeePinForm;
import com.webbdealer.detailing.employee.dto.EmployeeResponse;
import com.webbdealer.detailing.job.dao.Job;

import java.util.List;

public interface EmployeeService {

    User createAdmin(User user);

    User createManager(User user);

    User storeEmployee(User user);

    User storePin(EmployeePinForm pin, Long userId);

    User assignJobsToEmployee(Long companyId, List<Job> jobs, User user);

    List<User> fetchEmployees();

    List<EmployeeResponse> mapEmployeeListToResponseList(List<User> users);

    EmployeeResponse mapEmployeeToResponse(User user);
}

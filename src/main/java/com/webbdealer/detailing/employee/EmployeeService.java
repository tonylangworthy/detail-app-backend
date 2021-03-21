package com.webbdealer.detailing.employee;

import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.employee.dto.EmployeeCreateForm;
import com.webbdealer.detailing.employee.dto.EmployeePinForm;
import com.webbdealer.detailing.employee.dto.EmployeeResponse;
import com.webbdealer.detailing.job.dao.Job;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Optional<User> fetchById(Long userId);

    User fetchByIdReference(Long userId);

    User createAdmin(User user);

    User createManager(User user);

    User storeEmployee(User user);

    User storePin(EmployeePinForm pin, Long userId);

    User assignJobsToEmployee(Long companyId, List<Job> jobs, User user);

    EmployeeResponse fetchEmployeeDetails(Long userId);

    List<User> fetchEmployees();

    List<EmployeeResponse> mapEmployeeListToResponseList(List<User> users);

    EmployeeResponse mapEmployeeToResponse(User user);
}

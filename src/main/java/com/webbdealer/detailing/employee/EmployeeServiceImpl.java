package com.webbdealer.detailing.employee;

import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.employee.dao.UserRepository;
import com.webbdealer.detailing.employee.dto.EmployeeCreateForm;
import com.webbdealer.detailing.employee.dto.EmployeePinForm;
import com.webbdealer.detailing.employee.dto.EmployeeResponse;
import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.security.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private AuthorityService authorityService;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(AuthorityService authorityService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authorityService = authorityService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> fetchById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User fetchByIdReference(Long userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public User createAdmin(User user) {
        authorityService.addToAdminRole(user);
        return userRepository.save(user);
    }

    @Override
    public User createManager(User user) {
        authorityService.addToManagerRole(user);
        authorityService.addToEmployeeRole(user);
        return userRepository.save(user);
    }

    @Override
    public User storeEmployee(User user) {
        authorityService.addToEmployeeRole(user);
        return userRepository.save(user);
    }

    @Override
    public User storePin(EmployeePinForm pin, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User with ID of ["+userId+"] not found!"));
        user.setPin(passwordEncoder.encode(pin.getPin()));
        return userRepository.save(user);
    }

    @Override
    public User assignJobsToEmployee(Long companyId, List<Job> jobs, User user) {
        return null;
    }

    @Override
    public EmployeeResponse fetchEmployeeDetails(Long userId) {
        Optional<User> optionalUser = fetchById(userId);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User with id of ["+userId+"] not found!"));
        return mapEmployeeToResponse(user);
    }

    @Override
    public List<User> fetchEmployees() {
        return userRepository.findAll();
    }

    @Override
    public List<EmployeeResponse> mapEmployeeListToResponseList(List<User> users) {
        List<EmployeeResponse> employeeResponseList = new ArrayList<>();

        users.forEach(user -> {
            employeeResponseList.add(mapEmployeeToResponse(user));
        });
        return employeeResponseList;
    }

    @Override
    public EmployeeResponse mapEmployeeToResponse(User user) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(user.getId());
        employeeResponse.setFirstName(user.getFirstName());
        employeeResponse.setMiddle(user.getMiddle());
        employeeResponse.setLastName(user.getLastName());
        employeeResponse.setEmail(user.getEmail());
        employeeResponse.setPhone(user.getPhone());
        employeeResponse.setMobile(user.getIsMobile());
        employeeResponse.setRoles(
                user.getRoles().stream()
                        .map(role -> role.getName()).collect(Collectors.toList())
        );
        employeeResponse.setCreatedAt(user.getCreatedAt());
        employeeResponse.setUpdatedAt(user.getUpdatedAt());
        return employeeResponse;
    }
}

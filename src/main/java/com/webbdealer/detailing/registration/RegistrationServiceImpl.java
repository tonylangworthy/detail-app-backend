package com.webbdealer.detailing.registration;

import com.webbdealer.detailing.company.CompanyService;
import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.company.dao.CompanyRepository;
import com.webbdealer.detailing.company.dto.CompanyForm;
import com.webbdealer.detailing.employee.EmployeeService;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.employee.dao.UserRepository;
import com.webbdealer.detailing.employee.dto.UserForm;
import com.webbdealer.detailing.registration.dto.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService{

    private CompanyService companyService;

    private UserRepository userRepository;

    private EmployeeService employeeService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServiceImpl(
            CompanyService companyService,
            UserRepository userRepository,
            EmployeeService employeeService,
            PasswordEncoder passwordEncoder) {
        this.companyService = companyService;
        this.userRepository = userRepository;
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegistrationForm register(RegistrationForm registrationForm) {

        CompanyForm companyForm = registrationForm.getCompany();
        UserForm userForm = registrationForm.getUser();

        Company company = new Company.CompanyBuilder(
                companyForm.getName(),
                companyForm.getEmail(),
                companyForm.getPhone())
                .address1(companyForm.getAddress1())
                .address2(companyForm.getAddress2())
                .city(companyForm.getCity())
                .state(companyForm.getState())
                .zip(companyForm.getZip())
                .canReceiveTexts(companyForm.isReceiveTexts())
                .website(companyForm.getWebsite())
                .build();
        Company savedCompany = companyService.createCompany(company);

        User user = new User.UserBuilder()
                .firstName(userForm.getFirstName())
                .middle((userForm.getMiddle()))
                .lastName(userForm.getLastName())
                .phone(userForm.getPhone())
                .isMobile(userForm.getIsMobile())
                .email(userForm.getEmail())
                .password(passwordEncoder.encode(userForm.getPassword()))
                .company(savedCompany)
                .build();
        employeeService.createAdmin(user);

        return registrationForm;
    }
}

package com.webbdealer.detailing.registration;

import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.registration.dto.RegistrationForm;

public interface RegistrationService {

    RegistrationForm register(RegistrationForm registrationForm);
}

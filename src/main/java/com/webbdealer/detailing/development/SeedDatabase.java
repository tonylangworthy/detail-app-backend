package com.webbdealer.detailing.development;

import com.webbdealer.detailing.company.*;
import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.employee.EmployeeService;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.registration.RegistrationService;
import com.webbdealer.detailing.security.AuthorityService;
import com.webbdealer.detailing.security.dao.Privilege;
import com.webbdealer.detailing.security.dao.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
public class SeedDatabase implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;
	
	private RegistrationService registrationService;

	private CompanyService companyService;
	
	private EmployeeService employeeService;
	
    private AuthorityService authorityService;

    PasswordEncoder passwordEncoder;
    
    @Autowired
	public SeedDatabase(RegistrationService registrationService,
						CompanyService companyService,
						EmployeeService employeeService,
						AuthorityService authorityService,
						PasswordEncoder passwordEncoder) {
		this.registrationService = registrationService;
		this.companyService = companyService;
		this.employeeService = employeeService;
		this.authorityService = authorityService;
		this.passwordEncoder = passwordEncoder;
	}


    @Transactional
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		if(alreadySetup) {
			return;
		}
		
		// Create the privileges
        Privilege readJobs = authorityService.findOrCreatePrivilege("READ_JOBS");
		Privilege writeJobs = authorityService.findOrCreatePrivilege("WRITE_JOBS");
		Privilege updateJobs = authorityService.findOrCreatePrivilege("UPDATE_JOBS");
		Privilege deleteJobs = authorityService.findOrCreatePrivilege("DELETE_JOBS");

		Privilege readUsers = authorityService.findOrCreatePrivilege("READ_USER");
		Privilege writeUsers = authorityService.findOrCreatePrivilege("WRITE_USER");
		Privilege updateUsers = authorityService.findOrCreatePrivilege("UPDATE_USER");
		Privilege deleteUsers = authorityService.findOrCreatePrivilege("DELETE_USER");
        
        Role adminRole = authorityService.findOrCreateRole("ROLE_ADMIN");
        Role managerRole = authorityService.findOrCreateRole("ROLE_MANAGER");
        Role detailerRole = authorityService.findOrCreateRole("ROLE_DETAILER");

//		if(!authorityService.roleExists(adminRole.getName())) {
			// Add all privileges to the admin role
			List<Privilege> adminPrivileges = Arrays.asList(
					readJobs, writeJobs, updateJobs, deleteJobs, readUsers, writeUsers, updateUsers, deleteUsers
			);
//		}
//		if(!authorityService.roleExists(managerRole.getName())) {
			// Create the manager privileges
			List<Privilege> managerPrivileges = Arrays.asList(readJobs, writeJobs, updateJobs, readUsers);
//		}
//		if(!authorityService.roleExists(detailerRole.getName())) {
			// Create the detailer privileges
			List<Privilege> detailerPrivileges = Arrays.asList(readJobs, updateJobs, readUsers);
//		}
		authorityService.addPrivilegesToRole(adminPrivileges, adminRole);
		authorityService.addPrivilegesToRole(managerPrivileges, managerRole);
		authorityService.addPrivilegesToRole(detailerPrivileges, detailerRole);

		String companyEmail = "andy@midmodents.com";
		String companyPhone = "573-555-7899";
		if(!companyService.companyExists(companyEmail, companyPhone)) {
			// Create company and employees
			Company company1 = new Company.CompanyBuilder("Mid-Mo Dents", companyEmail, companyPhone)
					.address1("3209 South Ten Mile Drive")
					.city("Jefferson City")
					.state("MO")
					.zip("65109")
					.website("midmodents.com")
					.canReceiveTexts(true)
					.build();
			companyService.createCompany(company1);

			String user1Email = "andy@midmodents.com";
			String user1Phone = "5735501234";

			User user1 = new User();
			user1.setFirstName("Andy");
			user1.setLastName("Ott");
			user1.setEmail(user1Email);
			user1.setPhone(user1Phone);
			user1.setPassword(passwordEncoder.encode("12345"));
			user1.setCompany(company1);
			employeeService.createAdmin(user1);

			String user2Email = "tony@webbdealer.com";
			String user2Phone = "5735508456";

			User user2 = new User();
			user2.setFirstName("Tony");
			user2.setLastName("Langworthy");
			user2.setEmail(user2Email);
			user2.setPhone(user2Phone);
			user2.setPassword(passwordEncoder.encode("Acura21"));
			user2.setCompany(company1);
			employeeService.createManager(user2);

			String user3Email = "khigby@gmail.com";
			String user3Phone = "5732911234";

			User user3 = new User();
			user3.setFirstName("Keith");
			user3.setLastName("Higby");
			user3.setEmail(user3Email);
			user3.setPhone(user3Phone);
			user3.setPassword(passwordEncoder.encode("password"));
			user3.setCompany(company1);
			employeeService.storeEmployee(user3);

			String user4Email = "josh1111@gmail.com";
			String user4Phone = "5736911221";

			User user4 = new User();
			user4.setFirstName("Joshua");
			user4.setLastName("Chappell");
			user4.setEmail(user4Email);
			user4.setPhone(user4Phone);
			user4.setPassword(passwordEncoder.encode("password"));
			user4.setCompany(company1);
			employeeService.storeEmployee(user4);


		}
        alreadySetup = true;
	}
	

}

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
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		if (alreadySetup) {
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
		Role ownerRole = authorityService.findOrCreateRole("ROLE_OWNER");
		Role managerRole = authorityService.findOrCreateRole("ROLE_MANAGER");
		Role employeeRole = authorityService.findOrCreateRole("ROLE_EMPLOYEE");
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

		String company1Email = "andy@midmodents.com";
		String company1Phone = "573-555-7899";
		if (!companyService.companyExists(company1Email, company1Phone)) {
			// Create company and employees
			Company company1 = new Company.CompanyBuilder("Mid-Mo Dents", company1Email, company1Phone)
					.address1("3209 South Ten Mile Drive")
					.city("Jefferson City")
					.state("MO")
					.zip("65109")
					.website("midmodents.com")
					.canReceiveTexts(true)
					.build();
//			company1.setId(1L);
			companyService.createCompany(company1);

			String c1user1Email = "andy@midmodents.com";
			String c1user1Phone = "5735501234";

			User c1user1 = new User();
			c1user1.setFirstName("Andy");
			c1user1.setLastName("Ott");
			c1user1.setEmail(c1user1Email);
			c1user1.setPhone(c1user1Phone);
			c1user1.setPassword(passwordEncoder.encode("12345"));
			c1user1.setCompany(company1);
			employeeService.createAdmin(c1user1);

			String c1user2Email = "tony@webbdealer.com";
			String c1user2Phone = "5735508456";

			User c1user2 = new User();
			c1user2.setFirstName("Tony");
			c1user2.setLastName("Langworthy");
			c1user2.setEmail(c1user2Email);
			c1user2.setPhone(c1user2Phone);
			c1user2.setPassword(passwordEncoder.encode("Acura21"));
			c1user2.setCompany(company1);
			employeeService.createManager(c1user2);

			String c1user3Email = "khigby@gmail.com";
			String c1user3Phone = "5732911234";

			User c1user3 = new User();
			c1user3.setFirstName("Keith");
			c1user3.setLastName("Higby");
			c1user3.setEmail(c1user3Email);
			c1user3.setPhone(c1user3Phone);
			c1user3.setPassword(passwordEncoder.encode("password"));
			c1user3.setCompany(company1);
			employeeService.storeEmployee(c1user3);

			String c1user4Email = "josh1111@gmail.com";
			String c1user4Phone = "5736911221";

			User c1user4 = new User();
			c1user4.setFirstName("Joshua");
			c1user4.setLastName("Chappell");
			c1user4.setEmail(c1user4Email);
			c1user4.setPhone(c1user4Phone);
			c1user4.setPassword(passwordEncoder.encode("password"));
			c1user4.setCompany(company1);
			employeeService.storeEmployee(c1user4);

			// ---------------------------------------------------------------------

			String company2Email = "brandy@slickwhips.com";
			String company2Phone = "573-554-1117";
			if (!companyService.companyExists(company2Email, company2Phone)) {
				// Create company and employees
				Company company2 = new Company.CompanyBuilder("Brandy's Slick Whips", company2Email, company2Phone)
						.address1("1799 Sunset Park Lane")
						.city("Monterey")
						.state("CA")
						.zip("93940")
						.website("slickwhips.com")
						.canReceiveTexts(true)
						.build();
//				company2.setId(2L);
				companyService.createCompany(company2);

				String c2user1Email = "brandy@slickwhips.com";
				String c2user1Phone = "5735541117";
				User c2user1 = new User();
				c2user1.setFirstName("Brandy");
				c2user1.setLastName("Elkins");
				c2user1.setEmail(c2user1Email);
				c2user1.setPhone(c2user1Phone);
				c2user1.setPassword(passwordEncoder.encode("belkins"));
				c2user1.setCompany(company2);
				employeeService.createAdmin(c2user1);

				String c2user2Email = "brayjames@gmail.com";
				String c2user2Phone = "5735504455";
				User c2user2 = new User();
				c2user2.setFirstName("Brayden");
				c2user2.setLastName("James");
				c2user2.setEmail(c2user2Email);
				c2user2.setPhone(c2user2Phone);
				c2user2.setPassword(passwordEncoder.encode("bjames"));
				c2user2.setCompany(company2);
				employeeService.createManager(c2user2);

				String c2user3Email = "madisongrace@gmail.com";
				String c2user3Phone = "5732914447";
				User c2user3 = new User();
				c2user3.setFirstName("Madison");
				c2user3.setLastName("Grace");
				c2user3.setEmail(c2user3Email);
				c2user3.setPhone(c2user3Phone);
				c2user3.setPassword(passwordEncoder.encode("mgrace"));
				c2user3.setCompany(company2);
				employeeService.storeEmployee(c2user3);

				String c2user4Email = "audriefaith@gmail.com";
				String c2user4Phone = "5736918889";
				User c2user4 = new User();
				c2user4.setFirstName("Audrie");
				c2user4.setLastName("Faith");
				c2user4.setEmail(c2user4Email);
				c2user4.setPhone(c2user4Phone);
				c2user4.setPassword(passwordEncoder.encode("afaith"));
				c2user4.setCompany(company2);
				employeeService.storeEmployee(c2user4);

				String c2user5Email = "suepearon@gmail.com";
				String c2user5Phone = "5736917894";
				User c2user5 = new User();
				c2user5.setFirstName("Susan");
				c2user5.setLastName("Pearon");
				c2user5.setEmail(c2user5Email);
				c2user5.setPhone(c2user5Phone);
				c2user5.setPassword(passwordEncoder.encode("spearon"));
				c2user5.setCompany(company2);
				employeeService.storeEmployee(c2user5);

			}
			alreadySetup = true;
		}

	}
}

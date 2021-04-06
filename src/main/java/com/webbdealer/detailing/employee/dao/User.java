package com.webbdealer.detailing.employee.dao;

import com.fasterxml.jackson.annotation.*;
import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dao.JobAction;
import com.webbdealer.detailing.security.dao.Role;
import com.webbdealer.detailing.timeclock.dao.TimeClock;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle")
    private String middle;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "is_mobile", columnDefinition = "boolean default false")
    private boolean isMobile;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "pin")
    private String pin;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private Company company;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TimeClock> timeClocks;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

//    @ManyToMany(mappedBy = "employees")
//    @JsonIgnore
//    private List<Job> jobs;

    @OneToMany(mappedBy = "user")
    private List<JobAction> jobActions = new ArrayList<>();

    @ManyToMany(mappedBy = "assignedEmployees")
    @JsonIgnore
    List<Job> jobs = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private boolean deleted;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public User() {}

    public User(UserBuilder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.middle = builder.middle;
        this.lastName = builder.lastName;
        this.phone = builder.phone;
        this.isMobile = builder.isMobile;
        this.email = builder.email;
        this.userName = builder.userName;
        this.password = builder.password;
        this.pin = builder.pin;
        this.company = builder.company;
        this.roles = builder.roles;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(boolean isMobile) {
        this.isMobile = isMobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<TimeClock> getTimeclocks() {
        return timeClocks;
    }

    public void setTimeclocks(List<TimeClock> timeClocks) {
        this.timeClocks = timeClocks;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<JobAction> getJobActions() {
        return jobActions;
    }

    public void setJobActions(List<JobAction> jobActions) {
        this.jobActions = jobActions;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return isMobile == user.isMobile &&
                deleted == user.deleted &&
                Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(middle, user.middle) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(email, user.email) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(pin, user.pin) &&
                Objects.equals(company, user.company) &&
                Objects.equals(timeClocks, user.timeClocks) &&
                Objects.equals(roles, user.roles) &&
                Objects.equals(jobActions, user.jobActions) &&
                Objects.equals(jobs, user.jobs) &&
                Objects.equals(createdAt, user.createdAt) &&
                Objects.equals(updatedAt, user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, middle, lastName, phone, isMobile, email, userName, password, pin, company, timeClocks, roles, jobActions, jobs, createdAt, updatedAt, deleted);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middle='" + middle + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", isMobile=" + isMobile +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", pin='" + pin + '\'' +
                ", roles=" + roles +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deleted=" + deleted +
                '}';
    }

    public static class UserBuilder {

        private Long id;

        private String firstName;

        private String middle;

        private String lastName;

        private String phone;

        private boolean isMobile;

        private String email;

        private String userName;

        private String password;

        private String pin;

        private Company company;

        private List<Role> roles = new ArrayList<>();

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder middle(String middle) {
            this.middle = middle;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder isMobile(boolean isMobile) {
            this.isMobile = isMobile;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder pin(String pin) {
            this.pin = pin;
            return this;
        }

        public UserBuilder company(Company company) {
            this.company = company;
            return this;
        }

        public UserBuilder roles(List<Role> roles) {
            this.roles = roles;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}

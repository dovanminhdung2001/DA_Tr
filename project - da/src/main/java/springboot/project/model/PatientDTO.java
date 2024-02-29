package springboot.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import springboot.project.entity.DoctorUser;
import springboot.project.entity.Schedule;
import springboot.project.entity.TestResults;
import springboot.project.entity.User;

import java.util.Date;
import java.util.List;

public class PatientDTO {
    private int id;

    private DoctorUser doctorUser;

    private String name;

    private User user;

    private List<Schedule> schedule;

    private Date birthDate;

    private String cccd;

    private String address;

    private String description;

    private TestResults testResults;

    public PatientDTO() {
    }

    public PatientDTO(DoctorUser doctorUser, String name, User user, List<Schedule> schedule,
                      Date birthDate, String  CCCD, String address) {
//        this.doctorUser = doctorUser;
        this.name = name;
        this.user = user;
        this.schedule = schedule;
        this.birthDate = birthDate;
        this.cccd = CCCD;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DoctorUser getDoctorUser() {
        return doctorUser;
    }

    public void setDoctorUser(DoctorUser doctorUser) {
        this.doctorUser = doctorUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TestResults getTestResults() {
        return testResults;
    }

    public void setTestResults(TestResults testResults) {
        this.testResults = testResults;
    }
}

package springboot.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "patients") // nguoi benh
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "doctorId")
    @JsonIgnore
    private DoctorUser doctorUser;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "patientId")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "patient")
    private List<Schedule> schedule;

    @OneToOne(mappedBy = "patient")
    private TestResults testResults;

    @Column(name = "birth_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreatedDate
    private Date birthDate;

    @Column(name = "cccd")
    private String CCCD;

    @Column(name = "address")
    private String address;


    public Patient() {
    }

    public Patient(DoctorUser doctorUser, String name, User user, List<Schedule> schedule,
                    Date birthDate, String CCCD, String address) {
        this.doctorUser = doctorUser;
        this.name = name;
        this.user = user;
        this.schedule = schedule;
        this.birthDate = birthDate;
        this.CCCD = CCCD;
        this.address = address;
    }

    public Patient(Date birthDate) {
        this.birthDate = birthDate;
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

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TestResults getTestResults() {
        return testResults;
    }

    public void setTestResults(TestResults testResults) {
        this.testResults = testResults;
    }
}

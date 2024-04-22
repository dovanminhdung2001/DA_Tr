package springboot.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import springboot.project.entity.DoctorUser;
import springboot.project.entity.MedicalHistory;
import springboot.project.entity.Patient;
import springboot.project.entity.Role;

import java.util.Date;

@Data
public class UserDTO {
    private int id;

    private String name;

    private String email;

    private String password;

    private String address;

    private String phone;

    private String avatar;

    private String gender;

    private String description;

    private Role role;
    private Integer roleId;

    private boolean isActive;

    private String checkPass;

    private Patient patient;
    private MedicalHistory medicalHistory;
    private DoctorUser doctorUser;
    private String historyBreath;

    private String cccd;

    private Integer createdBy;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    private Date createdAt;
    private Date updatedAt;
    private Integer age;
    public UserDTO() {
    }

    public UserDTO(String name, String email, String password, String address, Patient patient, String historyBreath,
                   String phone, String avatar, String gender, String description, String checkPass, DoctorUser doctorUser,
                   Role role, boolean isActive, Date createdAt, Date updatedAt, Date deletedAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
        this.gender = gender;
        this.description = description;
        this.role = role;
        this.historyBreath = historyBreath;
        this.isActive = isActive;
        this.checkPass = checkPass;
        this.patient = patient;
        this.doctorUser = doctorUser;
    }

    // list-user - web
    public UserDTO(int id, String name, String email, String address, String phone, String avatar, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
        this.age = age;
    }

    // get by id - web

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCheckPass() {
        return checkPass;
    }

    public void setCheckPass(String checkPass) {
        this.checkPass = checkPass;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getHistoryBreath() {
        return historyBreath;
    }

    public void setHistoryBreath(String historyBreath) {
        this.historyBreath = historyBreath;
    }

    public DoctorUser getDoctorUser() {
        return doctorUser;
    }

    public void setDoctorUser(DoctorUser doctorUser) {
        this.doctorUser = doctorUser;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}

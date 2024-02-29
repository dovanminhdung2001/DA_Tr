package springboot.project.model;

import springboot.project.entity.Clinic;
import springboot.project.entity.Role;
import springboot.project.entity.Specialization;
import springboot.project.entity.User;

import java.util.Date;

public class DoctorUserDTO {
    private Integer id;

    private String name;
    private String email;
    private String password;
    private Date dob;
    private String gender;
    private String address;
    private String country;
    private Integer numberChoose;
    private String avatar;
    private String cccd;
    private String description;
    private Boolean isActive;
    private String phone;
    private Integer roleId;
    private Date birthDate;
    private String generalIntroduction;

    private String trainingProcess;

    private String achievementsAchieved;

    private String specialtiesInCharge;

    private User user;

    private Clinic clinic;

    private Specialization specialization;

    public DoctorUserDTO() {
    }

    public DoctorUserDTO(String name, String generalIntroduction,
                         String trainingProcess, String achievementsAchieved,
                         String specialtiesInCharge, User user, Clinic clinic,
                         Specialization specialization) {
        this.name = name;
        this.generalIntroduction = generalIntroduction;
        this.trainingProcess = trainingProcess;
        this.achievementsAchieved = achievementsAchieved;
        this.specialtiesInCharge = specialtiesInCharge;
        this.user = user;
        this.clinic = clinic;
        this.specialization = specialization;
    }

    public Integer getId() {
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

    public String getGeneralIntroduction() {
        return generalIntroduction;
    }

    public void setGeneralIntroduction(String generalIntroduction) {
        this.generalIntroduction = generalIntroduction;
    }

    public String getTrainingProcess() {
        return trainingProcess;
    }

    public void setTrainingProcess(String trainingProcess) {
        this.trainingProcess = trainingProcess;
    }

    public String getAchievementsAchieved() {
        return achievementsAchieved;
    }

    public void setAchievementsAchieved(String achievementsAchieved) {
        this.achievementsAchieved = achievementsAchieved;
    }

    public String getSpecialtiesInCharge() {
        return specialtiesInCharge;
    }

    public void setSpecialtiesInCharge(String specialtiesInCharge) {
        this.specialtiesInCharge = specialtiesInCharge;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getNumberChoose() {
        return numberChoose;
    }

    public void setNumberChoose(Integer numberChoose) {
        this.numberChoose = numberChoose;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}

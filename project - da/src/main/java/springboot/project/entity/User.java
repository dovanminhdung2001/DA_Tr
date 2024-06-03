package springboot.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(columnDefinition = "LONGTEXT")
    private String avatar;

    @Column(name = "gender")
    private String gender;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId")
    private Role role;

    @OneToOne(mappedBy = "user")
    private Patient patient;

    @OneToOne(mappedBy = "user")
    private MedicalHistory medicalHistory;

    @Column(name = "isActive")
    private Boolean isActive = false;

    @Column(name = "birth_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreatedDate
    private Date birthDate;

    private String cccd;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id")
    private Device device;
    private Date CreatedDate;
    public User() {
    }

    public User(String name, String email, String password, String address, String phone, String avatar, String gender, String description, Role role, Boolean isActive, String cccd, Date birthDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
        this.gender = gender;
        this.description = description;
        this.role = role;
        this.isActive = isActive;
        this.cccd = cccd;
        this.birthDate = birthDate;
    }

    public User(String name, String email, String password, String address,
                String phone, String avatar, String gender, String description,
                Patient patient, Role role, Boolean isActive) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
        this.patient = patient;
        this.gender = gender;
        this.description = description;
        this.role = role;
        this.isActive = isActive;

    }


    public Boolean getActive() {
        return isActive;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

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

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }
}

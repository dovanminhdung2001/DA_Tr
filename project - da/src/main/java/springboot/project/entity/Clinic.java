package springboot.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clinics")
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "introductionHTML")
    private String introductionHTML;

    @Column(name = "introductionMarkdown")
    private String introductionMarkdown;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "clinic")
    @JsonIgnore
    private List<DoctorUser> doctorUsers;

    @Column(name = "number_choose")
    private int numberChoose = 0;

    @Column(name = "createdAt")
    private Date createdAt;

    public Clinic() {
    }

    public Clinic(String name, String address, String phone, String introductionHTML,int numberChoose,
                  String introductionMarkdown, String description, String image, Date createdAt) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.numberChoose = numberChoose;
        this.introductionHTML = introductionHTML;
        this.introductionMarkdown = introductionMarkdown;
        this.description = description;
        this.image = image;
        this.createdAt = createdAt;
    }

    public Clinic(String name, String address, String phone, String introductionHTML, String introductionMarkdown, String description, String image, List<DoctorUser> doctorUsers, Date createdAt) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.introductionHTML = introductionHTML;
        this.introductionMarkdown = introductionMarkdown;
        this.description = description;
        this.image = image;
        this.doctorUsers = doctorUsers;
        this.createdAt = createdAt;
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

    public String getIntroductionHTML() {
        return introductionHTML;
    }

    public void setIntroductionHTML(String introductionHTML) {
        this.introductionHTML = introductionHTML;
    }

    public String getIntroductionMarkdown() {
        return introductionMarkdown;
    }

    public void setIntroductionMarkdown(String introductionMarkdown) {
        this.introductionMarkdown = introductionMarkdown;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getNumberChoose() {
        return numberChoose;
    }

    public void setNumberChoose(int numberChoose) {
        this.numberChoose = numberChoose;
    }


    public List<DoctorUser> getDoctorUsers() {
        return doctorUsers;
    }

    public void setDoctorUsers(List<DoctorUser> doctorUsers) {
        this.doctorUsers = doctorUsers;
    }
}

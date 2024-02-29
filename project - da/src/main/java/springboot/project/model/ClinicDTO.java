package springboot.project.model;

import springboot.project.entity.DoctorUser;

import java.util.Date;
import java.util.List;

public class ClinicDTO {
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String introductionHTML;
    private String introductionMarkdown;
    private String description;
    private String image;
    private String workingTime;
    private Integer examinationCosts;
    private List<DoctorUser> doctorUserList;
    private Integer numberChoose;
    private Date createdAt;

    public ClinicDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    public Integer getExaminationCosts() {
        return this.examinationCosts;
    }

    public void setExaminationCosts(Integer examinationCosts) {
        this.examinationCosts = examinationCosts;
    }

    public List<DoctorUser> getDoctorUserList() {
        return doctorUserList;
    }

    public void setDoctorUserList(List<DoctorUser> doctorUserList) {
        this.doctorUserList = doctorUserList;
    }

    public Integer getNumberChoose() {
        return numberChoose;
    }

    public void setNumberChoose(Integer numberChoose) {
        this.numberChoose = numberChoose;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

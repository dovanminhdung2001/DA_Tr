package springboot.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Data
@Table(name = "schedules") // lichj trinhf
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "doctorId")
    @JsonIgnore
    private DoctorUser doctorUser;

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private Integer time;
    @Column(name = "shiftId")
    private Integer shiftId;
    @Column(name = "examinationPrice")
    private Integer examinationPrice;
    @Column(name = "userphone")
    private String userPhone;

    private int status;

    private String type;

    private String pathological;

    @OneToOne(mappedBy = "schedule")
    private TestResults testResults;

    @Column(name = "createdAt")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreatedDate
    private Date createdAt;

    @Column(name = "updatedAt")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreatedDate
    private Date updatedAt;

    @Column(name = "deletedAt")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreatedDate
    private Date deletedAt;
    private String description;
    private String name;
    private String address;
    private Integer gender;
    private Date birthDate;
    private String cccd;
    private Integer districtId;
    private Integer provinceId;
    private Integer communeId;
    private String guardian;
    private String phoneGuardian;
    private Integer relationship;
    private Integer createdBy;
    private Integer specializationId;
    public Schedule() {
    }
    public Schedule(DoctorUser doctorUser, Date workingDate, Integer shiftId, Integer shiftTime,
                    Integer examinationCosts, String userPhone, String name, String address, Integer gender,
                    Date birthDate, String cccd, Integer districtId, Integer provinceId, Integer communeId,
                    String guardian, String phoneGuardian, Integer relationship, Integer patientId, Integer status,
                    String pathological, Integer specializationId) {
        this.doctorUser = doctorUser;
        this.date = workingDate;
        this.shiftId = shiftId;
        this.time = shiftTime;
        this.examinationPrice = examinationCosts;
        this.userPhone = userPhone;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.birthDate = birthDate;
        this.cccd = cccd;
        this.districtId = districtId;
        this.provinceId = provinceId;
        this.communeId = communeId;
        this.guardian = guardian;
        this.phoneGuardian = phoneGuardian;
        this.relationship = relationship;
        this.createdBy = patientId;
        this.status = status;
        this.pathological = pathological;
        this.specializationId = specializationId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getExaminationPrice() {
        return examinationPrice;
    }

    public void setExaminationPrice(Integer examinationPrice) {
        this.examinationPrice = examinationPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPathological() {
        return pathological;
    }

    public void setPathological(String pathological) {
        this.pathological = pathological;
    }

    public TestResults getTestResults() {
        return testResults;
    }

    public void setTestResults(TestResults testResults) {
        this.testResults = testResults;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}

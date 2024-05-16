package springboot.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "doctor_date")
public class DoctorDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "doctorId")
    @JsonIgnore
    private DoctorUser doctorUser;

    @Column(name = "working_date")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Date workingDate;


    @Column(name = "examination_costs")
    private Integer examinationCosts;

    @OneToMany(mappedBy = "doctorDate")
    private List<DateShift> dateShifts;


    public DoctorDate() {
    }

    public DoctorDate(DoctorUser doctorUser, Date workingDate, Integer examinationCosts) {
        this.doctorUser = doctorUser;
        this.workingDate = workingDate;
        this.examinationCosts = examinationCosts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DoctorUser getDoctorUser() {
        return doctorUser;
    }

    public void setDoctorUser(DoctorUser doctorUser) {
        this.doctorUser = doctorUser;
    }

    public Date getWorkingDate() {
        return workingDate;
    }

    public void setWorkingDate(Date workingDate) {
        this.workingDate = workingDate;
    }

    public Integer getExaminationCosts() {
        return examinationCosts;
    }

    public void setExaminationCosts(Integer examinationCosts) {
        this.examinationCosts = examinationCosts;
    }
}

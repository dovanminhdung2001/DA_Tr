package springboot.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "date_shift")
public class DateShift {
    @Id
    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "dateId")
    @JsonIgnore
    private DoctorDate doctorDate;

    @Column
    private String shiftTime;
    @Column
    private Boolean isActive;

    public DateShift() {
    }

    public DateShift(DoctorDate doctorDate, String shiftTime, Boolean activate) {
        this.doctorDate = doctorDate;
        this.shiftTime = shiftTime;
        this.isActive = activate;
    }
}
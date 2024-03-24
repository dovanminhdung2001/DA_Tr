package springboot.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import springboot.project.entity.Clinic;
import springboot.project.entity.DoctorUser;
import springboot.project.entity.Patient;
import springboot.project.entity.TestResults;

import java.util.Date;

@Data
public class ScheduleDTO {

    private int id;

    private DoctorUser doctorUser;

    private String date;

    private Integer time;

    private int status;

    private Patient patient;

    private String type;
    private Clinic clinic;

    private String examinationPrice;

    private String personalInformation;

    private String pathological;

    private TestResults testResults;
/////////
    private Integer doctorId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date workingDate;
    private Integer shiftId;
    private String userPhone;
    private String description;
    private String name;
    private String address;
    private Integer gender;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    private String cccd;
    private Integer districtId;
    private Integer provinceId;
    private Integer communeId;
    private String guardian;
    private String phoneGuardian;
    private Integer relationship;
///////////
    public ScheduleDTO() {
    }

    public ScheduleDTO(DoctorUser doctorUser, String date, Integer time,
                       Patient patient, Clinic clinic, String examinationPrice,
                       String personalInformation, String pathological) {
        this.doctorUser = doctorUser;
        this.date = date;
        this.time = time;
        this.clinic = clinic;
        this.examinationPrice = examinationPrice;
        this.personalInformation = personalInformation;
        this.pathological = pathological;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
    public String getExaminationPrice() {
        return examinationPrice;
    }

    public void setExaminationPrice(String examinationPrice) {
        this.examinationPrice = examinationPrice;
    }

    public String getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(String personalInformation) {
        this.personalInformation = personalInformation;
    }

    public String getPathological() {
        return pathological;
    }

    public void setPathological(String pathological) {
        this.pathological = pathological;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TestResults getTestResults() {
        return testResults;
    }

    public void setTestResults(TestResults testResults) {
        this.testResults = testResults;
    }
}

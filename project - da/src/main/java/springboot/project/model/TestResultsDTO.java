package springboot.project.model;

import lombok.Data;
import springboot.project.entity.Patient;
import springboot.project.entity.Schedule;
import springboot.project.entity.User;

@Data
public class TestResultsDTO {
    private int id;

    private Schedule schedule;

    private String description;

    private User user;

    private Patient patient;
    private Integer scheduleId;
    private Integer userId;
    private String note;

    public TestResultsDTO() {
    }

    public TestResultsDTO(Schedule schedule, String description) {
        this.schedule = schedule;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}

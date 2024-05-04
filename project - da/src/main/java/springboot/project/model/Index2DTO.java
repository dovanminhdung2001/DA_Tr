package springboot.project.model;

import lombok.Data;
import org.springframework.data.domain.Page;
import springboot.project.entity.DoctorUser;
import springboot.project.entity.User;

@Data
public class Index2DTO {
    private Integer clinicDoctors;
    private Integer homeDoctors;
    private Integer patients;
    private Integer schedules;
    private Page<User> users;
    private Page<DoctorUser> doctorUsers;
}

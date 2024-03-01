package springboot.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.project.entity.DoctorDate;
import springboot.project.entity.DoctorUser;

import java.util.Date;

public interface DoctorDateRepository extends JpaRepository<DoctorDate, Integer> {
        DoctorDate findByDoctorUserAndWorkingDate(DoctorUser doctorUser, Date workingDate);
}

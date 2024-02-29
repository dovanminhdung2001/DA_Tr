package springboot.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.project.entity.DoctorDate;

public interface DoctorDateRepository extends JpaRepository<DoctorDate, Integer> {

}

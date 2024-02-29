package springboot.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springboot.project.entity.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query(value = "select s.* from schedules s \n" +
            "join patients p \n" +
            "on p.id = s.patient_id\n" +
            "join users u \n" +
            "on u.id = p.patient_id\n" +
            "where u.id = ?",nativeQuery = true)
    List<Schedule> getById(int id);
}
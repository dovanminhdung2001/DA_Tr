package springboot.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springboot.project.entity.Schedule;

import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query(value = "select s.* from schedules s \n" +
            "join users u \n" +
            "on u.id = s.created_by\n" +
            "where u.id = ?",nativeQuery = true)
    Page<Schedule> getById(Pageable pageable, int id);

    @Query(value = "SELECT s.* FROM schedules s " +
            "JOIN users u ON u.id = s.created_by " +
            "WHERE u.id = ? AND s.date BETWEEN ? AND ?",
            nativeQuery = true)
    Page<Schedule> getByIdAndDateRange(Pageable pageable, int id, Date fromDate, Date toDate);

    Page<Schedule> getAllByDoctorUser_Id(Pageable pageable, int id);

    Page<Schedule> getAllByDoctorUser_IdAndDateBetween(Pageable pageable, int id, Date fromDate, Date toDate);
}
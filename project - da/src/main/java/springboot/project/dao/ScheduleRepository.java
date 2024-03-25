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

    @Query(value = "select s.* " +
            "from schedules s " +
            "join users u " +
            "on u.id = s.created_by " +
            "where u.id = ?1 " +
            "and (s.date > current_date " +
            "or (s.date = current_date and s.time > extract(hour from current_time)))", nativeQuery = true)
    Page<Schedule> getAllForUserInFuture(Pageable pageable, int id);

    @Query(value = "select s.* " +
            "from schedules s " +
            "join users u " +
            "on u.id = s.created_by " +
            "where u.id = ?1 " +
            "and (s.date = current_date + interval '1 day' " +
            "or s.date = current_date + interval '2 day' " +
            "or (s.date = current_date and s.time > extract(hour from current_time)))", nativeQuery = true)
    Page<Schedule> getAllForUserIn3NextDays(Pageable pageable, int id);

    @Query(value = "select s.* " +
            "from schedules s " +
            "join users u " +
            "on u.id = s.created_by " +
            "where u.id = ?1 " +
            "and (case " +
            "when s.date = current_date then s.time > extract(hour from current_time) " +
            "else s.date = ?2 " +
            "end)", nativeQuery = true)
    Page<Schedule> getAllForUserAt(Pageable pageable, int id, Date date);

    @Query(value = "select s.* " +
            "from schedules s " +
            "join users u " +
            "on u.id = s.created_by " +
            "where u.id = ?1 " +
            "and s.status = ?2 " +
            "and (s.date < current_date " +
            "or (s.date = current_date and s.time <= extract(hour from current_time)))", nativeQuery = true)
    Page<Schedule> getPageForUserInPastByType(Pageable pageable, int id, Integer status);

    @Query(value = "select s.* " +
            "from schedules s " +
            "join users u " +
            "on u.id = s.created_by " +
            "where u.id = ?1 " +
            "and s.status = ?2" +
            "and (case " +
            "when s.date = current_date then s.time <= extract(hour from current_time) " +
            "else s.date = ?3 " +
            "end)", nativeQuery = true)
    Page<Schedule> getPageForUserInPastByTypeAt(Pageable pageable, int id, Integer status, Date date);

    @Query(value = "select s.* " +
            "from schedules s " +
            "join users u " +
            "on u.id = s.created_by " +
            "where u.id = ?1 " +
            "and (s.date < current_date " +
            "or (s.date = current_date and s.time <= extract(hour from current_time)))", nativeQuery = true)
    Page<Schedule> getAllForUserInPast(Pageable pageable, int id);

    @Query(value = "select s.* " +
            "from schedules s " +
            "join doctor_users u " +
            "on u.id = s.doctor_id " +
            "where u.id = ?1 " +
            "and (s.date > current_date " +
            "or (s.date = current_date and s.time > extract(hour from current_time)))", nativeQuery = true)
    Page <Schedule> getAllForDoctorInFuture(Pageable pageable, Integer doctorId);

    @Query(value = "select s.* " +
            "from schedules s " +
            "join doctor_users u " +
            "on u.id = s.doctor_id " +
            "where u.id = ?1 " +
            "and s.status = ?2 " +
            "and (s.date < current_date " +
            "or (s.date = current_date and s.time <= extract(hour from current_time)))", nativeQuery = true)
    Page<Schedule> getPageForDoctorInPastByType(Pageable pageable, int id, Integer status);

    @Query(value = "select s.* " +
            "from schedules s " +
            "join doctor_users u " +
            "on u.id = s.doctor_id " +
            "where u.id = ?1 " +
            "and s.status = ?2" +
            "and (case " +
            "when s.date = current_date then s.time <= extract(hour from current_time) " +
            "else s.date = ?3 " +
            "end)", nativeQuery = true)
    Page<Schedule> getPageForDoctorInPastByTypeAt(Pageable pageable, int id, Integer status, Date date);

    @Query(value = "select s.* " +
            "from schedules s " +
            "join doctor_users u " +
            "on u.id = s.doctor_id " +
            "where u.id = ?1 " +
            "and (s.date < current_date " +
            "or (s.date = current_date and s.time <= extract(hour from current_time)))", nativeQuery = true)
    Page<Schedule> getAllForDoctorInPast(Pageable pageable, int id);
}
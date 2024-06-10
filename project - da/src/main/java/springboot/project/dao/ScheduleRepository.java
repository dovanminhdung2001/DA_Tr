package springboot.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springboot.project.entity.Schedule;

import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {


    Schedule findByIdAndStatus(Integer id, Integer status);

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
            "and s.type = ?2 " +
            "and (s.date > current_date " +
            "or (s.date = current_date and s.time > extract(hour from current_time)))", nativeQuery = true)
    Page<Schedule> getAllForUserInFuture(Pageable pageable, int id, Integer type);

    @Query(value = "select s.* " +
            "from schedules s " +
            "join users u " +
            "on u.id = s.created_by " +
            "where u.id = ?1 " +
            "and s.type = ?2 " +
            "and (s.date = current_date + interval '1 day' " +
            "or s.date = current_date + interval '2 day' " +
            "or (s.date = current_date and s.time > extract(hour from current_time)))", nativeQuery = true)
    Page<Schedule> getAllForUserIn3NextDays(Pageable pageable, int id, Integer type);

    @Query(value = "select s.* " +
            "from schedules s " +
            "join users u " +
            "on u.id = s.created_by " +
            "where u.id = ?1 " +
            "and s.type = ?3 " +
            "and (case " +
            "when s.date = current_date then s.time > extract(hour from current_time) " +
            "else s.date = ?2 " +
            "end)", nativeQuery = true)
    Page<Schedule> getAllForUserAt(Pageable pageable, int id, Date date, Integer type);

    @Query(value = "select s.* " +
            "from schedules s " +
            "join users u " +
            "on u.id = s.created_by " +
            "where u.id = ?1 " +
            "and s.status = ?2 " +
            "and s.type = ?3 " +
            "and (s.date < current_date " +
            "or (s.date = current_date and s.time <= extract(hour from current_time)))", nativeQuery = true)
    Page<Schedule> getPageForUserInPastByType(Pageable pageable, int id, Integer status, Integer type);

    @Query(value = "select s.* " +
            "from schedules s " +
            "join users u " +
            "on u.id = s.created_by " +
            "where u.id = ?1 " +
            "and s.status = ?2 " +
            "and s.type = ?4 " +
            "and (case " +
            "when s.date = current_date then s.time <= extract(hour from current_time) " +
            "else s.date = ?3 " +
            "end)", nativeQuery = true)
    Page<Schedule> getPageForUserInPastByTypeAt(Pageable pageable, int id, Integer status, Date date, Integer type);

    @Query(value = "select s.* " +
            "from schedules s " +
            "join users u " +
            "on u.id = s.created_by " +
            "where u.id = ?1 " +
            "and s.type = ?2 " +
            "and (s.date < current_date " +
            "or (s.date = current_date and s.time <= extract(hour from current_time)))", nativeQuery = true)
    Page<Schedule> getAllForUserInPast(Pageable pageable, int id, Integer type);

    @Query(value = "select s.* " +
            "from schedules s " +
            "join doctor_users u " +
            "on u.id = s.doctor_id " +
            "where u.id = ?1 " +
            "and (s.date > current_date " +
            "or (s.date = current_date and s.time > extract(hour from current_time)))", nativeQuery = true)
    Page <Schedule> getAllForDoctorInFuture(Pageable pageable, Integer doctorId);

    Page <Schedule> findAllByDateBeforeAndTestResults_Status(Pageable pageable, Date date, Integer type);
    Page <Schedule> findAllByTestResults_Status(Pageable pageable, Integer type);

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

    Integer countAllByStatus(Integer status);

    List<Schedule> findAllByDateAndCreatedByAndStatusInAndType(Date date, Integer createdBy, List<Integer> status, Integer type);

    @Query(value = "SELECT DATE_FORMAT(s.date, '%b-%Y') AS month, SUM(s.examination_price) AS totalPrice " +
            "FROM schedules s " +
            "JOIN test_results tr ON s.id = tr.schedule_id " +
            "WHERE tr.id IS NOT NULL " +
            "AND s.date >= :startDate " +
            "GROUP BY DATE_FORMAT(s.date, '%b-%Y')", nativeQuery = true)
    List<Object[]> findMonthlyExaminationPriceSum(@Param("startDate") Date startDate);

    Page<Schedule> findAllByDoctorUser_IdAndAssignedToAndStatus(Pageable pageable, Integer doctorId, Integer employeeId, Integer status);
}
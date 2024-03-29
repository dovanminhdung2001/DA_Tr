package springboot.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springboot.project.entity.DoctorUser;
import springboot.project.entity.Specialization;
import springboot.project.entity.User;

import java.util.Date;
import java.util.List;

public interface DoctorUserRepository extends JpaRepository<DoctorUser, Integer> {
    DoctorUser findByUser(User user);
    DoctorUser findByUser_Id(int userId);
    Page<DoctorUser> findAllBySpecialization(Pageable pageable, Specialization specialization);

    @Query("SELECT DISTINCT du FROM DoctorUser du " +
            "JOIN FETCH du.doctorDates dd " +
            "WHERE dd.workingDate = :workingDate")
    Page<DoctorUser> findAllByWorkingDate(Pageable pageable, @Param("workingDate") Date workingDate);

    Page<DoctorUser> findByDoctorDatesWorkingDate(Pageable pageable, @Param("workingDate") Date workingDate);

    @Query("SELECT DISTINCT du FROM DoctorUser du " +
            "JOIN FETCH du.doctorDates dd JOIN FETCH du.specialization s " +
            "WHERE dd.workingDate = :workingDate and s.id = :specializationId")
    Page<DoctorUser> findAllByWorkingDateAndSpecialization(Pageable pageable,@Param("workingDate") Date workingDate,@Param("specializationId") Integer specializationId);
}
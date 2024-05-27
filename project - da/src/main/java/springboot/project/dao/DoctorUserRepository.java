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
    DoctorUser findByUser_Email(String email);
    DoctorUser findByUser_Phone(String phone);
    DoctorUser findByUser_Cccd(String cccd);
    DoctorUser findByUser_Id(int userId);
    Page<DoctorUser> findAllBySpecialization(Pageable pageable, Specialization specialization);


    Page<DoctorUser> findAllByDoctorDates_WorkingDateAndType(Pageable pageable, Date workingDate, Integer type);
    Page<DoctorUser> findAllByDoctorDates_WorkingDateAndTypeAndSpecialization_Id(Pageable pageable, Date workingDate, Integer type, Integer specializationId);

    Integer countAllByType(Integer type);

    Page<DoctorUser> findAllByUser_NameContainingIgnoreCase(Pageable pageable, String name);
    Page<DoctorUser> findAllByUser_NameContainingIgnoreCaseAndType(Pageable pageable, String name, Integer type);
    Page<DoctorUser> findAllByUser_NameContainingIgnoreCaseAndTypeAndDoctorDates_WorkingDate(Pageable pageable, String name, Integer type, Date workingDate);
}
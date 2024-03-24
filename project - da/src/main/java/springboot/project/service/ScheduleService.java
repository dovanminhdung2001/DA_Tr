package springboot.project.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.project.entity.Schedule;
import springboot.project.model.ScheduleDTO;
import springboot.project.model.RegisterDTO;

import java.util.Date;
import java.util.List;

public interface ScheduleService {

    Schedule booking (ScheduleDTO dto);
    Schedule unBooking (ScheduleDTO dto);


    Schedule addMakeAnAppointment(RegisterDTO registerDTO);

    void deleteMakeAnAppointment(int id);

    Page<ScheduleDTO> getListSchedule(Pageable pageable, int id);
    Page<ScheduleDTO> getAllByDoctor(Pageable pageable, int doctor_id);


    Page<Schedule> getAllForUserInFuture (Pageable pageable, Integer id, Integer recent, Date date);

    Page<Schedule> getAllForUserInPast(Pageable pageable, Integer status, Date date);

    Page<Schedule> getAllForDoctorInFuture(Pageable pageable);

    Page<Schedule> getAllForDoctorInPast(Pageable pageable, Integer status, Date date);
}

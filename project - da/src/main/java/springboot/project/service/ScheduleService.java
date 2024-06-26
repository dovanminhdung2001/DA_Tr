package springboot.project.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.project.entity.Schedule;
import springboot.project.model.ScheduleDTO;
import springboot.project.model.RegisterDTO;

import java.util.Date;
import java.util.List;

public interface ScheduleService {

    ScheduleDTO booking (ScheduleDTO dto);
    ScheduleDTO unBooking (ScheduleDTO dto);


    Schedule addMakeAnAppointment(RegisterDTO registerDTO);

    void deleteMakeAnAppointment(int id);

    Page<ScheduleDTO> getListSchedule(Pageable pageable, int id);
    Page<ScheduleDTO> getAllByDoctor(Pageable pageable, int doctor_id);


    Page<ScheduleDTO> getAllForUserInFuture (Pageable pageable, Integer id, Integer recent, Date date, Integer type);

    Page<ScheduleDTO> getAllForUserInPast(Pageable pageable, Integer status, Date date, Integer type);

    Page<ScheduleDTO> getAllForDoctorInFuture(Pageable pageable);

    Page<ScheduleDTO> getAllForDoctorInPast(Pageable pageable, Integer status, Date date, Integer type);

    List<Integer> scheduleDay(ScheduleDTO atClinic);

    Page<?> getAllUnassigned(Pageable pageable, Integer doctorId);

    Page<?> getAllAssigned(Pageable pageable, Integer employeeId);

    String assign(Integer employeeId, Integer scheduleId);

    String cancelAssign(Integer scheduleId);

    Page<?> getAllResultedScheduleForEmployee(Pageable pageable, Integer employeeId);

    Page<?> getAllResultedScheduleOfUser(Pageable pageable, String phone);
}

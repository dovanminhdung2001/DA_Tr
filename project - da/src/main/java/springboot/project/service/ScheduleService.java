package springboot.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.project.entity.Schedule;
import springboot.project.model.ScheduleDTO;
import springboot.project.model.RegisterDTO;

import java.util.List;

public interface ScheduleService {

    Schedule booking (ScheduleDTO dto);
    Schedule unBooking (ScheduleDTO dto);


    Schedule addMakeAnAppointment(RegisterDTO registerDTO);

    void deleteMakeAnAppointment(int id);

    Page<ScheduleDTO> getListSchedule(Pageable pageable, int id);
    Page<ScheduleDTO> getAllByDoctor(Pageable pageable, int doctor_id);
}

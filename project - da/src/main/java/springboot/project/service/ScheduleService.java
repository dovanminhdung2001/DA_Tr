package springboot.project.service;

import springboot.project.entity.Schedule;
import springboot.project.model.ScheduleDTO;
import springboot.project.model.RegisterDTO;

import java.util.List;

public interface ScheduleService {

    Schedule booking (ScheduleDTO dto);
    Schedule unBooking (Integer id);


    Schedule addMakeAnAppointment(RegisterDTO registerDTO);

    void deleteMakeAnAppointment(int id);

    List<ScheduleDTO> getListSchedule(int id);

}

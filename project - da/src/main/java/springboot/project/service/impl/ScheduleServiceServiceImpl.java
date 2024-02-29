package springboot.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.project.dao.DoctorUserRepository;
import springboot.project.dao.ScheduleRepository;
import springboot.project.entity.DoctorUser;
import springboot.project.entity.Schedule;
import springboot.project.model.ScheduleDTO;
import springboot.project.model.RegisterDTO;
import springboot.project.service.PatientService;
import springboot.project.service.ScheduleService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ScheduleServiceServiceImpl implements ScheduleService {
    @Autowired
    DoctorUserRepository doctorUserRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PatientService patientService;


    @Override
    public Schedule addMakeAnAppointment(RegisterDTO registerDTO) {
        Schedule schedule = new Schedule();
        DoctorUser doctorUser = doctorUserRepository.findById(registerDTO.getDoctorUser().getId()).orElse(null);
        schedule.setDoctorUser(doctorUser);

        schedule.setPathological(registerDTO.getPathological());
        schedule.setType(registerDTO.getType());

        scheduleRepository.save(schedule);
        return schedule;
    }

    @Override
    public void deleteMakeAnAppointment(int id) {
        Schedule schedule = scheduleRepository.findById(id).orElse(null);
        scheduleRepository.delete(schedule);
    }

    @Override
    public List<ScheduleDTO> getListSchedule(int id) {
        List<Schedule> schedules = scheduleRepository.getById(id);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOS.add(convert(schedule));
        }
        return scheduleDTOS;
    }

    private ScheduleDTO convert(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDoctorUser(schedule.getDoctorUser());
        scheduleDTO.setPatient(schedule.getPatient());
        scheduleDTO.setType(schedule.getType());
        return scheduleDTO;
    }
}

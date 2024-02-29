package springboot.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springboot.project.dao.ScheduleRepository;
import springboot.project.entity.Patient;
import springboot.project.entity.Schedule;
import springboot.project.model.MessageResponseDTO;
import springboot.project.model.ScheduleDTO;
import springboot.project.model.RegisterDTO;
import springboot.project.service.PatientService;
import springboot.project.service.ScheduleService;

@RestController
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PatientService patientService;

    @Autowired
    ScheduleRepository scheduleRepository;

    // dat lich kham
    @PostMapping("/api/user/booking")
    public ResponseEntity<?> addMakeAnAppointment(@RequestBody RegisterDTO registerDTO) {
        try {
            Patient patient = patientService.addPatient(registerDTO);
            Schedule schedule = scheduleService.addMakeAnAppointment(registerDTO);
            schedule.setPatient(patient);
            scheduleRepository.save(schedule);
            return ResponseEntity.ok(schedule);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Error"));
        }
    }

    // huy lich kham
    @DeleteMapping("/api/user/unbooking")
    public void deleteMakeAnAppointment(@RequestBody ScheduleDTO scheduleDTO) {
        scheduleService.deleteMakeAnAppointment(scheduleDTO.getId());
    }
}

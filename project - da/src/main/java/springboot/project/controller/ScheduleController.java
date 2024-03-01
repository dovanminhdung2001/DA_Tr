package springboot.project.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.project.dao.ScheduleRepository;
import springboot.project.entity.Patient;
import springboot.project.entity.Schedule;
import springboot.project.model.MessageResponseDTO;
import springboot.project.model.ScheduleDTO;
import springboot.project.model.RegisterDTO;
import springboot.project.service.PatientService;
import springboot.project.service.ScheduleService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PatientService patientService;

    @Autowired
    ScheduleRepository scheduleRepository;

    // dat lich kham
    @PostMapping("/booking")
    public ResponseEntity<?> booking(@RequestBody ScheduleDTO dto) {
        return ResponseEntity.ok(scheduleService.booking(dto));
    }

    // huy lich kham
    @DeleteMapping("/unbooking")
    public ResponseEntity<?> unbooking(@RequestParam Integer id) {
        return ResponseEntity.ok(new MessageResponseDTO("ok"));
    }
}

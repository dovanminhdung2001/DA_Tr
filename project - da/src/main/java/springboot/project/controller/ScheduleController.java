package springboot.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.project.dao.ScheduleRepository;
import springboot.project.model.MessageResponseDTO;
import springboot.project.model.ScheduleDTO;
import springboot.project.service.PatientService;
import springboot.project.service.ScheduleService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user/schedule")
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
        try {
            return ResponseEntity.ok(scheduleService.booking(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage()));
        }
    }

    // huy lich kham
    @DeleteMapping("/unbooking")
    public ResponseEntity<?> unbooking(@RequestBody ScheduleDTO dto) {
        try {
            scheduleService.unBooking(dto);
            return ResponseEntity.ok(new MessageResponseDTO("ok"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage()));
        }
    }
}

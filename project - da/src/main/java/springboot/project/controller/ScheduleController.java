package springboot.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springboot.project.dao.DoctorDateRepository;
import springboot.project.dao.DoctorUserRepository;
import springboot.project.dao.ScheduleRepository;
import springboot.project.model.MessageResponseDTO;
import springboot.project.model.ScheduleDTO;
import springboot.project.model.UserPrincipal;
import springboot.project.service.PatientService;
import springboot.project.service.ScheduleService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PatientService patientService;

    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    DoctorUserRepository doctorUserRepository;

    @GetMapping("/doctor/schedule/demo")
    public ResponseEntity<?> demo(Pageable pageable) {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return ResponseEntity.ok("demo ok");
    }

    @GetMapping("/user/schedule/history")
    public ResponseEntity<?> getListSchedule(Pageable pageable) {
        try {
            UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            Page<ScheduleDTO> scheduleDTOS = scheduleService.getListSchedule(pageable, currentUser.getId());
            return ResponseEntity.ok(scheduleDTOS);
        } catch (Exception exp) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Error"));
        }
    }


    // dat lich kham
    @PostMapping("/user/schedule/booking")
    public ResponseEntity<?> booking(@RequestBody ScheduleDTO dto) {
        try {
            return ResponseEntity.ok(scheduleService.booking(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage()));
        }
    }

    // huy lich kham
    @DeleteMapping("/user/schedule/unbooking")
    public ResponseEntity<?> unbooking(@RequestBody ScheduleDTO dto) {
        try {
            scheduleService.unBooking(dto);
            return ResponseEntity.ok(new MessageResponseDTO("ok"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage()));
        }
    }
}

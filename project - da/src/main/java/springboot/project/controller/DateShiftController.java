package springboot.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springboot.project.dao.DoctorUserRepository;
import springboot.project.entity.DateShift;
import springboot.project.model.DateShiftDTO;
import springboot.project.model.ScheduleDTO;
import springboot.project.model.UserPrincipal;
import springboot.project.service.DateShiftService;
import springboot.project.service.ScheduleService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class DateShiftController {
    @Autowired
    private DateShiftService dateShiftService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private DoctorUserRepository doctorUserRepository;
    @GetMapping("/doctor/dateShift/list")
    public ResponseEntity<?> page (Pageable pageable) {
        return ResponseEntity.ok(dateShiftService.page(pageable));
    }

    @PostMapping("/doctor/dateShift/create")
    public ResponseEntity<?> create(@RequestBody  DateShiftDTO dto) {
        DateShift dateShift = dateShiftService.create(dto);

        return ResponseEntity.ok(dateShift);
    }

    @GetMapping("/doctor/schedule/list")
    public ResponseEntity<?> demo(Pageable pageable) {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Page<ScheduleDTO> scheduleDTOS = scheduleService.getAllByDoctor(pageable, doctorUserRepository.findByUser_Id(currentUser.getId()).getId());
            return ResponseEntity.ok(scheduleDTOS);
    }
}


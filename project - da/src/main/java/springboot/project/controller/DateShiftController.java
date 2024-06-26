package springboot.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springboot.project.dao.DoctorUserRepository;
import springboot.project.entity.DateShift;
import springboot.project.model.DateShiftDTO;
import springboot.project.model.MessageResponseDTO;
import springboot.project.model.ScheduleDTO;
import springboot.project.model.UserPrincipal;
import springboot.project.service.DateShiftService;
import springboot.project.service.ScheduleService;

import java.util.Date;


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

    @PostMapping("/doctor/dateShift/createAES")
    public ResponseEntity<?> createAES(@RequestBody String reqAES) {
        return ResponseEntity.ok(dateShiftService.createAES(reqAES));
    }

    @DeleteMapping("/doctor/dataShift/delete")
    public ResponseEntity<?> delete(@RequestParam int dateShiftId) {
        return ResponseEntity.ok(dateShiftService.delete(dateShiftId));
    }

    @GetMapping("/doctor/schedule/list")
    public ResponseEntity<?> demo(Pageable pageable) {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Page<ScheduleDTO> scheduleDTOS = scheduleService.getAllByDoctor(pageable, doctorUserRepository.findByUser_Id(currentUser.getId()).getId());
            return ResponseEntity.ok(scheduleDTOS);
    }


    @GetMapping("/user/schedule/future")
    public ResponseEntity<?> futureForUser (
            Pageable pageable,
            @RequestParam(required = false) Integer recent,
            @RequestParam Integer type,
            @RequestParam(required = false) @DateTimeFormat(pattern="dd/MM/yyyy") Date date
            ) {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Page<ScheduleDTO> schedule = scheduleService.getAllForUserInFuture(pageable, currentUser.getId(), recent, date, type);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping({"/user/schedule/day", "/employee/schedule/day"})
    public ResponseEntity<?> scheduleToday(
            @RequestBody ScheduleDTO scheduleDTO
    ) {
        return ResponseEntity.ok(new MessageResponseDTO("",scheduleService.scheduleDay(scheduleDTO)));
    }

    @GetMapping("/user/schedule/past")
    public ResponseEntity<?> pastUser (
            Pageable pageable,
            @RequestParam(required = false) Integer status,
            @RequestParam Integer type,
            @RequestParam(required = false) @DateTimeFormat(pattern="dd/MM/yyyy") Date date
    ) {
        return ResponseEntity.ok(scheduleService.getAllForUserInPast(pageable, status, date, type));
    }

    @GetMapping("/doctor/schedule/future")
    public ResponseEntity<?> futureDoctor (Pageable pageable) {
        return ResponseEntity.ok(scheduleService.getAllForDoctorInFuture(pageable));
    }

    @GetMapping("/doctor/schedule/past")
    public ResponseEntity<?> pastDoctor (
            Pageable pageable,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) @DateTimeFormat(pattern="dd/MM/yyyy") Date date,
            @RequestParam(required = false) Integer type
    ) {
        return ResponseEntity.ok(scheduleService.getAllForDoctorInPast(pageable, status, date, type));
    }

    @GetMapping("/doctor/schedule/list-unassigned")
    public ResponseEntity<?> listUnassigned (Pageable pageable, @RequestParam Integer doctorId) {
        return ResponseEntity.ok(scheduleService.getAllUnassigned(pageable, doctorId));
    }

    @GetMapping("/doctor/schedule/user-list-resulted")
    public ResponseEntity<?> listResultedScheduleOfUser(Pageable pageable, @RequestParam String phone) {
        return ResponseEntity.ok(scheduleService.getAllResultedScheduleOfUser(pageable, phone));
    }

    @GetMapping("/employee/schedule/list-assigned")
    public ResponseEntity<?> listAssigned (Pageable pageable, @RequestParam Integer employeeId) {
        return ResponseEntity.ok(scheduleService.getAllAssigned(pageable, employeeId));
    }

    @GetMapping("/employee/schedule/list-resulted-schedule")
    public ResponseEntity<?> listResultedSchedule (Pageable pageable, @RequestParam Integer employeeId) {
        return ResponseEntity.ok(scheduleService.getAllResultedScheduleForEmployee(pageable, employeeId));
    }

    @PostMapping("/doctor/schedule/assign")
    public ResponseEntity<?> assign (@RequestParam Integer employeeId, @RequestParam Integer scheduleId) {
        return ResponseEntity.ok(new MessageResponseDTO(scheduleService.assign(employeeId, scheduleId)));
    }

    @DeleteMapping("/employee/schedule/cancel-assigned")
    public ResponseEntity<?> cancelAssign(@RequestParam Integer scheduleId) {
        return ResponseEntity.ok(new MessageResponseDTO(scheduleService.cancelAssign(scheduleId)));
    }
}


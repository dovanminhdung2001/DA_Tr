package springboot.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springboot.project.entity.Patient;
import springboot.project.entity.Schedule;
import springboot.project.model.*;
import springboot.project.service.DoctorUserService;
import springboot.project.service.PatientService;
import springboot.project.service.ScheduleService;
import springboot.project.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {
    @Autowired
    DoctorUserService doctorUserService;

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    UserService userService;

    @Autowired
    PatientService patientService;

    // xem danh sach bac si
    @GetMapping("/api/list_doctor")
    public ResponseEntity<?> getListDoctor() {
        try {
            List<DoctorUserDTO> doctorUserDTOS = doctorUserService.getListDoctorUser();
            if (doctorUserDTOS.isEmpty()) {
                return ResponseEntity.ok("Doctor not found");
            }
            return ResponseEntity.ok(doctorUserDTOS);
        } catch (Exception exp) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Error"));
        }
    }

    // xem lich su dat lich
//    @GetMapping("/api/user/history")
//    public ResponseEntity<?> getListSchedule() {
//        try {
//            UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
//                    .getPrincipal();
//            List<ScheduleDTO> scheduleDTOS = scheduleService.getListSchedule(currentUser.getId());
//            return ResponseEntity.ok(scheduleDTOS);
//        } catch (Exception exp) {
//            return ResponseEntity.badRequest().body(new MessageResponseDTO("Error"));
//        }
//    }

    // xem hồ sơ cá nhân
    @GetMapping("/api/user/infomation")
    public ResponseEntity<?> getInfo() {
        try {
            UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            PatientDTO patientDTO = new PatientDTO();

            patientDTO.setId(currentUser.getId());
            patientDTO.setName(currentUser.getName());
            patientDTO.setAddress(currentUser.getAddress());
            patientDTO.setCccd(currentUser.getCccd());
            patientDTO.setBirthDate(currentUser.getBirthDate());
            patientDTO.setDescription(currentUser.getHistoryBreath());
            return ResponseEntity.ok(patientDTO);
        } catch (Exception exp) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Error"));
        }
    }

    @GetMapping("/api/admin/user/list")
    public ResponseEntity<?> page(Pageable pageable) {
        return ResponseEntity.ok(userService.page(pageable));
    }

    @GetMapping("/api/admin/employee/list")
    public ResponseEntity<?> pageEmployee (Pageable pageable) {
        return ResponseEntity.ok(userService.pageEmployee(pageable));
    }

    @GetMapping("/api/doctor/employee/list")
    public ResponseEntity<?> pageEmployeeForDoctor(Pageable pageable, @RequestParam(required = false) String name){
        return ResponseEntity.ok(userService.pageEmployeeForDoctor(pageable, name));
    }

    @PostMapping("/api/admin/employee/create")
    public ResponseEntity<?> createEmployee(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createEmployee(userDTO));
    }

    @PostMapping("/api/admin/user/create")
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.create(userDTO));
    }

    @PostMapping("/api/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            return ResponseEntity.ok(new MessageResponseDTO(userService.forgotPassword(email)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage(), e));
        }
    }

    @GetMapping("/api/admin/user/get")
    public ResponseEntity<?> get(@RequestParam Integer id) {
        return ResponseEntity.ok(userService.findOnlyUser(id));
    }

    @PutMapping("/api/admin/user/update")
    public ResponseEntity<?> update(@RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.update(dto));
    }

    @DeleteMapping("/api/admin/user/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Integer userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    @DeleteMapping("/api/admin/employee/delete")
    public ResponseEntity<?> deleteEmployee(@RequestParam Integer employeeId) {
        return ResponseEntity.ok(userService.deleteEmployee(employeeId));
    }
}

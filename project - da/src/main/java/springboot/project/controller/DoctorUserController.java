package springboot.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.project.entity.DoctorUser;
import springboot.project.entity.Role;
import springboot.project.entity.User;
import springboot.project.model.DoctorUserDTO;
import springboot.project.service.ClinicService;
import springboot.project.service.DoctorUserService;
import springboot.project.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class DoctorUserController {
    @Autowired
    private DoctorUserService doctorUserService;
    @Autowired
    private ClinicService clinicService;
    @Autowired
    private UserService userService;
    @GetMapping({"/user/doctor/list", "/admin/doctor/list"})
    public ResponseEntity<?> page(Pageable pageable) {
        return ResponseEntity.ok(doctorUserService.page(pageable));
    }

    @GetMapping("/user/doctor/find")
    public ResponseEntity<?> find (Pageable pageable, @RequestBody DoctorUserDTO dto) {
        return ResponseEntity.ok(doctorUserService.find(pageable, dto));
    }

    @GetMapping("/user/doctor/get")
    public ResponseEntity<?> get (Pageable pageable, @RequestParam Integer id ) {
        return ResponseEntity.ok(doctorUserService.getById(id));
    }

    @PostMapping("/admin/doctor/create")
    public ResponseEntity<?> create(@RequestBody DoctorUserDTO dto) {
        return ResponseEntity.ok(doctorUserService.create(dto));
    }
}

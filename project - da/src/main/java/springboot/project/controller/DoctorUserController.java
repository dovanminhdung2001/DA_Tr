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
import springboot.project.model.MessageResponseDTO;
import springboot.project.service.ClinicService;
import springboot.project.service.DoctorUserService;
import springboot.project.service.UserService;

//@CrossOrigin(origins = "*")
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
    public ResponseEntity<?> page(Pageable pageable, @RequestParam(required = false) String name) {
        return ResponseEntity.ok(doctorUserService.page(pageable, name));
    }

    @GetMapping("/user/doctor/find")
    public ResponseEntity<?> find (Pageable pageable, @RequestBody DoctorUserDTO dto) {
        return ResponseEntity.ok(doctorUserService.find(pageable, dto));
    }

    @GetMapping("/admin/index2/get")
    public ResponseEntity<?> index2 (Pageable pageable) {
        return ResponseEntity.ok(doctorUserService.index2(pageable));
    }

    @GetMapping({"/user/doctor/get", "/admin/doctor/get"})
    public ResponseEntity<?> get (@RequestParam Integer id ) {
        return ResponseEntity.ok(doctorUserService.getById(id));
    }

    @PostMapping("/admin/doctor/create")
    public ResponseEntity<?> create(@RequestBody DoctorUserDTO dto) {
        return ResponseEntity.ok(doctorUserService.create(dto));
    }

    @PutMapping("/admin/doctor/update")
    public ResponseEntity<?> update(@RequestBody DoctorUserDTO dto) {
        try {
            return ResponseEntity.ok(doctorUserService.update(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage(), e));
        }
    }
}

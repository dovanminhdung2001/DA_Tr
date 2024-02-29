package springboot.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.project.entity.DoctorUser;
import springboot.project.entity.User;
import springboot.project.model.DoctorUserDTO;
import springboot.project.service.ClinicService;
import springboot.project.service.DoctorUserService;

@RestController
@RequestMapping("/api/doctor")
public class DoctorUserController {
    @Autowired
    private DoctorUserService doctorUserService;
    @Autowired
    private ClinicService clinicService;

    @GetMapping("/list")
    public ResponseEntity<?> page(@RequestParam Pageable pageable) {
        return ResponseEntity.ok(doctorUserService.page(pageable));
    }

//    @PostMapping("/create")
//    public ResponseEntity<?> create(@RequestParam DoctorUserDTO dto) {
//        User user = new User()
//
//        DoctorUser doctorUser = new DoctorUser(
//
//        );
//    }
}

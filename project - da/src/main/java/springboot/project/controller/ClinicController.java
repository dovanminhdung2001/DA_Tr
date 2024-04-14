package springboot.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springboot.project.entity.Clinic;
import springboot.project.model.ClinicDTO;
import springboot.project.model.MessageResponseDTO;
import springboot.project.service.ClinicService;

@Controller
@RequestMapping("/api/admin/clinic")
public class ClinicController {
    @Autowired
    private ClinicService clinicService;

    @GetMapping("/list")
    ResponseEntity<?> page (Pageable pageable) {
        return ResponseEntity.ok(clinicService.page(pageable));
    }

    @GetMapping("/get")
    ResponseEntity<?> find (@RequestParam Integer id) {
        return ResponseEntity.ok(clinicService.find(id));
    }

    @PostMapping("/create")
    ResponseEntity<?> create (@RequestBody ClinicDTO dto) {
        try {
            return ResponseEntity.ok(clinicService.create(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage()));
        }
    }

    @PutMapping("/update")
    ResponseEntity<?> update (@RequestBody ClinicDTO dto) {
        try {
            return ResponseEntity.ok(clinicService.update(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage()));
        }
    }
}

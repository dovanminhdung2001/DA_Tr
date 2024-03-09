package springboot.project.controller;

import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.project.model.DoctorDateDTO;
import springboot.project.service.DoctorDateService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/docter/docterDate")
public class DoctorDateController {
    @Autowired
    private DoctorDateService doctorDateService;

    @GetMapping("/list")
    public ResponseEntity<?> page (Pageable pageable) {
        return ResponseEntity.ok(doctorDateService.page(pageable));
    }

    @GetMapping("/find")
    public ResponseEntity<?> filter (
            @RequestParam(required = false) Integer id
    ) {
        return ResponseEntity.ok(doctorDateService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DoctorDateDTO dto) {
        return ResponseEntity.ok(doctorDateService.create(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody DoctorDateDTO dto) {
        return ResponseEntity.ok(doctorDateService.update(dto));
    }
}

package springboot.project.controller;

import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.project.model.DoctorDateDTO;
import springboot.project.model.MessageResponseDTO;
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
        try {
            return ResponseEntity.ok(doctorDateService.create(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage(), e));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody DoctorDateDTO dto) {
        try {
            return ResponseEntity.ok(doctorDateService.update(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage(), e));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(new MessageResponseDTO(doctorDateService.delete(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage(), e));
        }
    }
}

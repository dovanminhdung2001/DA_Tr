package springboot.project.controller;

import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.project.model.DoctorDateDTO;
import springboot.project.model.MessageResponseDTO;
import springboot.project.service.DoctorDateService;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class DoctorDateController {
    @Autowired
    private DoctorDateService doctorDateService;

    @GetMapping("/doctor/doctorDate/list")
    public ResponseEntity<?> page (Pageable pageable) {
        return ResponseEntity.ok(doctorDateService.page(pageable));
    }

    @GetMapping({"/doctor/doctorDate/find"})
    public ResponseEntity<?> filter (
            Pageable pageable,
            @RequestParam(required = false) Integer id,
            @RequestParam @DateTimeFormat(pattern="dd/MM/yyyy") Date date
    ) {
        if (date != null)
            return ResponseEntity.ok(doctorDateService.findByDate(pageable, date));

        return ResponseEntity.ok(doctorDateService.findById(id));
    }

    @PostMapping("/doctor/doctorDate/create")
    public ResponseEntity<?> create(@RequestBody DoctorDateDTO dto) {
//        try {
            return ResponseEntity.ok(doctorDateService.create(dto));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage(), e));
//        }
    }

    @PutMapping("/doctor/doctorDate/update")
    public ResponseEntity<?> update(@RequestBody DoctorDateDTO dto) {
        try {
            return ResponseEntity.ok(doctorDateService.update(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage(), e));
        }
    }

    @DeleteMapping("/doctor/doctorDate/delete")
    public ResponseEntity<?> delete(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(new MessageResponseDTO(doctorDateService.delete(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage(), e));
        }
    }
}

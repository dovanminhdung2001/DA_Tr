package springboot.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.project.entity.Specialization;
import springboot.project.model.MessageResponseDTO;
import springboot.project.model.SpecializationDTO;
import springboot.project.service.SpecializationService;

@RestController
@RequestMapping("/api/admin/specialization")
public class SpecializationController {
    @Autowired
    private SpecializationService specializationService;

    @GetMapping("/list")
    public ResponseEntity<?> page (Pageable pageable) {
        return ResponseEntity.ok(specializationService.page(pageable));
    }

    @GetMapping("/active")
    public ResponseEntity<?> active (Pageable pageable) {
        return ResponseEntity.ok(specializationService.findAllActive(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody SpecializationDTO dto) {
        Specialization specialization = specializationService.create(dto);

        return  specialization == null
                ? ResponseEntity.badRequest().body(new MessageResponseDTO("Id not null"))
                : ResponseEntity.ok(specialization);
    }

    @PutMapping("/update")
    public ResponseEntity<?> udpate (@RequestBody SpecializationDTO dto) {
        Specialization specialization = specializationService.update(dto);

        return  specialization == null
                ? ResponseEntity.badRequest().body(new MessageResponseDTO("Id is null"))
                : ResponseEntity.ok(specialization);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete (@RequestParam Integer id) {

        return specializationService.delete(id)
                ? ResponseEntity.ok(new MessageResponseDTO("SUCCESS"))
                : ResponseEntity.badRequest().body(new MessageResponseDTO("Not exist or already deleted"));
    }
}

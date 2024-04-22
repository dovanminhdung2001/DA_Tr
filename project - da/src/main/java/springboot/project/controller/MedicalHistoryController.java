package springboot.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.project.model.MedicalHistoryDTO;
import springboot.project.service.MedicalHistoryService;

@RestController
@RequestMapping("/api")
public class MedicalHistoryController {
    @Autowired
    private MedicalHistoryService medicalHistoryService;
    @PostMapping("/user/medical-history/create")
    public ResponseEntity<?> create (@RequestBody MedicalHistoryDTO dto) {
        return ResponseEntity.ok(medicalHistoryService.create(dto));
    }

    @GetMapping("/user/medical-history/get")
    public ResponseEntity<?> get (
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer userId
    ) {
        return  id != null
                ? ResponseEntity.ok(medicalHistoryService.findById(id))
                : ResponseEntity.ok(medicalHistoryService.findByUser_Id(userId));
    }

    @PutMapping("/user/medical-history/update")
    public ResponseEntity<?> update (@RequestBody MedicalHistoryDTO dto) {
        return ResponseEntity.ok(medicalHistoryService.update(dto));
    }
}

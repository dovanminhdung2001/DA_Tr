package springboot.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.project.entity.MedicalHistory;
import springboot.project.model.MedicalHistoryDTO;
import springboot.project.model.MessageResponseDTO;
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
        MedicalHistory response =  (id != null)
                ? medicalHistoryService.findById(id)
                : medicalHistoryService.findByUser_Id(userId);

        return response == null
                ? ResponseEntity.ok(new MessageResponseDTO("User not update medical history yet"))
                : ResponseEntity.ok(response);
    }

    @GetMapping("/doctor/medical-history/get")
    public ResponseEntity<?> getForDoctor(@RequestParam Integer userId) {
        MedicalHistory res = medicalHistoryService.findByUser_Id(userId);

        return res == null
                ? ResponseEntity.ok(new MessageResponseDTO("User not update medical history yet"))
                : ResponseEntity.ok(res);
    }

    @PutMapping("/user/medical-history/update")
    public ResponseEntity<?> update (@RequestBody MedicalHistoryDTO dto) {
        return ResponseEntity.ok(medicalHistoryService.update(dto));
    }
}

package springboot.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.project.entity.DateShift;
import springboot.project.model.DateShiftDTO;
import springboot.project.service.DateShiftService;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/doctor/dateShift")
public class DateShiftController {
    @Autowired
    private DateShiftService dateShiftService;

    @GetMapping("/list")
    public ResponseEntity<?> page (Pageable pageable) {
        return ResponseEntity.ok(dateShiftService.page(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody  DateShiftDTO dto) {
        DateShift dateShift = dateShiftService.create(dto);

        return ResponseEntity.ok(dateShift);
    }
}


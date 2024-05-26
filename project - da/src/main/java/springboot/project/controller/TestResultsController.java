package springboot.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springboot.project.entity.TestResults;
import springboot.project.model.MessageResponseDTO;
import springboot.project.model.TestResultsDTO;
import springboot.project.model.UserPrincipal;
import springboot.project.service.TestResultsService;

import java.util.List;

@RestController
public class TestResultsController {
    @Autowired
    TestResultsService testResultsService;

    // danh sach lich su kham
    @GetMapping("/api/user/listResults")
    public ResponseEntity<?> getListResults() {
        try {
            UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<TestResultsDTO> testResultsDTO = testResultsService.getListResults(currentUser.getId());
            return ResponseEntity.ok(testResultsDTO);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Error"));
        }
    }

    // xem kq kham
    @GetMapping("/api/user/view")
    public ResponseEntity<?> viewResult(@RequestBody TestResultsDTO testResultsDTO) {
        try {
            TestResults testResults = testResultsService.viewResult(testResultsDTO.getId());

            return ResponseEntity.ok(testResults);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Error"));
        }
    }

    @PostMapping("/api/employee/add-testResult")
    public ResponseEntity<?> addResultEmployee(@RequestBody TestResultsDTO testResultsDTO) {
        try {
            TestResults testResults = testResultsService.addResultEmployee(testResultsDTO);

            return ResponseEntity.ok(testResults);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Error"));
        }
    }

    // thêm kq khám
    @PostMapping("/api/doctor/add-testResult")
    public ResponseEntity<?> addResultDoctor(@RequestBody TestResultsDTO testResultsDTO) {
        try {
            TestResults testResults = testResultsService.addResultDoctor(testResultsDTO);

            return ResponseEntity.ok(testResults);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Error"));
        }
    }
}

package springboot.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springboot.project.model.BlogDTO;
import springboot.project.service.BlogService;

@Controller
@RequestMapping("/api/doctor/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/list")
    ResponseEntity<?> list (
        Pageable pageable,
        @RequestParam(required = false) Integer blogId,
        @RequestParam(required = false) Integer doctorId
    ) {
        if (blogId != null)
            return ResponseEntity.ok(blogService.findById(blogId));

        if (doctorId != null)
            return ResponseEntity.ok(blogService.findAllByDoctorId(pageable, doctorId));

        return ResponseEntity.ok(blogService.findAll(pageable));
    }

    @PostMapping("/create")
    ResponseEntity<?> create (@RequestBody BlogDTO blogDTO) {
        return ResponseEntity.ok(blogService.create(blogDTO));
    }

    @PutMapping("/update")
    ResponseEntity<?> update (@RequestBody BlogDTO blogDTO) {
        return ResponseEntity.ok(blogService.update(blogDTO));
    }
}

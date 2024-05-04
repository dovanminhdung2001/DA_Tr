package springboot.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springboot.project.model.BlogDTO;
import springboot.project.service.BlogService;

@Controller
@RequestMapping("/api")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping({"/doctor/blog/list", "/admin/blog/list"})
    ResponseEntity<?> list (
        Pageable pageable,
        @RequestParam(required = false) Integer blogId,
        @RequestParam(required = false) Integer doctorId,
        @RequestParam(required = false) Boolean isActive
    ) {
        if (blogId != null)
            return ResponseEntity.ok(blogService.findById(blogId));

        if (doctorId != null)
            return ResponseEntity.ok(blogService.findAllByDoctorId(pageable, doctorId, isActive));

        return ResponseEntity.ok(blogService.findAll(pageable, isActive));
    }

    @GetMapping("/admin/blog/get")
    ResponseEntity<?> get (@RequestParam Integer id) {
        return ResponseEntity.ok(blogService.findById(id));
    }
    @PostMapping("/doctor/blog/create")
    ResponseEntity<?> create (@RequestBody BlogDTO blogDTO) {
        return ResponseEntity.ok(blogService.create(blogDTO));
    }

    @PutMapping("/doctor/blog/update")
    ResponseEntity<?> update (@RequestBody BlogDTO blogDTO) {
        return ResponseEntity.ok(blogService.update(blogDTO));
    }

    @DeleteMapping("/admin/blog/delete")
    ResponseEntity<?> delete(@RequestParam Integer id, @RequestParam Boolean status) {
        return ResponseEntity.ok(blogService.delete(id, status));
    }
}

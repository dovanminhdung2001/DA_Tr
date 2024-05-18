package springboot.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.project.entity.Device;
import springboot.project.service.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotifyController {
    private  final UserService userService;

    @PostMapping({"/user/notify/register", "/doctor/notify/register"})
    public ResponseEntity<?> register (@RequestBody Device device) {
        return ResponseEntity.ok(userService.saveDevice(device));
    }
}

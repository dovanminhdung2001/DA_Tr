package springboot.project.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.project.model.MessageDTO;
import springboot.project.service.MessageService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @CrossOrigin
    @GetMapping({"/user/message", "/doctor/message"})
    public ResponseEntity<?> getMessages(Pageable pageable, @PathParam("room") String room) {
        return ResponseEntity.ok(messageService.findAllByRoom(pageable, room));
    }

    @GetMapping({"/user/message/recent", "/doctor/message/recent"})
    public ResponseEntity<?> recent(Pageable pageable) {
        return ResponseEntity.ok(messageService.findAllRecent(pageable));
    }

    @PostMapping("/message/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDTO dto) throws FirebaseMessagingException {
        return ResponseEntity.ok(messageService.saveMessage(dto));
    }
}

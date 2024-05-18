package springboot.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "messages", indexes = {
        @Index(name = "idx_room", columnList = "room")
})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sender_id", nullable = false)
    private Long senderId;
    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;
    private String room;
    @Column(name = "content", columnDefinition = "LONGTEXT", nullable = false)
    private String content;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT+7")
    @Column(name = "created_date", nullable = false)
    private Date createdDate;
}

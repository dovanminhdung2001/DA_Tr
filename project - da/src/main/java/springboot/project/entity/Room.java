package springboot.project.entity;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique=true)
    private String room;
    private Integer userId;
    private Integer doctorId;
    @OneToOne
    @JoinColumn(name = "last_message_id")
    private Message lastMessage;
}

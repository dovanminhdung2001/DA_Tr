package springboot.project.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String deviceId;
    @Column(nullable = false)
    private String firebaseToken;
}

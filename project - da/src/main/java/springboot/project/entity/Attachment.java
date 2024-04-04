package springboot.project.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String type;
    private String title;
    @Column(columnDefinition = "LONGTEXT")
    private String url;
}

package springboot.project.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Table
@Entity
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private Integer doctorId;
    private String doctorName;
    @Column(columnDefinition = "longtext")
    private String title;
    @Column(name="content", columnDefinition="LONGTEXT")
    private String content;
    private String specializationIdList;
    private Integer view;
    private Integer share;
    private Date createAt;
    private Date updateAt;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Attachment> attachments;
    private Boolean isActive;

    public Blog(Integer doctorId, String doctorName, String content, String specializationIdList, Integer view, Integer share, Date createAt, List<Attachment> attachments) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.content = content;
        this.specializationIdList = specializationIdList;
        this.view = view;
        this.share = share;
        this.createAt = createAt;
        this.attachments = attachments;
        this.isActive = true;
    }
}

package springboot.project.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import springboot.project.entity.Attachment;

import java.util.Date;
import java.util.List;

@Data
public class BlogDTO {
    private int id;
    private Integer doctorId;
    private String doctorName;
    private String title;
    private String content;
    private String specializationIdList;
    private Integer view;
    private Integer share;
    private Date createAt;
    private List<Attachment> attachments;
    private Boolean isActive;
}

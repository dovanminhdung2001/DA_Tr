package springboot.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import springboot.project.model.SpecializationDTO;

import java.util.Date;

@Entity
@Table(name = "specializations") // chuyên môn
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;


    @Column(name = "number_search")
    private Integer numberSearch;

    @Column(name = "createdAt")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreatedDate
    private Date createdAt;

    @Column(name = "updatedAt")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreatedDate
    private Date updatedAt;

    @Column(name = "deletedAt")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreatedDate
    private Date deletedAt;

    private Boolean active;

    public Specialization() {
    }

    public Specialization(String name, String description, String image, int numberSearch,
                          Date createdAt, Date updatedAt, Date deletedAt) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.numberSearch = numberSearch;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.active = true;
    }

    public Specialization(SpecializationDTO dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getNumberSearch() {
        return numberSearch;
    }

    public void setNumberSearch(Integer numberSearch) {
        this.numberSearch = numberSearch;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

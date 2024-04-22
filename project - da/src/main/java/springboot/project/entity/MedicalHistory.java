package springboot.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;

@Data
@Entity
@Table(name = "medical_history")
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;
    private Integer age;
    @Column(columnDefinition = "varchar(500)")
    private String result;
    @Comment("Bệnh tiểu đường")
    private Boolean isDiabetes;
    @Comment("Bệnh tim mạch")
    private Boolean isCardiovascularDisease;
    @Comment("Rối loạn chuyển hóa lipid")
    private Boolean isLipidMetabolismDisorder;
    @Comment("Phổi tắc nghẽn mãn tính")
    private Boolean isChronicObstructivePulmonaryDisease;
    @Comment("Lao/Hen/Viêm phế quản mãn tính")
    private Boolean isChronicBronchitis;
    @Comment("Ung thư")
    private Boolean isCancer;
    @Comment("Bệnh lý khác")
    private Boolean isOtherDiseases;
    @Comment("Bệnh lý cụ thể")
    @Column(columnDefinition = "longtext")
    private String specificPathology;
    @Comment("Thuốc đang dùng")
    @Column(columnDefinition = "longtext")
    private String currentInUseMedical;
    @Comment("Sốt")
    private Boolean isFever;
    @Comment("Ho")
    private Boolean isCough;
    @Comment("Đau tức ngực")
    private Boolean isChestPain;
    @Comment("Khó thở")
    private Boolean isShortnessOfBreath;
    @Comment("Sử dụng chất gây nghiện")
    private Boolean isSubstanceAbuse;
    private String nameContact;
    private String phoneContact;
}

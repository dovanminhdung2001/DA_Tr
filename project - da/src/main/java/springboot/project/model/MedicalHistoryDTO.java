package springboot.project.model;

import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.Comment;
import springboot.project.entity.User;

@Data
public class MedicalHistoryDTO {
    private Integer id;
    private Integer userId;
    private User user;
    private Integer age;
    private String result;
    private Boolean isDiabetes;
    private Boolean isCardiovascularDisease;
    private Boolean isLipidMetabolismDisorder;
    private Boolean isChronicObstructivePulmonaryDisease;
    private Boolean isChronicBronchitis;
    private Boolean isCancer;
    private Boolean isOtherDiseases;
    private String specificPathology;
    private String currentInUseMedical;
    private Boolean isFever;
    private Boolean isCough;
    private Boolean isChestPain;
    private Boolean isShortnessOfBreath;
    private Boolean isSubstanceAbuse;
    private String nameContact;
    private String phoneContact;
}

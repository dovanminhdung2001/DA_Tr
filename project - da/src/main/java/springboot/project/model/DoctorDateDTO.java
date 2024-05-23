package springboot.project.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DoctorDateDTO {
    private Integer id;
    private Integer doctorId;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Date date;
    private Integer cost;
}

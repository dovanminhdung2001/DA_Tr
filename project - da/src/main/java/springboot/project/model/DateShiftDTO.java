package springboot.project.model;

import lombok.Data;

@Data
public class DateShiftDTO {
    private Integer id;
    private Integer dateId;
    private String shiftTime;
    private Boolean activate;
}

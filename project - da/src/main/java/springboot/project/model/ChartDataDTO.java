package springboot.project.model;

import lombok.Data;

import java.util.List;

@Data
public class ChartDataDTO {
    private List<String> labels;
    private List<DatasetsDTO> datasets;
}

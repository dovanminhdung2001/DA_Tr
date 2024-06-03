package springboot.project.model;

import lombok.Data;

import java.util.List;

@Data
public class DatasetsDTO {
    private String label;
    private List<Integer> data;
    private String backgroundColor;
    private String borderColor;
    private Integer borderWidth;

    public DatasetsDTO() {
    }

    public DatasetsDTO(String label, List<Integer> data) {
        this.label = label;
        this.data = data;
    }
}

package springboot.project.model;

import lombok.Data;

import java.util.Map;

@Data
public class NotificationRequest {
    private String targetToken;
    private String title;
//    private String image;
    private String body;
    private Map<String, String> data;
}

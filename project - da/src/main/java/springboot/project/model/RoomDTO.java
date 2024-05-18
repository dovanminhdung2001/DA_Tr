package springboot.project.model;

import lombok.Data;
import springboot.project.entity.Message;

@Data
public class RoomDTO {
    private Integer id;
    private String room;
    private Message lastMessage;
}

package springboot.project.model;

import springboot.project.entity.User;

public class MessageResponseDTO {
    private String message;

    private int status;
    private Object data;


    public MessageResponseDTO() {
    }

    public MessageResponseDTO(String message) {
        this.message = message;
    }

    public MessageResponseDTO(String token, Object user) {
        this.message = token;
        this.data = user;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

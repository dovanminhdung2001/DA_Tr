package springboot.project.model;

import lombok.Data;

@Data
public class JwtResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String message;
    private Integer statusCode;
    private Object data;

    public JwtResponseDTO(String accessToken, String refreshToken, Object data) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.data = data;
    }

    public JwtResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}

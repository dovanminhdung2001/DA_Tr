package springboot.project.config;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServerMessage implements CommandLineRunner {
    private final SocketIOServer server;


    @Override
    public void run(String... args) {
        server.start();
    }
}

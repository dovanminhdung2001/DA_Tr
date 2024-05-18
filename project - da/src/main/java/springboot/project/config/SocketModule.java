package springboot.project.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import springboot.project.entity.Message;
import springboot.project.service.SocketService;

import java.util.stream.Collectors;

@Component
@Slf4j
public class SocketModule {
    private final SocketIOServer server;
    private final SocketService socketService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", Message.class, onChatReceived());
    }

    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            socketService.saveMessage(senderClient, data);
        };
    }

    private ConnectListener onConnected() {
        return (client) -> {
            var params = client.getHandshakeData().getUrlParams();
            String room = params.get("room").stream().collect(Collectors.joining());
            String userId = params.get("userId").stream().collect(Collectors.joining());
            client.joinRoom(room);
            log.info("Socket ID[{}] - room[{}] - userId [{}]  Connected to chat module through", client.getSessionId().toString(), room, userId);
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            var params = client.getHandshakeData().getUrlParams();
            String room = params.get("room").stream().collect(Collectors.joining());
            String userId = params.get("userId").stream().collect(Collectors.joining());
            log.info("Socket ID[{}] - room[{}] - userId [{}]  disconnected to chat module through", client.getSessionId().toString(), room, userId);
        };
    }
}

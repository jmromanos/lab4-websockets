package websockets;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import websockets.service.Eliza;

import java.io.IOException;
import java.util.Scanner;


@Component
public class SocketHandler extends TextWebSocketHandler {

    private Eliza eliza = new Eliza();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage("The doctor is in."));
        session.sendMessage(new TextMessage("What's on your mind?"));
        session.sendMessage(new TextMessage("---"));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException,
                                                                                                          IOException {
        Scanner currentLine = new Scanner(message.getPayload().toLowerCase());
        if (currentLine.findInLine("bye") == null) {
            session.sendMessage(new TextMessage(eliza.respond(currentLine)));
            session.sendMessage(new TextMessage("---"));
        } else {
            session.sendMessage(new TextMessage("Alright then, goodbye!"));
            session.close();
        }
    }

}

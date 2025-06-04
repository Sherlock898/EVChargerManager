package com.noder.ocppserver.WebSocket;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * Handshake handler for OCPP 1.6
 */
public class OcppHandshakeHandler implements HandshakeHandler{

    private final DefaultHandshakeHandler defaultHandler;

    public OcppHandshakeHandler(DefaultHandshakeHandler defaultHandler){
        this.defaultHandler = defaultHandler;
    }

    @Override
    public boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws HandshakeFailureException {
        
        // Get id from uri, the last parameter in the uri
        String uri = request.getURI().toString();
        String[] uriParts = uri.split("/");
        String id = uriParts[uriParts.length - 1];
        if(id == null || id.isEmpty()){
            response.setStatusCode(HttpStatusCode.valueOf(400));
            return false;
        }

        //TODO: Here must be a call to the main server to check if charger is registered and allowed to connect
        boolean allowed = true; // Call to server to check

        if(!allowed){
            response.setStatusCode(HttpStatusCode.valueOf(403));
        }

        // Check if request subprotocol is ocpp1.6
        // TODO: change this to check for ocpp2.0.1 as well
        List<String> protocols = request.getHeaders().get("Sec-WebSocket-Protocol");
        if(protocols == null || protocols.isEmpty()){
            response.setStatusCode(HttpStatusCode.valueOf(400));
            return false;
        }
        String protocol = protocols.get(0);
        if(!protocol.equals("ocpp1.6")){
            response.setStatusCode(HttpStatusCode.valueOf(400));
            return false;
        }
        
        // Set response subprotocol to ocpp1.6
        response.getHeaders().add("Sec-WebSocket-Protocol", "ocpp1.6");
        return defaultHandler.doHandshake(request, response, wsHandler, attributes);
    }
}
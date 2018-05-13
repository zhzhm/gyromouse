package cn.zzm.remote.keyboard;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A JCAT WebSocket server based on Jetty WebSocket Server API.
 *
 * @see <a
 * href="http://www.eclipse.org/jetty/documentation/current/websocket-jetty.html#jetty-websocket-api">Jetty
 * WebSocket API</a>
 */
public class RemoteController extends WebSocketServlet {
    private static final long serialVersionUID = -8375294682383099330L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteController.class);

    @Override
    public void configure(WebSocketServletFactory factory) {
        // set half hour timeout
        factory.getPolicy().setIdleTimeout(1800000L);
        factory.register(JcatWebSocket.class);
    }

    /**
     * WebSocket server definition.
     *
     * @see <a
     * href="http://www.eclipse.org/jetty/documentation/current/jetty-websocket-api-annotations.html">WebSocket
     * Annotations</a> for more infos.
     */
    @WebSocket
    public static class JcatWebSocket {
        @OnWebSocketMessage
        public void onMessage(Session session, String message) {
            LOGGER.debug("Socket message received: {}", message);
            JSONObject request;
            try {
                request = JSON.parseObject(message);
                //Drop keep-alive message
            } catch (JSONException e) {
                LOGGER.trace("Log message couldn't be parsed. Check stack trace for details.", e);
            }
        }

        @OnWebSocketConnect
        public void onConnect(Session session) {
            LOGGER.info("Connection incoming at {}.", session.getRemoteAddress().getHostString());
        }

        @OnWebSocketClose
        public void onClose(Session session, int closeCode, String reason) {
            LOGGER.info("Connection closed. Code: {}, reason: {}.", closeCode, reason);
        }
    }
}
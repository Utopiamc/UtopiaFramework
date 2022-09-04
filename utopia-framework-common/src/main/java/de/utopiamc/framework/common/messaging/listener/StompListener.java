package de.utopiamc.framework.common.messaging.listener;

import de.utopiamc.framework.common.messaging.StompClient;

public interface StompListener {
    /**
     * Occurs when a STOMP connection could be created.
     * @param connection A working and open STOMP connection ready to be used.
     */
    void connectionSuccess(StompClient connection);

    /**
     * Occurs when the connection could NOT be established.
     * @param cause The reason why it could not connect
     */
    void connectionFailed(Throwable cause);

    /**
     * Occurs when the previously working connection is lost.
     * @param reason The reason why the connection terminated
     */
    void disconnected(String reason);
}

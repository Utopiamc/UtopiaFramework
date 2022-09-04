package de.utopiamc.framework.common.messaging.socket;

import de.utopiamc.framework.common.messaging.listener.SocketListener;
import de.utopiamc.framework.common.messaging.trafic.StompFrame;

public interface StompSocket {

    boolean isConnected();

    void connect(SocketListener listener);

    void sendFrame(StompFrame frame);

    String getHost();

    void close();
}

package de.utopiamc.framework.common.messaging.listener;

import de.utopiamc.framework.common.messaging.trafic.StompFrame;

public interface SocketListener {

    void connected();

    void onStompFrameReceived(StompFrame frame);

    void closed(String reason);

    void connectionFailed(Throwable exception);

}

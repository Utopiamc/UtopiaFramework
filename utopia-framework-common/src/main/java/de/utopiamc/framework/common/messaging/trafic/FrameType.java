package de.utopiamc.framework.common.messaging.trafic;

public enum FrameType {
    /**
     * Connection Request
     */
    CONNECT,

    /**
     * Send a message to the server
     */
    SEND,

    /**
     * Subscription request
     */
    SUBSCRIBE,

    /**
     * Remove a channel subscription
     */
    UNSUBSCRIBE,

    // Transaction handling (NOT IMPLEMENTED YET)
    BEGIN,
    COMMIT,
    ABORT,

    // ACK handling (NOT IMPLEMENTED YET)
    ACK,
    NACK,

    /**
     * Sent to the server to disconnect.
     * The server will only respond if a RECEIPT is requested.
     */
    DISCONNECT,

    /**
     * Response from server, when connection is successful
     */
    CONNECTED,

    /**
     *
     */
    RECEIPT,

    /**
     * Sent if something went wrong.
     * This will cause the connection to be closed afterwards.
     */
    ERROR,

    /**
     * A message from the server
     */
    MESSAGE
}

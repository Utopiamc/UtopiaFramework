package de.utopiamc.framework.api.service;

public interface MessageService {

    void sendMessage(String message);
    void sendError(String message);

}

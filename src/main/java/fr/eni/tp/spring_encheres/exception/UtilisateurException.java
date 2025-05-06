package fr.eni.tp.spring_encheres.exception;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurException extends RuntimeException {
    private List<String> messages = new ArrayList<String>();

    public UtilisateurException() {
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}

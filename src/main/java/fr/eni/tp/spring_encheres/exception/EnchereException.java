package fr.eni.tp.spring_encheres.exception;

import java.util.ArrayList;
import java.util.List;

public class EnchereException extends RuntimeException {
    List<String> messages = new ArrayList<>();
    public EnchereException() {

    }
    public void addMessage(String message) {
      messages.add(message);
    }
  public List<String> getMessages(){
    return messages;
  }
}

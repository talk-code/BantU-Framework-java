package org.bantu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mário Júnior
 */
public class Header {

    private List<Message> messages = new ArrayList();

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}

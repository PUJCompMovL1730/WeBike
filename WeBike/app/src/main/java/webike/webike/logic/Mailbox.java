package webike.webike.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 10/30/2017.
 */

public class Mailbox implements Serializable{
    private List<Message> sent;
    private List<Message> received;

    public Mailbox() {
    }

    public List<Message> getSent() {
        return sent;
    }

    public void setSent(List<Message> sent) {
        this.sent = sent;
    }

    public List<Message> getReceived() {
        return received;
    }

    public void setReceived(List<Message> received) {
        this.received = received;
    }

    @Override
    public String toString() {
        return "Mailbox{" +
                "sent=" + sent +
                ", received=" + received +
                '}';
    }
}

package logic;

/**
 * Created by Juan on 10/28/2017.
 */

public class Message {

    private String msg;
    private User sender;
    private User receiver;

    public Message() {
    }

    public Message(String msg, User sender, User receiver) {
        this.msg = msg;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}

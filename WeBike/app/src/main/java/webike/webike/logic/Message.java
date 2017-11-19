package webike.webike.logic;

import java.io.Serializable;

/**
 * Created by Juan on 10/28/2017.
 */

public class Message implements Serializable{

    private String msg;
    private String subject;
    private String sender;
    private String receiver;

    public Message() {
    }

    public Message(String msg, String subject, String sender, String receiver) {
        this.msg = msg;
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

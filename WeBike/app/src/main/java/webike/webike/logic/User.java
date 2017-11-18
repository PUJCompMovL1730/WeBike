package webike.webike.logic;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 10/28/2017.
 */

public class User implements Serializable{

    private String key;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String email;
    private Mailbox mailbox;
    private List<String> friends;
    private List<Route> history;
    private List<SpecialPublication> historyPublications;
    private List<String> groups;

    public User() {
    }

    public User(String key, String firstName, String lastName, int age, String gender,String email ) {
        this.key = key;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.mailbox = new Mailbox();
        this.friends = new ArrayList<>();
        this.history = new ArrayList<>();
        this.historyPublications = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Mailbox getMailbox() {
        return mailbox;
    }

    public void setMailbox(Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public List<SpecialPublication> getHistoryPublications() {
        return historyPublications;
    }

    public void setHistoryPublications(ArrayList<SpecialPublication> historyPublications) {this.historyPublications = historyPublications;}

    public List<Route> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Route> history) {
        this.history = history;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }
}

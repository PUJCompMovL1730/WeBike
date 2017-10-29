package logic;

import android.location.Location;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by Juan on 10/28/2017.
 */

public class User {

    private String key;
    private String firstName;
    private String lastName;
    private int age;
    private char gender;
    private Uri photo;
    private ArrayList<Message> mailbox;
    private ArrayList<User> friends;
    private ArrayList<Route> history;
    private ArrayList<Group> groups;

    public User() {
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

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    public ArrayList<Message> getMailbox() {
        return mailbox;
    }

    public void setMailbox(ArrayList<Message> mailbox) {
        this.mailbox = mailbox;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<Route> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Route> history) {
        this.history = history;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }
}

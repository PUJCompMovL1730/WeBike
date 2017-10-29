package logic;

import android.location.Location;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 10/28/2017.
 */

public class User {

    private String key;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private Uri photo;
    private List<Message> received;
    private List<Message> sent;
    private List<User> friends;
    private List<Route> history;
    private List<Group> groups;

    public User() {
    }

    public User(String key, String firstName, String lastName, int age, String gender ) {
        this.key = key;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.photo = null;
        this.received = new ArrayList<>();
        this.sent = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.history = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public User(String key, String firstName, String lastName, int age, String gender, Uri photo) {
        this.key = key;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.photo = photo;
        this.received = new ArrayList<>();
        this.sent = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.history = new ArrayList<>();
        this.groups = new ArrayList<>();
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

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    public List<Message> getReceived() {
        return received;
    }

    public void setReceived(List<Message> received) {
        this.received = received;
    }

    public List<Message> getSent() {
        return sent;
    }

    public void setSent(List<Message> sent) {
        this.sent = sent;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public List<Route> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Route> history) {
        this.history = history;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }
}

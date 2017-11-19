package webike.webike.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 10/28/2017.
 */

public class Group implements Serializable{

    private String key;
    private String name;
    private String start;
    private String finish;
    private long time;
    private List<User> users;
    private List<User> admins;
    private String route;

    public Group(String name, String start, String finish, String route) {
        this.name = name;
        this.start = start;
        this.finish = finish;
        this.route = route;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public List<User> getAdmins() {
        return admins;
    }

    public void setAdmins(ArrayList<User> admins) {
        this.admins = admins;
    }
}

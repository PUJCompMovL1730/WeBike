package webike.webike.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Camila on 19/11/2017.
 */

public class OrgUser extends AbstractUser implements Serializable {

    private String key;
    private String name;
    private String location;
    private String email;
    private List<String> specialPublication;

    public OrgUser() {
    }

    public OrgUser(String key, String name, String location,String email ) {
        this.key = key;
        this.name = name;
        this.location = location;
        this.email=email;
        this.specialPublication = new ArrayList<>();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getSpecialPublication() {
        return specialPublication;
    }

    public void setSpecialPublication(List<String> specialPublication) {
        this.specialPublication = specialPublication;
    }
}

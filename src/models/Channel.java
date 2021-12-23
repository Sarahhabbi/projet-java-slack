package models;

import java.util.ArrayList;

public class Channel implements HasId {
    private String name;
    private String admin_id;
    private ArrayList<User> members;

    public Channel(String name, String admin_id) {
        this.name = name;
        this.admin_id = admin_id;
    }

    //Getters and setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getAdmin_id() {return admin_id;}
    public void setAdmin_id(String admin_id) {this.admin_id = admin_id;}
}

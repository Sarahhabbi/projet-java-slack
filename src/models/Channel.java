package models;

public class Channel {
    private String name;
    private String admin_id;

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

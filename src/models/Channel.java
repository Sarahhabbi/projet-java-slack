package models;

public class Channel {
    private int id;
    private String name;
    private String admin_id;

    public Channel(int id, String name, String admin_id) {
        this.id = id;
        this.name = name;
        this.admin_id = admin_id;
    }

    //Getters and setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getAdmin_id() {return admin_id;}
    public void setAdmin_id(String admin_id) {this.admin_id = admin_id;}
}

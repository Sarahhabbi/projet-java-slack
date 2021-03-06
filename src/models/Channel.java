package models;



public class Channel implements HasId {
    private final String name;
    private final String admin_id;
    private final int isPrivate;



    public Channel(String name, String admin_id) {
        this.name = name;
        this.admin_id = admin_id;
        this.isPrivate=0;
    }

    public Channel(String name, String admin_id,int isPrivate) {
        this.name = name;
        this.admin_id = admin_id;
        this.isPrivate=isPrivate;
    }


    //Getters and setters
    public String getName() {return name;}

    public String getAdmin_id() {return admin_id;}

    public int getIsPrivate() {
        return isPrivate;
    }
}

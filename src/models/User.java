package models;

import java.net.Socket;

public class User {
    private int id;
    private String pseudo;
    private String password;
    private String ipAddress;


    public User(int id, String pseudo, String password, String ipAddress) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.ipAddress = ipAddress;
    }

    // Getters and Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public String getIpAddress() { return pseudo; }
    public String getPassword() { return password;}
    public void setPassword(String password) { this.password = password;}
    public void setIpAddress(String ipaAddress) {
        this.ipAddress = ipAddress;
    }

}

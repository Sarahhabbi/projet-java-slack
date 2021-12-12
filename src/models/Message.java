package models;

import java.util.Date;

public class Message {
    private int id;
    private int user_id;
    private int channel_id;
    private String text;
    private Date date;

    public Message(int id, int user_id, int channel_id, String text, Date date) {
        this.id = id;
        this.user_id = user_id;
        this.channel_id = channel_id;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

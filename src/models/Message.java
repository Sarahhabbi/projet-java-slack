package models;


import java.util.concurrent.atomic.AtomicLong;

public class Message implements HasId_Channel {

    private static final AtomicLong ID_GENERATOR = new AtomicLong();

    private final String id;
    private String text;
    private final String creator;
    private String channelName;
    private boolean isEdited;


    public Message(String text,String creator,String channelName) {
        id = Long.toString(ID_GENERATOR.incrementAndGet());
        this.text = text;
        this.creator=creator;
        this.channelName=channelName;
        this.isEdited=false;
    }

    public Message(String id,String text,String creator,String channelName) {
        this.id = id;
        this.text = text;
        this.creator=creator;
        this.channelName=channelName;
        this.isEdited=false;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getCreator() {
        return creator;
    }

    public String getChannelName() {
        return channelName;
    }

    public boolean isEdited() {
        return isEdited;
    }

    @Override
    public String getName() {
        return id;
    }
}

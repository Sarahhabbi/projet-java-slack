package models;


import java.util.concurrent.atomic.AtomicLong;

public class Message implements HasId {

    private static final AtomicLong ID_GENERATOR = new AtomicLong();

    private final String id;
    private String text;
    private final String creator;
    private boolean isEdited;
    private String join;

    public Message(String text,String creator) {
        id = Long.toString(ID_GENERATOR.incrementAndGet());
        this.text = text;
        this.creator=creator;
        this.isEdited=false;
        this.join=null;
    }

    public Message(String text,String creator,String join) {
        id = Long.toString(ID_GENERATOR.incrementAndGet());
        this.text = text;
        this.creator=creator;
        this.isEdited=false;
        this.join=join;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getName() {
        return id;
    }
}

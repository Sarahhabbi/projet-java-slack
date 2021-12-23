package models;


import java.util.concurrent.atomic.AtomicLong;

public class Message implements HasId {

    private static final AtomicLong ID_GENERATOR = new AtomicLong();

    private String id;
    private String text;

    public Message(String text) {
        id = Long.toString(ID_GENERATOR.incrementAndGet());
        this.text = text;
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

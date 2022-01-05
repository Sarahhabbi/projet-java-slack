package models;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class Message implements HasId {

    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private static final String currentDate = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss").format(LocalDateTime.now());

    private final String id;
    private String text;
    private final String creator;
    private final String channelName;


    public Message(String text,String creator,String channelName) {
        id = Long.toString(ID_GENERATOR.incrementAndGet()) + "_"+currentDate;
        this.text = text;
        this.creator=creator;
        this.channelName=channelName;
    }

    public Message(String id,String text,String creator,String channelName) {
        this.id = id;
        this.text = text;
        this.creator=creator;
        this.channelName=channelName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreator() {
        return creator;
    }

    public String getChannelName() {
        return channelName;
    }

    @Override
    public String getName() {
        return id;
    }
}

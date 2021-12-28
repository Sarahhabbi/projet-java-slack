package models;

public class ChannelUser implements HasId_Channel{
    private String channelName;
    private String userName;

    public ChannelUser(String channelName, String userName) {
        this.channelName = channelName;
        this.userName = userName;
    }

    @Override
    public String getName() {
        return channelName+"_"+userName;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getUserName() {
        return userName;
    }
}

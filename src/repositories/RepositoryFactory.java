package repositories;
import models.Channel;
import models.ChannelUser;
import models.Message;
import models.User;
import database.Database;

import java.util.HashMap;
import java.util.Map;

public class RepositoryFactory {

    private static final Database DATABASE = Database.getInstance("jdbc:mysql://localhost:3306/slack", "root", "poudebs91");

    public static Repository<User> user() {
        return new CompositeRepository<>(UserRepository.getInstance(DATABASE.getConnection()));
    }

    public static Repository<Channel> channel(){
        return new CompositeRepository<>(ChannelRepository.getInstance(DATABASE.getConnection()));
    }

    public static Repository<ChannelUser> channel_user(String channelName){
        return new CompositeRepository<>( ChannelUserRepository.getInstance(channelName,DATABASE.getConnection()));
    }

    public static Repository<Message> message(String channelName){
        return new CompositeRepository<>( MessageRepository.getInstance(channelName,DATABASE.getConnection()));
    }
}





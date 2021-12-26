package repositories;

import models.Channel;
import models.ChannelUser;
import models.Message;
import models.User;
import database.Database;

public class RepositoryFactory {
    private static final Database DATABASE = Database.getInstance("jdbc:mysql://localhost:3306/slack", "root", "poudebs91");

    public static Repository<User> user() {
        return new CompositeRepository<>(UserRepository.getInstance(DATABASE.getConnection()));
    }

    public static Repository<Channel> channel(){
        return new CompositeRepository<>(ChannelRepository.getInstance(DATABASE.getConnection()));
    }

    public static Repository_channel<ChannelUser> channel_user(){
        return new CompositeChannelRepository<ChannelUser>(ChannelUserRepository.getInstance(DATABASE.getConnection()));
    }

    public static Repository_channel<Message> message(){
        return new CompositeChannelRepository<>(MessageRepository.getInstance(DATABASE.getConnection()));
    }

}

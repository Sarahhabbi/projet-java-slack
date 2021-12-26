package repositories;

import models.Channel;
import models.User;

public class RepositoryFactory {

    public static Repository<User> user() {
        return new CompositeRepository<>(new UserRepository());
    }

    public static Repository<Channel> channel(){
        return new CompositeRepository<>(new ChannelRepository());
    }
//    public static Repository<Channel> channel(String filename) { return new CompositeRepository<>(new ChannelRepository(filename)); }

    public static Repository<Channel> channel_user(String filename){
        return new CompositeRepository<>(new ChannelUserRepository(filename));
    }

}

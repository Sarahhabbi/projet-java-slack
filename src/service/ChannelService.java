package service;
import models.Channel;
import models.ChannelUser;
import models.User;
import models.Message;
import repositories.ChannelUserRepository;
import repositories.Repository;
import repositories.RepositoryFactory;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelService {
    private User currentUser;
    private Channel currentchannel;

    private static final Repository<User> userRepository = RepositoryFactory.user();
    private static final Repository<Channel> channelRepository = RepositoryFactory.channel();
    private static Map<String, Repository<Message>> messageRepositories = new HashMap<String, Repository<Message>>();
    private static Map<String, Repository<ChannelUser>> channeluserRepositories = new HashMap<String, Repository<ChannelUser>>();

    public void deleteChannel(String name,String pseudo) throws Exception{
        Channel channel=channelRepository.find(name);
        if(channel==null){
            throw new Exception("This channel didn't exist");
        }
        if(channel.getAdmin_id().equals(pseudo) ){
            channelRepository.delete(channel);
            ChannelUser channel_user= new ChannelUser(name,pseudo);
            channeluserRepositories.get(name).delete(channel_user);
        }
        else{
            throw new Exception("Action denied, only admin who can delete this channel ");
        }
    }

    public List<Message> joinChannel(String name,String pseudo) throws Exception {
        Channel channel=channelRepository.find(name);
        if(channel == null){
            throw new Exception("This channel didn't exist");
        }
        ChannelUser channel_user= new ChannelUser(name,pseudo);
        Repository<ChannelUser> cu=channeluserRepositories.get(name);
        if(cu==null){
            cu=RepositoryFactory.channel_user(name);
            channeluserRepositories.put(name,cu);
        }

        //récuperer l'historique des messages d'un channel
        Repository<Message> messageRepository = messageRepositories.get(name);
        if(messageRepository == null){
            messageRepository = RepositoryFactory.message(name);
            messageRepositories.put(name, messageRepository);
        }
        cu.save(channel_user);
        List<Message> messageHistory = messageRepository.findAll();
        return messageHistory;
    }

    public void createChannel(String name,String pseudo) throws Exception {
        if(channelRepository.find(name)!=null) {
            throw new Exception("Name already exists ! Please try another one.");
        }
        Channel channel = new Channel(name,pseudo);
        Repository<ChannelUser> cu=RepositoryFactory.channel_user(name);
        channeluserRepositories.put(name,cu);
        cu.save(new ChannelUser(name,pseudo));
        //create message repository with channel name and add to MessageRepository
        List<Message> messageHistory = this.joinChannel(name, pseudo);
        channelRepository.save(channel);
    }
}

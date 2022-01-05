package service;
import models.Channel;
import models.ChannelUser;

import models.Message;

import repositories.Repository;
import repositories.RepositoryFactory;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelService {


    private static final Repository<Channel> channelRepository = RepositoryFactory.channel();
    private static Map<String, Repository<Message>> messageRepositories = new HashMap<String, Repository<Message>>();
    private static Map<String, Repository<ChannelUser>> channeluserRepositories = new HashMap<String, Repository<ChannelUser>>();

    public void deleteChannel(String name,String pseudo) throws Exception{
        Channel channel=channelRepository.find(name);
        if(channel==null){
            throw new Exception("This channel didn't exist");
        }
        if(! channel.getAdmin_id().equals(pseudo) ){
            throw new Exception("Action denied, only admin who can delete this channel ");
        }
        if(channel.getIsPrivate()==0){
            channelRepository.delete(channel);
        }
        Repository<ChannelUser> cu=channeluserRepositories.get(name);
        if(cu==null){
            cu=RepositoryFactory.channel_user(name);
            channeluserRepositories.put(name,cu);
        }
        List<ChannelUser> members=cu.findAll();
        for(int i=0;i<members.size();i++){
            cu.delete(members.get(i));
        }
        channelRepository.delete(channel);
    }

    public List<Message> joinChannel(String name,String pseudo) throws Exception {
        Channel channel = channelRepository.find(name);
        System.out.println(name +" "+pseudo);
        if(channel == null){
            throw new Exception("This channel doesn't exist, you can create it with this command: / create " + name);
        }
        if (channel.getIsPrivate()==1){
            Repository<ChannelUser> cu=channeluserRepositories.get(name);
            if(cu==null){
                cu=RepositoryFactory.channel_user(name);
                channeluserRepositories.put(name,cu);
            }
            if(cu.exists(new ChannelUser(name, pseudo))){
                throw new Exception("This channel is private and you are not a member !");
            }
        }

        //récuperer l'historique des messages d'un channel
        Repository<Message> messageRepository = messageRepositories.get(name);
        if(messageRepository == null){
            messageRepository = RepositoryFactory.message(name);
            messageRepositories.put(name, messageRepository);
        }
        List<Message> messageHistory = messageRepository.findAll();
        System.out.println("Service");
        for (int i=0;i< messageHistory.size();i++){

            System.out.println(messageHistory.get(i).getCreator() + ": "+messageHistory.get(i).getText());
        }
        return messageHistory;
    }

    public void createChannel(String name,String pseudo,int isPrivate) throws Exception {
        if(channelRepository.find(name)!=null) {
            throw new Exception("Name already exists ! Please try another one.");
        }
        Channel channel = new Channel(name,pseudo,isPrivate);
        //create message repository with channel name and add to MessageRepository
        channelRepository.save(channel);
        if(isPrivate==1){
            Repository<ChannelUser> cu=RepositoryFactory.channel_user(name);
            channeluserRepositories.put(name,cu);
            ChannelUser admin=new ChannelUser(name, pseudo);
            cu.save(admin);
        }
        this.joinChannel(name, pseudo);
    }

    public void addMemberChannel(String name, String pseudo,String user)throws Exception{
        Channel channel = channelRepository.find(name);
        if (channel == null) {
            throw new Exception("This channel didn't exist");
        }
        if (! channel.getAdmin_id().equals(pseudo)) {
            throw new Exception("Action denied, only admin can add members");
        }
        Repository<ChannelUser> cu=channeluserRepositories.get(name);
        if(cu==null){
            cu=RepositoryFactory.channel_user(name);
            channeluserRepositories.put(name,cu);
        }
        cu.save(new ChannelUser(name, user));

    }

    public void deleteMemberChannel(String name, String pseudo,String user)throws Exception{
        Channel channel = channelRepository.find(name);
        if (channel == null) {
            throw new Exception("This channel didn't exist");
        }
        if (! channel.getAdmin_id().equals(pseudo)) {
            throw new Exception("Action denied, only admin can add members");
        }
        Repository<ChannelUser> cu=channeluserRepositories.get(name);
        if(cu==null){
            cu=RepositoryFactory.channel_user(name);
            channeluserRepositories.put(name,cu);
        }
        cu.delete(new ChannelUser(name, user));

    }

    public void sendMessage(Message m){
        Repository<Message> messageRepository = messageRepositories.get(m.getChannelName());
        messageRepository.save(m);
    }

    public List<Message> deleteMessage(Message m)throws Exception{
        Repository<Message> messageRepository = messageRepositories.get(m.getChannelName());
        messageRepository.delete(m);
        List<Message> messageHistory = messageRepository.findAll();
        System.out.println("Service");
        for (int i=0;i< messageHistory.size();i++){

            System.out.println(messageHistory.get(i).getCreator() + ": "+messageHistory.get(i).getText());
        }
        return messageHistory;
    }

    public List<Message> EditMessage(Message m)throws Exception{
        Repository<Message> messageRepository = messageRepositories.get(m.getChannelName());
        messageRepository.delete(m);
        List<Message> messageHistory = messageRepository.findAll();
        System.out.println("Service");
        for (int i=0;i< messageHistory.size();i++){

            System.out.println(messageHistory.get(i).getCreator() + ": "+messageHistory.get(i).getText());
        }
        return messageHistory;
    }


}

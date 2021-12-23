package service;

import models.Channel;
import models.User;
import repositories.Repository;
import repositories.RepositoryFactory;

public class UserService {

    private User currentUser;
    private Channel currentchannel;
    private static final Repository<User> userRepository = RepositoryFactory.user();
    private static final Repository<Channel> channelRepository = RepositoryFactory.channel();
    private final Repository<Channel> channelUserRepository = RepositoryFactory.channel_user();

    public UserService() {
    }

    public void signUp(String pseudo, String password) throws Exception {
        User newUser = new User(pseudo, password);

        if(!userRepository.exists(newUser)){
            userRepository.save(newUser);
            System.out.println(pseudo + " was successfully added !");
        }
        else{
            throw new Exception("Pseudo already exists ! Please try another one.");
        }
    }

    public void connect(String pseudo, String password) throws Exception {
        User user = userRepository.find(pseudo);
        if (user == null) {
            System.out.println("User is null");
            throw new NullPointerException();
        }
        else if(!user.getPassword().equals(password)) {
            throw new Exception("Illegal connection: password incorrect");
        }
        this.currentUser=user;
        System.out.println("Hey "+ pseudo + " you've successfully connected to Slack");
    }

    public void createChannel(String name) throws Exception{
        /*a rajouter dans controller*/
        if(this.currentUser==null){
            throw new Exception("Please sign in before create a channel");
        }
        Channel channel=channelRepository.find(name);
        if(channel!=null){
            throw new Exception("Name already exists ! Please try another one.");
        }
        channel=new Channel(name,this.currentUser.getPseudo());
        channelRepository.save(channel);

    }

   /*public void joinChannel(String name,String pseudo) throws Exception{

        //Channel channel=channelRepository.(name,pseudo);
        if(channel==null){
            throw new Exception("You are not a member ");
        }
        channelUserRepository.save(channel);
        this.currentchannel=channel;
    }*/

    public void deleteChannel(String name,String pseudo) throws Exception{
        Channel channel=channelRepository.find(name);
        if(channel==null){
            throw new Exception("There is no channel with this name");
        }

        if(!channel.getAdmin_id().equals(pseudo)){
            throw new Exception("You are not the admin of the channel, you can't delete the channel");
        }

        channelRepository.delete(channel);
        channelUserRepository.delete(channel);

        this.currentchannel=null;
    }

/*    public void quitChannel(String name) throws Exception{
        if(this.currentchannel==null){
            throw new Exception("You are already in main page ");
        }
        this.currentchannel=null;*/
    }
//   public void joinChannel()

}


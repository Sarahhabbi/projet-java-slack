package service;

import models.Channel;
import models.User;
import repositories.Repository;
import repositories.RepositoryFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class UserService {

    private User currentUser;
    private Channel currentchannel;
    private static final Repository<User> userRepository = RepositoryFactory.user();
    private static final Repository<Channel> channelRepository = RepositoryFactory.channel();
    private ArrayList<Repository<Channel>> channelUserRepository;
    private ArrayList<Repository<Channel>> MessageRepository;

    public UserService(){

    }

    public void signUp(String pseudo, String password) throws Exception {
        User newUser = userRepository.find(pseudo);
        if(newUser==null){
            userRepository.save(new User(pseudo,password));
            System.out.println(pseudo + " was successfully added !");
        }
        else{
            throw new Exception("Pseudo already exists ! Please try another one.");
        }
    }

    public void connect(String pseudo, String password) throws Exception {
        User user = userRepository.find(pseudo);
        if (user == null) {
            System.out.println("There is no user with this pseudo");
            throw new NullPointerException();
        }
        else if(!user.getPassword().equals(password)) {
            throw new Exception("Illegal connection: password incorrect");
        }
        System.out.println("Hey "+ pseudo + " you've successfully connected to Slack");
    }

    public void createChannel(String name,String pseudo) throws Exception{
        Channel channel=channelRepository.find(name);
        if(channel!=null){
            throw new Exception("Name already exists ! Please try another one.");
        }
        channel=new Channel(name,pseudo);
        channelRepository.save(channel);

    }
}


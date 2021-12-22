package service;

import models.User;
import repositories.CompositeRepository;
import repositories.Repository;
import repositories.RepositoryFactory;
import repositories.UserRepository;

public class UserService {

    private final Repository<User> userRepository = RepositoryFactory.user();

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
        System.out.println("Hey "+ pseudo + " you've successfully connected to Slack");
    }

//   public void joinChannel()

}

package service;

import models.User;
import repositories.CompositeRepository;
import repositories.Repository;
import repositories.RepositoryFactory;
import repositories.UserRepository;

public class UserService {

    private final Repository<User> userRepository = RepositoryFactory.user();


    public void signUp(String pseudo, String password) throws Exception {
        User newUser = new User(pseudo, password);

        if(!userRepository.exists(newUser)){
            userRepository.save(newUser);
        }
        else{
            throw new Exception("Pseudo already exists ! Please try another one.");
        }
    }

    public void connect(String pseudo, String password) throws Exception {
        User user = userRepository.find(pseudo);
        if (user == null) {
            throw new NullPointerException();
        }
        else if(!user.getPassword().equals(password)) {
            throw new Exception("Illegal connection: password incorrect");
        }
        System.out.println("Connection réussi pour " + pseudo);
    }

//   public void joinChannel()

}

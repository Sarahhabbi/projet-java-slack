package service;

import models.User;
import repositories.CompositeRepository;
import repositories.Repository;
import repositories.RepositoryFactory;
import repositories.UserRepository;

public class UserService {

    private Repository<User> userRepository = RepositoryFactory.user();

    public void connect(String pseudo, String password) {
        User user = userRepository.find(pseudo);
        if (user == null) {
            //TODO : Erreur
        }
        if (!user.getPassword().equals(password)) {
            // TODO : Erreur
        }
        // Connexion réussie
    }

}

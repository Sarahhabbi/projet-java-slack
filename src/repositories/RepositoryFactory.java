package repositories;

import models.User;

public class RepositoryFactory {

    public static Repository<User> user() {
        return new CompositeRepository(new UserRepository());
    }

}

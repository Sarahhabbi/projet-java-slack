package repositories;

import models.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class UserRepository implements Repository<User> {

    private static final String FILENAME = "users.txt";

    @Override
    public User save(User obj) {
        try (FileOutputStream fos = new FileOutputStream(FILENAME)) {

        } catch (IOException e) {

        }
        return obj;
    }

    @Override
    public void delete(User obj) {

    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User find(String id) {
        return null;
    }

    public boolean exists(User newUser) {
//    TODO: trouver si un user est deja présent dans le fichier
        return false;
    }
}

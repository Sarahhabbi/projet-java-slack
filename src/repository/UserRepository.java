package repository;

import models.User;

import java.sql.*;
import java.util.Optional;

public class UserRepository{
    private static UserRepository instance;

    private final Connection DBConnexion;

    private UserRepository(Connection DBConnexion){
        this.DBConnexion = DBConnexion;
    }

    public static UserRepository getInstance(Connection DBConnexion){
        if (UserRepository.instance == null) {
            UserRepository.instance = new UserRepository(DBConnexion);
        }
        return instance;
    }

    public void save(User user) {
        String req = "INSERT INTO users (pseudo, password) VALUES (?, ?)";
        try (PreparedStatement ps = this.DBConnexion.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getPseudo());
            ps.setString(2, user.getPassword());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId((int) generatedKeys.getLong(1));
            }

            System.out.println(user.getPseudo() + " successfully added to USERS table !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  
    public Iterable<User> saveAll(Iterable users) {
        return null;
    }

    public Optional<User> findById(User userID) {
        return Optional.empty();
    }

    public boolean existsById(User userID) {
        return false;
    }

    public Iterable<User> findAll() {
        return null;
    }
       
    public long count() {
        return 0;
    }
       
    public void deleteById(User userID) {

    }

    public void deleteAll() {

    }
}

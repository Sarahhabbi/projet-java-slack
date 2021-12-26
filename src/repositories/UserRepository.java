package repositories;

import models.User;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class UserRepository implements Repository<User> {
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

    @Override
    public User save(User user) {
        String req = "INSERT INTO users (pseudo, password) VALUES (?, ?)";
        try (PreparedStatement ps = this.DBConnexion.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getPseudo());
            ps.setString(2, user.getPassword());

            ps.executeUpdate();

            System.out.println(user.getPseudo() + " successfully added to USERS table !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        String req="SELECT * FROM users";
        ArrayList<User> users = new ArrayList<>();
        try{
            PreparedStatement ps = this.DBConnexion.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys=ps.executeQuery(req);
            while(generatedKeys.next()){
                users.add(new User(generatedKeys.getString(1),generatedKeys.getString(2)));
                System.out.println(generatedKeys.getString(1)+ " "+generatedKeys.getString(2));
            }
            generatedKeys.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean exists(User obj){
        System.out.println("recherche: "+obj.getPseudo()+" "+obj.getPassword());
        try{
            PreparedStatement ps = this.DBConnexion.prepareStatement("SELECT * FROM users WHERE pseudo=? AND password=?");
            ps.setString(1, obj.getPseudo());
            ps.setString(2, obj.getPassword());

            ResultSet res=ps.executeQuery();
            while(res.next()){
                System.out.println(res.getString(1)+ " "+res.getString(2));
                return true;
            }
            res.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(User obj){

        try {
            PreparedStatement ps = this.DBConnexion.prepareStatement("DELETE FROM users WHERE pseudo=? AND password=?");
            ps.setString(1, obj.getPseudo());
            ps.setString(2, obj.getPassword());

            ps.executeUpdate();


            System.out.println(" successfully deleted to USERS table !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(String name){
        try {
            PreparedStatement ps = this.DBConnexion.prepareStatement("SELECT * FROM users WHERE pseudo=? ");
            ps.setString(1, name);

            ResultSet res=ps.executeQuery();
            while(res.next()){
                User u=new User(res.getString(1),res.getString(2));
                System.out.println(u.getPseudo()+" "+u.getPassword());
                res.close();
                return u;
            }
            res.close();
            System.out.println("There is no user with this name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> saveAll(List<User> users) {
        for(User element:users){
            save(element);
        }
        return null;
    }


    public void deleteAll() {
        try {
            PreparedStatement ps = this.DBConnexion.prepareStatement("DELETE FROM users");
            ps.executeUpdate();
            System.out.println(" successfully deleted to USERS table !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
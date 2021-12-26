package repositories;



import models.ChannelUser;
import models.Message;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChannelUserRepository implements Repository_channel<ChannelUser>{
    private static ChannelUserRepository instance;
    private final Connection DBConnexion;

    private ChannelUserRepository(Connection DBConnexion){
        this.DBConnexion = DBConnexion;
    }

    public static ChannelUserRepository getInstance(Connection DBConnexion){
        if (ChannelUserRepository.instance == null) {
            ChannelUserRepository.instance = new ChannelUserRepository(DBConnexion);
        }
        return instance;
    }

    @Override
    public ChannelUser save(ChannelUser obj) {
        String req = "INSERT INTO channel_users (channel_id,user_id) VALUES (?,?)";
        try (PreparedStatement ps = this.DBConnexion.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, obj.getChannelName());
            ps.setString(2, obj.getUserName());
            ps.executeUpdate();

            System.out.println(obj.getName() + " successfully added to USERS table !");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

    @Override
    public void delete(ChannelUser obj) throws FileNotFoundException, Exception {
        try {
            PreparedStatement ps = this.DBConnexion.prepareStatement("DELETE FROM channel_users WHERE channel_id=? AND user_id=?");
            ps.setString(1, obj.getChannelName());
            ps.setString(2, obj.getUserName());
            ps.executeUpdate();
            System.out.println(" successfully deleted to USERS table !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ChannelUser> findAll() throws FileNotFoundException {
        String req="SELECT channel_id,user_id FROM channel_users";
        ArrayList<ChannelUser> cu = new ArrayList<>();
        try{
            PreparedStatement ps = this.DBConnexion.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys=ps.executeQuery(req);
            while(generatedKeys.next()){
                cu.add(new ChannelUser(generatedKeys.getString(1),generatedKeys.getString(2)));
                System.out.println(generatedKeys.getString(1)+ " "+generatedKeys.getString(2));
            }
            generatedKeys.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return cu;
    }

    @Override
    public ChannelUser find(String id) throws FileNotFoundException {

        return null;
    }

    @Override
    public boolean exists(ChannelUser obj) {
        try{
            PreparedStatement ps = this.DBConnexion.prepareStatement("SELECT * FROM channel_users WHERE channel_id=? AND user_id=?");
            ps.setString(1, obj.getChannelName());
            ps.setString(2, obj.getUserName());

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
    public List<ChannelUser> findAllByChannel(String name) throws FileNotFoundException {
        String req="SELECT channel_id,user_id FROM channel_users WHERE channel_id=?";
        ArrayList<ChannelUser> cu = new ArrayList<>();
        try{
            PreparedStatement ps = this.DBConnexion.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ResultSet generatedKeys=ps.executeQuery(req);
            while(generatedKeys.next()){
                cu.add(new ChannelUser(generatedKeys.getString(1),generatedKeys.getString(2)));
                System.out.println(generatedKeys.getString(1)+ " "+generatedKeys.getString(2));
            }
            generatedKeys.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return cu;
    }
}


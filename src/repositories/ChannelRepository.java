package repositories;

import models.Channel;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class ChannelRepository implements Repository<Channel> {
    private static ChannelRepository instance;
    private final Connection DBConnexion;

    private ChannelRepository(Connection DBConnexion){
        this.DBConnexion = DBConnexion;
    }

    public static ChannelRepository getInstance(Connection DBConnexion){
        if (ChannelRepository.instance == null) {
            ChannelRepository.instance = new ChannelRepository(DBConnexion);
        }
        return instance;
    }

    @Override
    public Channel save(Channel channel) {
        String req = "INSERT INTO channels (name, admin_pseudo) VALUES (?, ?)";
        try (PreparedStatement ps = this.DBConnexion.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, channel.getName());
            ps.setString(2, channel.getAdmin_id());

            ps.executeUpdate();

            System.out.println(channel.getName() + " successfully added to CHANNEL_USERS table !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return channel;
    }

    @Override
    public List<Channel> findAll() {
        String req="SELECT * FROM channels";
        ArrayList<Channel> channels = new ArrayList<>();
        try{
            PreparedStatement ps = this.DBConnexion.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys=ps.executeQuery(req);
            while(generatedKeys.next()){
                channels.add(new Channel(generatedKeys.getString(2),generatedKeys.getString(1)));
                System.out.println(generatedKeys.getString(2)+ " "+generatedKeys.getString(1));
            }
            generatedKeys.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return channels;
    }

    @Override
    public boolean exists(Channel obj){
        try{
            PreparedStatement ps = this.DBConnexion.prepareStatement("SELECT * FROM channels WHERE name=? AND admin_pseudo =?");
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getAdmin_id());

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
    public void delete(Channel obj){

        try {
            PreparedStatement ps = this.DBConnexion.prepareStatement("DELETE FROM channels WHERE name=? AND admin_pseudo =?");
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getAdmin_id());
            ps.executeUpdate();
            System.out.println(" successfully deleted to CHANNEL_USERS table !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Channel find(String name){
        try {
            PreparedStatement ps = this.DBConnexion.prepareStatement("SELECT * FROM channels WHERE name=? ");
            ps.setString(1, name);

            ResultSet res=ps.executeQuery();
            while(res.next()){
                Channel u=new Channel(res.getString(1),res.getString(2));
                System.out.println(u.getName()+" "+u.getAdmin_id());
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

    public List<Channel> saveAll(List<Channel> users) {
        for(Channel element:users){
            save(element);
        }
        return null;
    }


    public void deleteAll() {
        try {
            PreparedStatement ps = this.DBConnexion.prepareStatement("DELETE FROM channels");
            ps.executeUpdate();
            System.out.println(" successfully deleted to CHANNEL_USERS table !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
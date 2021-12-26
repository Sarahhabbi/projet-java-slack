package repositories;


import models.Channel;
import models.Message;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageRepository implements Repository_channel<Message>{
    private static MessageRepository instance;
    private final Connection DBConnexion;

    private MessageRepository(Connection DBConnexion){
        this.DBConnexion = DBConnexion;
    }

    public static MessageRepository getInstance(Connection DBConnexion){
        if (MessageRepository.instance == null) {
            MessageRepository.instance = new MessageRepository(DBConnexion);
        }
        return instance;
    }

    @Override
    public Message save(Message message) {
        String req = "INSERT INTO groupeMessages (message_id,user_id,channel_id,message,date) VALUES (?,?,?,?,CURRENT_TIMESTAMP)";
        try (PreparedStatement ps = this.DBConnexion.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, message.getName());
            ps.setString(2, message.getCreator());
            ps.setString(3, message.getChannelName());
            ps.setString(4, message.getText());
            ps.executeUpdate();

            System.out.println(message.getName() + " successfully added to USERS table !");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return message;
    }

    @Override
    public void delete(Message obj) throws FileNotFoundException, Exception {
        try {
            PreparedStatement ps = this.DBConnexion.prepareStatement("DELETE FROM groupeMessages WHERE message_id=?");
            ps.setString(1, obj.getName());
            ps.executeUpdate();
            System.out.println(" successfully deleted to USERS table !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> findAll() throws FileNotFoundException {
        String req="SELECT message_id,message,user_id,channel_id FROM groupeMessages";
        ArrayList<Message> messages = new ArrayList<>();
        try{
            PreparedStatement ps = this.DBConnexion.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys=ps.executeQuery(req);
            while(generatedKeys.next()){
                messages.add(new Message(generatedKeys.getString(1),generatedKeys.getString(2),generatedKeys.getString(3),generatedKeys.getString(4)));
                System.out.println(generatedKeys.getString(1)+ " "+generatedKeys.getString(2)+" "+generatedKeys.getString(3)+" "+generatedKeys.getString(4));
            }
            generatedKeys.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Message find(String id) throws FileNotFoundException {
        try {
            PreparedStatement ps = this.DBConnexion.prepareStatement("SELECT message,user_id,channel_id FROM groupeMessages WHERE message_id=?");
            ps.setString(1, id);

            ResultSet res=ps.executeQuery();
            while(res.next()){
                Message u=new Message(id,res.getString(1),res.getString(2),res.getString(3));

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

    @Override
    public boolean exists(Message obj) {
        try{
            PreparedStatement ps = this.DBConnexion.prepareStatement("SELECT * FROM groupeMessages WHERE message_id=?");
            ps.setString(1, obj.getName());

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
    public List<Message> findAllByChannel(String name) throws FileNotFoundException {
        String req="SELECT message_id,message,user_id,channel_id FROM groupeMessages WHERE channel_id=?";
        ArrayList<Message> messages = new ArrayList<>();
        try{
            PreparedStatement ps = this.DBConnexion.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ResultSet generatedKeys=ps.executeQuery(req);
            while(generatedKeys.next()){
                messages.add(new Message(generatedKeys.getString(1),generatedKeys.getString(2),generatedKeys.getString(3),generatedKeys.getString(4)));
                System.out.println(generatedKeys.getString(1)+ " "+generatedKeys.getString(2)+" "+generatedKeys.getString(3)+" "+generatedKeys.getString(4));
            }
            generatedKeys.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}

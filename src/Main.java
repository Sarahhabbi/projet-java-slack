import java.io.IOException;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import database.Database;
import models.User;
import repositories.UserRepository;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Database database = Database.getInstance("jdbc:mysql://localhost:3306/slack", "root", "Siyani=17");
        System.out.println("connexion réussie");
        User sarah = new User("habbisarah", "motdepasse2312");
        UserRepository userRepository = UserRepository.getInstance(database.getConnection());

        userRepository.save(sarah);
        userRepository.delete(sarah);
        System.out.println(userRepository.exists(sarah));
        /*System.out.println("pseudo: "+ sarah.getPseudo() + " id : "+ sarah.getName());*/
        List<User> u=userRepository.findAll();
        /*for(User element:u){
            System.out.println("pseudo: "+ element.getPseudo() + " id : "+ element.getName()+" password: "+element.getPassword());
        }
        userRepository.delete(sarah);
        u=userRepository.findAll();
        for(User element:u){
            System.out.println("pseudo: "+ element.getPseudo() + " id : "+ element.getName()+" password: "+element.getPassword());
        }
        User shalom=userRepository.find("Shalom");
        User dohee=userRepository.find("Dohee");
        System.out.println("pseudo: "+ shalom.getPseudo() + " id : "+ shalom.getName());*/
        userRepository.deleteAll();
        u=userRepository.findAll();
    }
}

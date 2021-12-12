import database.Database;
import models.User;
import repository.UserRepository;

public class Main {

    public static void main(String[] args) {
        Database database = Database.getInstance("jdbc:mysql://localhost:3306/slack", "root", "poudebs91");

        UserRepository userRepository = UserRepository.getInstance(database.getConnection());

        User sarah = new User("habbisarah", "motdepasse2312","192.234.344.54");
        userRepository.save(sarah);
        System.out.println("pseudo: "+ sarah.getPseudo() + " id : "+ sarah.getId());

    }
}

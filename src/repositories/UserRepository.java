package repositories;

import models.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.FileInputStream;

public class UserRepository implements Repository<User> {

    private static String FILENAME = "/Users/habbi/Documents/L3-DANT/PROJETS_GROUPE/ProjetSlack/projet-java-slack/src/data/users";/* a changer selon l'os*/

    @Override
    public User save(User obj) {
        String pseudo = obj.getPseudo();
        String password = obj.getPassword();

        try{
            String content=pseudo+ ";"+ password+"\n";
            FileOutputStream fos= new FileOutputStream(FILENAME,true);
            fos.write(content.getBytes(StandardCharsets.UTF_8));
            fos.close();
        }catch(FileNotFoundException fne){
            fne.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public void delete(User obj) throws Exception {
        if(!exists(obj)){
            throw new Exception(obj.getPseudo() +" doesn't exists !");
        }
        String pseudo1 = obj.getPseudo();
        Scanner scanner = new Scanner(new File(FILENAME));
        String line ;
        PrintWriter writer = new PrintWriter("/src/data/newfile.txt", StandardCharsets.UTF_8);

        while((line = scanner.nextLine()) != null) {
            String[] words = line.split(";");
            String pseudo2 = words[0];
            String password2 = words[1];

            if( !(pseudo2.equals(pseudo1)) ) {
                writer.println(pseudo2 + " " + password2);
            }
        }
        FILENAME = "/src/data/newfile.txt";
    }

    @Override
    public ArrayList<User> findAll() throws FileNotFoundException {
        ArrayList<User> users = new ArrayList<>();
        try{
            FileInputStream fis=new FileInputStream(FILENAME);
            byte[]b= fis.readAllBytes();
            fis.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }

        return users;
    }

    @Override
    public User find(String pseudo) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(FILENAME));
        String line ;
        while((line = scanner.nextLine())!=null) {
            String[] words = line.split(";");
            if(pseudo.equals(words[0])){
                return new User(words[0], words[1]);
            }
        }
        return null;
    }

    public boolean exists(User newUser) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(FILENAME));
        String line ;
        while((line = scanner.nextLine())!=null) {
            String[] words = line.split(";");
            if(words[0].equals(newUser.getPseudo()) && words[1].equals(newUser.getPassword())){
                return true;
            }
        }
        return false;
    }
}

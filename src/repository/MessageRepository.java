package repository;

import models.Message;

import java.util.Optional;

public class MessageRepository {

    public Message save(Message message) {
        return null;
    }

    public Iterable<Message> saveAll(Iterable<Message> messages) {
        return null;
    }

    public Optional<Message> findById(Message messageID) {
        return Optional.empty();
    }

    public boolean existsById(Message messageID) {
        return false;
    }

    public Iterable<Message> findAll() {
        return null;
    }

    public long count() {
        return 0;
    }

    public void deleteById(Message messageID) {

    }
    public void deleteAll() {

    }
}

package repository;

import models.Channel;

import java.util.Optional;

public class ChannelRepository{

    public Channel save(Channel channel) {
        return null;
    }

    public Iterable<Channel> saveAll(Iterable channels) {
        return null;
    }

    public Optional<Channel> findById(Channel channelID) {
        return Optional.empty();
    }

    public boolean existsById(Channel channelID) {
        return false;
    }

    public Iterable<Channel> findAll() {
        return null;
    }

    public long count() {
        return 0;
    }

    public void deleteById(Channel channelID) {

    }

    public void deleteAll() {

    }
}

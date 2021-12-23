package repositories;

import models.Channel;

import java.util.List;

public class ChannelUserRepository implements Repository<Channel>{

    public ChannelUserRepository() {

    }

    @Override
    public Channel save(Channel obj) {
        return null;
    }

    @Override
    public void delete(Channel obj) {

    }

    @Override
    public List<Channel> findAll() {
        return null;
    }

    @Override
    public Channel find(String id) {
        return null;
    }

    @Override
    public boolean exists(Channel obj) {
        return false;
    }
}

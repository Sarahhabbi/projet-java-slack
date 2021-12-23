package repositories;

import models.Channel;
import models.HasId;

import java.util.List;

public class ChannelRepository implements Repository<Channel>{
    private String filename;

    public ChannelRepository(String filename) {
        this.filename = filename;
    }

    @Override
    public Channel save(Channel obj) {
        return null;
    }

    @Override
    public void delete(Channel obj) {

    }

    @Override
    public List findAll() {
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

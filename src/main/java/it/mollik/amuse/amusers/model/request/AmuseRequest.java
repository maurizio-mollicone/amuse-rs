package it.mollik.amuse.amusers.model.request;

import java.util.List;

import it.mollik.amuse.amusers.model.AmuseModel;
import it.mollik.amuse.amusers.model.IAmuseEntity;
import it.mollik.amuse.amusers.model.Key;


public class AmuseRequest<T extends IAmuseEntity> extends AmuseModel<T> {
    
    public AmuseRequest() {
        super();
    }

    public AmuseRequest(Key key, List<T> data) {
        super(key, null, data);
    }
}



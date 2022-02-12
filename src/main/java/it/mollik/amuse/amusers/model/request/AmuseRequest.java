package it.mollik.amuse.amusers.model.request;

import java.util.List;

import it.mollik.amuse.amusers.model.AmuseModel;
import it.mollik.amuse.amusers.model.IAmuseEntity;
import it.mollik.amuse.amusers.model.RequestKey;
import it.mollik.amuse.amusers.model.SearchParams;

public class AmuseRequest<T extends IAmuseEntity> extends AmuseModel<T> {
    
    public AmuseRequest() {
        super();
    }

    public AmuseRequest(RequestKey requestKey) {
        super(requestKey);
    }

    public AmuseRequest(RequestKey requestKey, List<T> data) {
        super(requestKey, data);
    }

    public AmuseRequest(RequestKey requestKey, SearchParams page, List<T> data) {
        super(requestKey, page, data);
    }
}



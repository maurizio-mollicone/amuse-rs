package it.mollik.amuse.amusers.model.request;

import java.util.List;

import it.mollik.amuse.amusers.model.AmuseModel;
import it.mollik.amuse.amusers.model.IAmuseEntity;
import it.mollik.amuse.amusers.model.Key;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class AmuseRequest<T extends IAmuseEntity> extends AmuseModel<T> {
    
    public AmuseRequest(Key key, List<T> data) {
        super(key, null, data);
    }
}



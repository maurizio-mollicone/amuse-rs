package it.mollik.amuse.amusers.model.response;

import java.util.List;

import it.mollik.amuse.amusers.config.Constants;
import it.mollik.amuse.amusers.model.AmuseModel;
import it.mollik.amuse.amusers.model.IAmuseEntity;
import it.mollik.amuse.amusers.model.Key;
import it.mollik.amuse.amusers.model.SearchParams;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class AmuseResponse<T extends IAmuseEntity>  extends AmuseModel<T> {
    
    private int statusCode;

    private String statusMessage;

    public AmuseResponse(Key key, int statusCode, String statusMessage, List<T> data) {
        super(key, new SearchParams(), data);
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
    
    public AmuseResponse(Key key, SearchParams searchParams, List<T> data) {
        super(key, searchParams, data);
        this.statusCode = Constants.Status.Code.STATUS_CODE_OK;
        this.statusMessage = Constants.Status.Message.STATUS_MESSAGE_OK;
    }

    public AmuseResponse(Key key, List<T> data) {
        super(key, new SearchParams(), data);
        this.statusCode = Constants.Status.Code.STATUS_CODE_OK;
        this.statusMessage = Constants.Status.Message.STATUS_MESSAGE_OK;
    }
}

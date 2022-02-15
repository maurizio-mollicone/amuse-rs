package it.mollik.amuse.amusers.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class AmuseModel<T extends IAmuseEntity> extends AmuseObject {
    
    private Key key;

    private SearchParams searchParams;

    private List<T> data;

    public AmuseModel(Key key, SearchParams searchParams, List<T> data) {
        this.key = key;
        this.searchParams = searchParams;
        this.data = data;
    }


    public AmuseModel() {
    }


    /**
     * @return Key return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * @return SearchParams return the searchParams
     */
    public SearchParams getSearchParams() {
        return searchParams;
    }

    /**
     * @param searchParams the searchParams to set
     */
    public void setSearchParams(SearchParams searchParams) {
        this.searchParams = searchParams;
    }

    /**
     * @return List<T> return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<T> data) {
        this.data = data;
    }

}

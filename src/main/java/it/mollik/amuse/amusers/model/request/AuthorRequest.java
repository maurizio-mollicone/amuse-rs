package it.mollik.amuse.amusers.model.request;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

import it.mollik.amuse.amusers.model.orm.Author;

public class AuthorRequest extends GenericRequest {
    
    private List<Author> authors;

    public AuthorRequest() {
        super();
        this.authors = new ArrayList<>();
    }
    /**
     * @return List<Author> return the authors
     */
    public List<Author> getAuthors() {
        return this.authors;
    }

    /**
     * @param authors the users to set
     */
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
    
    @Override
    public String toString() {
        return new StringJoiner(StringUtils.EMPTY).add(this.getClass().getName()).add(" [ ").add(this.getRequestKey().toString())
        .add(", authors ").add(this.getAuthors().toString())
        .add("]").toString();
    }
}

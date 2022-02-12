package it.mollik.amuse.amusers.model.response;

import java.util.List;

import it.mollik.amuse.amusers.model.Key;
import it.mollik.amuse.amusers.model.orm.Author;
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
public class AuthorResponse extends AmuseResponse {

    public AuthorResponse(Key key, List<Author> authors2) {
    }

    public AuthorResponse(Key key) {
    }

    public AuthorResponse(Key key, int i, String string, List<Author> authors2) {
    }

    private List<Author> authors;
}

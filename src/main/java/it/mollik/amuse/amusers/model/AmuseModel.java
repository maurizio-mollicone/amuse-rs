package it.mollik.amuse.amusers.model;

import java.util.List;

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
public class AmuseModel<T extends IAmuseEntity> extends AmuseObject {
    
    private Key key;

    private SearchParams searchParams;

    private List<T> data;

}

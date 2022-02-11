package it.mollik.amuse.amusers.model;

import java.io.Serializable;

public interface IAmuseEntity extends Serializable {
    
    public String getName();

    public String toJSONString();

}

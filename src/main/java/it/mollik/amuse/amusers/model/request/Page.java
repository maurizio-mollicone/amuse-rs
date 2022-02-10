package it.mollik.amuse.amusers.model.request;

import java.io.Serializable;
import java.util.StringJoiner;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

public class Page implements Serializable {
    
    @NotNull
    private int index;

    @NotNull
    private int size;

    public Page(int index, int size) {
        this.index = index;
        this.size = size;
    }

    /**
     * @return int return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return int return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return new StringJoiner(StringUtils.EMPTY).add(this.getClass().getName()).add(" [index]: [").add(Integer.toString(index)).add(", ").add(" [index]: ").add(Integer.toString(index)).toString();
    }
}

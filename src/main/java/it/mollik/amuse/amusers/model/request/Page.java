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

    private int totalItems;

    private int totalPages;

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

    /**
     * @return int return the totalItems
     */
    public int getTotalItems() {
        return totalItems;
    }

    /**
     * @param totalItems the totalItems to set
     */
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    /**
     * @return int return the totalPages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * @param totalPages the totalPages to set
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "Page [index=" + index + ", size=" + size + ", totalItems=" + totalItems + ", totalPages=" + totalPages
                + "]";
    }

}

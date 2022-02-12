package it.mollik.amuse.amusers.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class SearchParams extends AmuseEntity {
    
    @NotNull
    private int index;

    @NotNull
    private int size;

    private int currentSize;

    private long totalItems;

    private int totalPages;

    public SearchParams(int index, int size) {
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
    public long getTotalItems() {
        return totalItems;
    }

    /**
     * @param totalItems the totalItems to set
     */
    public void setTotalItems(long totalItems) {
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

    
    /**
     * @return int return the currentSize
     */
    public int getCurrentSize() {
        return currentSize;
    }

    /**
     * @param currentSize the currentSize to set
     */
    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    @Override
    public String toString() {
        return "Page [index=" + index + ", size=" + size + ", currentSize=" + currentSize + ", totalItems=" + totalItems + ", totalPages=" + totalPages
                + "]";
    }



}

package it.mollik.amuse.amusers.model;

import javax.validation.constraints.NotNull;

public class SearchParams extends AmuseObject {
    
    @NotNull(message = "index must be valorized")
    private int currentPageIndex=0;

    @NotNull
    private int pageSize=10;

    private int currentPageSize;

    private long totalItems;

    private int totalPages;

    public SearchParams(int index, int size) {
        this.currentPageIndex = index;
        this.pageSize = size;
    }



    /**
     * @return int return the currentPageIndex
     */
    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    /**
     * @param currentPageIndex the currentPageIndex to set
     */
    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    /**
     * @return int return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return int return the currentPageSize
     */
    public int getCurrentPageSize() {
        return currentPageSize;
    }

    /**
     * @param currentPageSize the currentPageSize to set
     */
    public void setCurrentPageSize(int currentPageSize) {
        this.currentPageSize = currentPageSize;
    }

    /**
     * @return long return the totalItems
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

}

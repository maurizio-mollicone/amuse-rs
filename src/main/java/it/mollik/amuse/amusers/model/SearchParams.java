package it.mollik.amuse.amusers.model;

import javax.validation.constraints.NotNull;

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
public class SearchParams extends AmuseObject {
    
    public SearchParams(int currentPageIndex, int currentPageSize) {
        this.currentPageIndex = currentPageIndex;
        this.currentPageSize = currentPageSize;
    }

    @NotNull(message = "index must be valorized")
    private int currentPageIndex=0;

    @NotNull(message = "index must be valorized")
    private int pageSize=10;

    private int currentPageSize;

    private long totalItems;

    private int totalPages;

}

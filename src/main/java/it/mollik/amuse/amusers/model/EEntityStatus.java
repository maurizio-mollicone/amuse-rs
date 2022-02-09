package it.mollik.amuse.amusers.model;

public enum EEntityStatus {
    
    INSERT("INSERT"),
    UPDATE("UPDATE"),
    SYNC("SYNC"),
    DELETE("DELETE");

    private String value;

    private EEntityStatus(String value) {
        this.value = value;
    }

    
    public String getValue() {
        return value;
    }

    protected void setValue(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return this.value;
    }
    
}

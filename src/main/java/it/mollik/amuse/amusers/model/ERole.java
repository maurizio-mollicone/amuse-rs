package it.mollik.amuse.amusers.model;

public enum ERole {
    
    USER("USER"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN");

    private String value;

    private ERole(String value) {
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

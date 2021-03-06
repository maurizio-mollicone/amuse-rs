package it.mollik.amuse.amusers.model;

public enum EUserStatus {
    
    ENABLED(1),
    DISABLED(0),
    BLOCKED(2);

    private int value;

    private EUserStatus(int value) {
        this.value = value;
    }

    
    public int getValue() {
        return value;
    }

    protected void setValue(int value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
    
}

package application.radiocell;
public enum Chose {
    YES,
    NO,
    LATE;

    public String toString() {
        return super.toString().toLowerCase();
    };
}
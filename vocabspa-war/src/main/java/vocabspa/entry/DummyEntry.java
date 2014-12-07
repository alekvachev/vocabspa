package vocabspa.entry;

/**
 * Created by Alek on 6/30/2014.
 */
public class DummyEntry {
    String field;
    String lookUpString;

    public DummyEntry(String field, String lookUpString) {
        this.field = field;
        this.lookUpString = lookUpString;
    }

    public String getLookUpString() {
        return lookUpString;
    }

    public void setLookUpString(String lookUpString) {
        this.lookUpString = lookUpString;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}

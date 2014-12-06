package vocabspa.entry;

import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alek on 6/12/2014.
 */
public class Entry {
    private String frn;
    private String prn;
    private String ntv;
    private String lastReview;
    private int correctCount;
    private String nextReview;
    private Key entryGroup;
    private Key synonymGroup;
    private String dictionary;
    private String userEmail;
    private int timezoneOffset;
    private Date created;
    private Date lastModified;

    public String getFrn() {
        return frn;
    }

    public void setFrn(String frn) {
        this.frn = frn;
    }

    public String getPrn() {
        return prn;
    }

    public void setPrn(String prn) {
        this.prn = prn;
    }

    public String getNtv() {
        return ntv;
    }

    public void setNtv(String ntv) {
        this.ntv = ntv;
    }

    public String getLastReview() {
        return lastReview;
    }

    public void setLastReview(String lastReview) {
        this.lastReview = lastReview;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public String getNextReview() {
        return nextReview;
    }

    public void setNextReview(String nextReview) {
        this.nextReview = nextReview;
    }

    public Key getEntryGroup() {
        return entryGroup;
    }

    public void setEntryGroup(Key entryGroup) {
        this.entryGroup = entryGroup;
    }

    public Key getSynonymGroup() {
        return synonymGroup;
    }

    public void setSynonymGroup(Key synonymGroup) {
        this.synonymGroup = synonymGroup;
    }

    public String getDictionary() {
        return dictionary;
    }

    public void setDictionary(String dictionary) {
        this.dictionary = dictionary;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String user) {
        this.userEmail = user + "@gmail.com";
    }

    public int getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(int timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void populateNewEntry() {
        this.created = new Date();
        this.lastModified = this.created;
        this.correctCount = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
        Date lastReviewAux = DateUtils.truncate(DateUtils.addMinutes(this.created, -timezoneOffset), Calendar.DATE);
        this.lastReview = sdf.format(lastReviewAux);
        this.nextReview = sdf.format(DateUtils.addDays(lastReviewAux, 2));
    }
}
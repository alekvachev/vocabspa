package vocabspa.entry;

import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;

import java.util.Date;

/**
 * Created by Alek on 6/12/2014.
 */
public class Entry {
    private String frn;
    private String prn;
    private String ntv;
    private Date lastReview;
    private int correctCount;
    private Date nextReview;
    private String entryGroupStr;
    private String synonymGroupStr;
    private Key entryGroup;
    private Key synonymGroup;
    private String dictionary;
    private String userEmail;

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

    public Date getLastReview() {
        return lastReview;
    }

    public void setLastReview(Date lastReview) {
        this.lastReview = lastReview;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public Date getNextReview() {
        return nextReview;
    }

    public void setNextReview(Date nextReview) {
        this.nextReview = nextReview;
    }

    public String getEntryGroupStr() {
        return entryGroupStr;
    }

    public void setEntryGroupStr(String entryGroupStr) {
        this.entryGroupStr = entryGroupStr;
    }

    public String getSynonymGroupStr() {
        return synonymGroupStr;
    }

    public void setSynonymGroupStr(String synonymGroupStr) {
        this.synonymGroupStr = synonymGroupStr;
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

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
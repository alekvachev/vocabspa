package vocabspa.entry;

import com.google.appengine.api.datastore.*;

/**
 * Created by Alek on 10/19/2014.
 */
public class EntryService {

    private static final String USER_KIND = "user";
    private static final String ENTRY_KIND = "entry";
    private static final String DICTIONARY_KEY_SUFFIX = "_d$";
    private static final String DICTIONARY_KIND = "dictionary";

    public Entry save(Entry entry) throws Exception {
        Entity dictionary;
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key userKey = KeyFactory.createKey(USER_KIND, entry.getUserEmail());
        Key dictionaryKey = KeyFactory.createKey(userKey, DICTIONARY_KIND, entry.getUserEmail() + DICTIONARY_KEY_SUFFIX + entry.getDictionary());
        try {
            dictionary = datastore.get(dictionaryKey);
        } catch (EntityNotFoundException e) {
            throw new Exception("Unable to retrieve dictionary " + entry.getUserEmail() + DICTIONARY_KEY_SUFFIX + entry.getDictionary());
        }

        //populate local entry object's properties
        entry.populateNewEntry();

        Entity entryEntity = populateNewEntity(entry, dictionary);
        datastore.put(entryEntity);

        return entry;
    }

    private Entity populateNewEntity(Entry localEntry, Entity dictionary) {
        Entity entryEntity = new Entity(ENTRY_KIND, dictionary.getKey());
        entryEntity.setProperty("frn", localEntry.getFrn());
        entryEntity.setProperty("prn", localEntry.getPrn());
        entryEntity.setProperty("ntv", localEntry.getNtv());
        entryEntity.setProperty("lastReview", localEntry.getLastReview());
        entryEntity.setProperty("correctCount", localEntry.getCorrectCount());
        entryEntity.setProperty("nextReview", localEntry.getNextReview());
        //TO DO provide for entry and synonym groups
        entryEntity.setProperty("created", localEntry.getCreated());
        entryEntity.setProperty("lastModified", localEntry.getLastModified());

        return entryEntity;
    }
}

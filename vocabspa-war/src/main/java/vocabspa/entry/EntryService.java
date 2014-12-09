package vocabspa.entry;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alek on 10/19/2014.
 */
public class EntryService {

    private static final String USER_KIND = "user";
    private static final String DICTIONARY_KIND = "dictionary";
    private static final String ENTRY_KIND = "entry";
    private static final String DICTIONARY_KEY_SUFFIX = "_d$";
    Log logger = LogFactory.getLog(getClass());

    public Entry save(Entry entry) throws Exception {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity dictionary = getDictionary(entry.getUserEmail(), entry.getDictionary(), datastore);

        //populate local entry object's properties
        entry.populateNewEntry();

        Entity entryEntity = populateNewEntity(entry, dictionary);
        datastore.put(entryEntity);

        return entry;
    }

    public List<Entry> lookup(String user, String dictionaryName, String field, String lookUpString) throws Exception{
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        String userEmail = user + "@gmail.com";
        Entity dictionary = getDictionary(userEmail, dictionaryName, datastore);

        //prepare a LIKE query
        Filter minStringFilter = new FilterPredicate(field, FilterOperator.GREATER_THAN_OR_EQUAL, lookUpString);
        Filter maxStringFilter = new FilterPredicate(field, FilterOperator.LESS_THAN, lookUpString + '\ufffd');
        Filter stringRangeFilter = CompositeFilterOperator.and(minStringFilter, maxStringFilter);
        Query query = new Query(ENTRY_KIND).setAncestor(dictionary.getKey()).setFilter(stringRangeFilter);

        List<Entity> resultSet = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(20));
        List<Entry> entrySet = new ArrayList<Entry>();
        for (Entity entity : resultSet) {
            Entry entry = new Entry(entity);
            entrySet.add(entry);
        }

        return entrySet;
    }

    private Entity getDictionary(String userEmail, String dictionaryName, DatastoreService datastore) throws Exception{
        Entity dictionary;
        Key userKey = KeyFactory.createKey(USER_KIND, userEmail);
        Key dictionaryKey = KeyFactory.createKey(userKey, DICTIONARY_KIND, userEmail + DICTIONARY_KEY_SUFFIX + dictionaryName);
        try {
            dictionary = datastore.get(dictionaryKey);
        } catch (EntityNotFoundException e) {
            throw new Exception("Unable to retrieve dictionary " + userEmail + DICTIONARY_KEY_SUFFIX + dictionaryName);
        }

        return dictionary;
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

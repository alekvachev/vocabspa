package vocabspa.dictionary;

import com.google.appengine.api.datastore.*;
import static com.google.appengine.api.datastore.FetchOptions.Builder.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Alek on 6/7/2014.
 */
public class DictionaryService {
    private static final String DICTIONARY_KEY_SUFFIX = "_d$";
    private static final String DICTIONARY_KIND = "dictionary";
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public static List<Entity> getUserDictionaries(Key userKey) {
        Query q = new Query(DICTIONARY_KIND).setAncestor(userKey).addSort("dateCreated", Query.SortDirection.ASCENDING);
        return datastore.prepare(q).asList(withDefaults());
    }

    public static void addDictionaryForUser(String dictionaryName, Key userKey) {
        Entity dictionaryEntity = new Entity(DICTIONARY_KIND, userKey.getName() + DICTIONARY_KEY_SUFFIX + dictionaryName, userKey);
        dictionaryEntity.setProperty("name", dictionaryName);
        dictionaryEntity.setProperty("dateCreated", new Date());
        dictionaryEntity.setProperty("wordCount", 0);
        dictionaryEntity.setProperty("currentStreak", 0);
        dictionaryEntity.setProperty("longestStreak", 0);
        datastore.put(dictionaryEntity);
    }
}

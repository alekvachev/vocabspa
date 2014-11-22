package vocabspa.entry;

import com.google.appengine.api.datastore.*;

/**
 * Created by Alek on 10/19/2014.
 */
public class EntryService {

    private static final String ENTRY_KIND = "entry";
    private static final String DICTIONARY_KEY_SUFFIX = "_d$";
    private static final String DICTIONARY_KIND = "dictionary";
    private Entity dictionary;
    private Entity user;

    public Entry save(Entry entry) throws Exception{
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        if (user == null) {
            Key userKey = KeyFactory.createKey("user", entry.getUserEmail());
            try {
                user = datastore.get(userKey);
            } catch(EntityNotFoundException e) {
                throw new Exception("Unable to retrieve user" + entry.getUserEmail());
            }
        }
        if (dictionary == null) {
            //Query q = new Query(DICTIONARY_KIND).setAncestor(entry.getUserEmail())
        }
        //Entity entryEntity = new Entity(ENTRY_KIND,)
    }
}

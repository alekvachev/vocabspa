package vocabspa.user;

import com.google.appengine.api.datastore.*;

/**
 * Created by Alek on 5/22/2014.
 */
public class UsersService {

    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public static Entity getUserEntity(String email) {
        Key userKey = KeyFactory.createKey("user", email);

        Entity userEntity = null;
        try {
            userEntity = datastore.get(userKey);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        return userEntity;
    }

    public static void updateUser(Entity userEntity) {
        datastore.put(userEntity);
    }
}
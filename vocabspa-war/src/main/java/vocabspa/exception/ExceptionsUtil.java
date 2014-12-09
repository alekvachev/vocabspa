package vocabspa.exception;

import org.apache.commons.logging.Log;

/**
 * Created by Alek on 12/8/2014.
 */
public class ExceptionsUtil {

    public static void handleGenericException(Exception e, Log logger) {
        logger.error(e);
        for (StackTraceElement element: e.getStackTrace()) {
            logger.error(element);
        }
    }
}

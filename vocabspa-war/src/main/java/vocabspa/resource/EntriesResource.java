package vocabspa.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import vocabspa.entry.Entry;
import vocabspa.entry.EntryService;
import vocabspa.exception.ExceptionsUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Alek on 6/10/2014.
 */
@Path("/entry")
public class EntriesResource {
    //app engine does not support injection
    EntryService entryService;

    Log logger = LogFactory.getLog(getClass());

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveEntry(Entry entry) {
        entryService = new EntryService();
        try {
            return Response.status(201).entity(entryService.save(entry)).build();
        } catch (Exception e) {
            ExceptionsUtil.handleGenericException(e, logger);
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/lookup")
    @Produces(MediaType.APPLICATION_JSON)
    public Response lookUpEntries(@QueryParam("user") String user, @QueryParam("dict") String dict,
                                  @QueryParam("field") String field, @QueryParam("string") String string) {
        entryService = new EntryService();
        try {
            return Response.ok().entity(entryService.lookup(user, dict, field, string)).build();
        } catch (Exception e) {
            ExceptionsUtil.handleGenericException(e, logger);
            return Response.status(500).build();
        }
    }
}

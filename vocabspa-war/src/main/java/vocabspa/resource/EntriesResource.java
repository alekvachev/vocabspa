package vocabspa.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import vocabspa.entry.DummyEntry;
import vocabspa.entry.Entry;
import vocabspa.entry.EntryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Alek on 6/10/2014.
 */
@Path("/entry")
public class EntriesResource {
    @Inject
    EntryService entryService;

    Log logger = LogFactory.getLog(getClass());

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public DummyEntry getEntry() {
        DummyEntry dm = new DummyEntry();
        dm.setMsg("message");
        return dm;
    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveEntry(Entry entry) {
        return Response.status(201).entity(entry).build();
    }
}

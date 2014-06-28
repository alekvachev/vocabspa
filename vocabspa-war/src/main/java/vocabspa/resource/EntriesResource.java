package vocabspa.resource;

import vocabspa.entry.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Alek on 6/10/2014.
 */
@Path("res")
public class EntriesResource {

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEntry(Entry entry) {


        String result = "Entry created:\n";// + entry;
        return Response.status(201).entity(result).build();
    }
}

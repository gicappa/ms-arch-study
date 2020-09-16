package eu.cise;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static java.lang.String.format;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/messages")
public class MessageResource {

    private final Logger logger = LoggerFactory.getLogger("ms:submission-agent");

    @Inject
    @Channel("validated-messages")
    Emitter<String> emitter;

    @POST
    @Consumes(TEXT_PLAIN)
    @Produces("text/plain")
    public String create(String message) {
        logger.info(">> RECV: {}", message);

        emitter.send(format("msg: %s\nstep: verified", message));

        return "RECV: " + message;
    }
}

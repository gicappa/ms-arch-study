package eu.cise;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/sentences")
public class SentencesResource {

    @Inject
    @Channel("submitted-messages")
    Emitter<String> emitter;

    @POST
    @Consumes(TEXT_PLAIN)
    @Produces("text/plain")
    public String create(String sentence) {
        System.out.println(">> RECV: " + sentence);
        emitter.send(sentence);

        return "Received: " + sentence;
    }

}

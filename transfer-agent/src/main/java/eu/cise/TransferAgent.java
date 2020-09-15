package eu.cise;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransferAgent {

    private final Logger logger = LoggerFactory.getLogger("ms:transfer-agent");

    @Incoming("transferred-messages")
    public void transfer(String value) {
        logger.info(">> TRNS: {}", value);
    }
}

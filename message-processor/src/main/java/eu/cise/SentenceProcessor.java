package eu.cise;

import org.apache.kafka.streams.kstream.ValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SentenceProcessor implements ValueMapper<String, String> {

    private final Logger logger = LoggerFactory.getLogger("ms:sentence-processor");

    @Override
    public String apply(String value) {
        logger.info(">> PROC: {}", value);

        return value.toUpperCase();
    }
}
